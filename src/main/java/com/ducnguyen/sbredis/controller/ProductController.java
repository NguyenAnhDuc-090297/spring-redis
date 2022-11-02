package com.ducnguyen.sbredis.controller;

import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.dto.request.ProductAddRequest;
import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;
import com.ducnguyen.sbredis.dto.response.ProductSearchResponse;
import com.ducnguyen.sbredis.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return this.productService.getProduct(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PatchMapping("/product")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        this.productService.updateProduct(productDto);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/product/search")
    public ResponseEntity<ProductSearchResponse> searchProduct(@RequestBody ProductSearchRequest productSearchRequest) {
        List<ProductDto> productList = productService.searchProduct(productSearchRequest);
        ProductSearchResponse response = new ProductSearchResponse();
        response.setProductSearchResponses(productList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductAddRequest> addProduct(@RequestBody ProductAddRequest productAddRequest) {
        productService.addProduct(productAddRequest);
        return ResponseEntity.ok(productAddRequest);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }
}
