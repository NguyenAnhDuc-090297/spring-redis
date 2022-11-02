package com.ducnguyen.sbredis.service;

import com.ducnguyen.sbredis.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> getProduct(long id);

    void updateProduct(ProductDto productDto);
}
