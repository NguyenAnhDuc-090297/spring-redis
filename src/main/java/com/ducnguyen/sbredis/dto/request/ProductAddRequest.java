package com.ducnguyen.sbredis.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddRequest {

    private String description;

    private double price;

    private Integer quantityAvailable;

    private String name;

    private Integer categoryId;
}
