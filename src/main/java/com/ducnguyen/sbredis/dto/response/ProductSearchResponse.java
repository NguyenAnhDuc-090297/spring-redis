package com.ducnguyen.sbredis.dto.response;

import com.ducnguyen.sbredis.dto.ProductDto;
import com.ducnguyen.sbredis.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchResponse {

    private List<ProductDto> productSearchResponses;
}
