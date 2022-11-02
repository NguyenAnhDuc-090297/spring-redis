package com.ducnguyen.sbredis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String description;
    private double price;
    private Integer quantityAvailable;
}
