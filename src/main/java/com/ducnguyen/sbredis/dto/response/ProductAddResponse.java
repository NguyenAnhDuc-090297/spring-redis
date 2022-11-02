package com.ducnguyen.sbredis.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAddResponse {

    private String description;

    private double price;

    private Integer quantityAvailable;

    private String name;

    private Integer categoryId;
}
