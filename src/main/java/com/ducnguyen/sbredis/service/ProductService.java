package com.ducnguyen.sbredis.service;

import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.dto.request.ProductAddRequest;
import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> getProduct(long id);

    Map<String, String> getProduct2(long id);

    void updateProduct(ProductDto productDto);

    List<ProductDto> searchProduct(ProductSearchRequest productSearchRequest);

    void addProduct(ProductAddRequest productAddRequest);

    List<ProductDto> findAll();
}
