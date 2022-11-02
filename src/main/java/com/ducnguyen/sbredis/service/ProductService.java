package com.ducnguyen.sbredis.service;

import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.dto.request.ProductSearchRequest;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<ProductDto> getProduct(long id);

    void updateProduct(ProductDto productDto);

    List<ProductDto> searchProduct(ProductSearchRequest productSearchRequest);
}
