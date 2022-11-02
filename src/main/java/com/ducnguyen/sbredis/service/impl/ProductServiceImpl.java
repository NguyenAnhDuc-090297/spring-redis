package com.ducnguyen.sbredis.service.impl;

import com.ducnguyen.sbredis.cache.ProductCacheHelper;
import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.dto.request.ProductAddRequest;
import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;
import com.ducnguyen.sbredis.entity.CacheData;
import com.ducnguyen.sbredis.entity.Product;
import com.ducnguyen.sbredis.repository.CacheDataRepository;
import com.ducnguyen.sbredis.repository.ProductRepository;
import com.ducnguyen.sbredis.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CacheDataRepository cacheDataRepository;

    private final ObjectMapper objectMapper;

    public ProductServiceImpl(ProductRepository productRepository, CacheDataRepository cacheDataRepository) {
        this.productRepository = productRepository;
        this.cacheDataRepository = cacheDataRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<ProductDto> getProduct(long id) {

        try {
            // Cache hit
            Optional<CacheData> optionalCacheData = cacheDataRepository.findById(String.valueOf(id));
            if (optionalCacheData.isPresent()) {
                String productStr = optionalCacheData.get().getValue();
                TypeReference<ProductDto> mapType = new TypeReference<>() {
                };
                return Optional.ofNullable(objectMapper.readValue(productStr, mapType));
            }

            // Cache miss
            ProductDto productDto = this.productRepository.findById(id).map(this::entityToDto)
                    .orElseThrow(() -> new NullPointerException("productDto is null"));

            String productAsString = objectMapper.writeValueAsString(productDto);
            CacheData cacheData = new CacheData(String.valueOf(id), productAsString);
            cacheDataRepository.save(cacheData);

            return Optional.ofNullable(productDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        this.productRepository.findById(productDto.getId())
                .map(product -> this.setQuantityAvailable(product, productDto))
                .ifPresent(this.productRepository::save);
    }

    @Override
    public List<ProductDto> searchProduct(ProductSearchRequest productSearchRequest) {

        try {
            String cacheKey = ProductCacheHelper.buildCacheKey("searchProduct", productSearchRequest);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Cache hit
            Optional<CacheData> optionalCacheData = cacheDataRepository.findById(cacheKey);
            TypeReference<List<ProductDto>> mapType = new TypeReference<>() {};
            if (optionalCacheData.isPresent()) {
                String productListAsString = optionalCacheData.get().getValue();

                return objectMapper.readValue(productListAsString, mapType);
            }

            // Cache miss
            Thread.sleep(5000);

            List<Product> productList = productRepository.searchByNameAndCategory(productSearchRequest.getName(), productSearchRequest.getCategory());
            String productListAsJsonString = objectMapper.writeValueAsString(productList);
            CacheData cacheData = new CacheData(cacheKey, productListAsJsonString);
            cacheDataRepository.save(cacheData);

            String productListAsString = objectMapper.writeValueAsString(productList);

            return objectMapper.readValue(productListAsString, mapType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void addProduct(ProductAddRequest productAddRequest) {
        Product product = new Product();
        product.setName(productAddRequest.getName());
        product.setPrice(productAddRequest.getPrice());
        product.setDescription(productAddRequest.getDescription());
        product.setCategoryId(productAddRequest.getCategoryId());
        product.setQuantityAvailable(productAddRequest.getQuantityAvailable());
        productRepository.save(product);

        // delete cache
        this.deleteCacheData();
    }

    @Override
    public List<ProductDto> findAll() {

        try {
            Optional<CacheData> optionalCacheData = cacheDataRepository.findById("allProducts");
            TypeReference<List<ProductDto>> mapType = new TypeReference<>() {};
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // Cache hit
            if (optionalCacheData.isPresent()) {
                String allProductsAsString = optionalCacheData.get().getValue();

                return objectMapper.readValue(allProductsAsString, mapType);
            }

            // Cache miss
            Thread.sleep(5000);
            List<Product> allProducts = productRepository.findAll();
            String allProductsAsString = objectMapper.writeValueAsString(allProducts);
            CacheData cacheData = new CacheData("allProducts", allProductsAsString);
            cacheDataRepository.save(cacheData);

            return objectMapper.readValue(allProductsAsString, mapType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private ProductDto entityToDto(Product product){
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantityAvailable(product.getQuantityAvailable());
        dto.setName(product.getName());
        return dto;
    }

    private Product setQuantityAvailable(Product product, ProductDto dto){
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantityAvailable(dto.getQuantityAvailable());
        return product;
    }

    private void deleteCacheData() {
        // delete operation
        cacheDataRepository.deleteAll();
    }
}
