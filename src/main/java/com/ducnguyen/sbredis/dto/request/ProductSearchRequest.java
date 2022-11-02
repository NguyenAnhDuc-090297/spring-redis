package com.ducnguyen.sbredis.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {

    private String name;
    private String category;
}
