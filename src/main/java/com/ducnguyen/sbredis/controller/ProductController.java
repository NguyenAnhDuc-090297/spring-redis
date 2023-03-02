package com.ducnguyen.sbredis.controller;

import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.dto.request.ProductAddRequest;
import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;
import com.ducnguyen.sbredis.dto.response.ProductSearchResponse;
import com.ducnguyen.sbredis.service.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/product2/{id}")
    public ResponseEntity<Map<String, String>> getProduct2ById(@PathVariable Long id) {
        Map<String, String> res = productService.getProduct2(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
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
