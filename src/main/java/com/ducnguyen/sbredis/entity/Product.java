package com.ducnguyen.sbredis.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double price;

    @Column(name = "qty_available", nullable = false)
    private Integer quantityAvailable;

    private String name;

    @Column(name = "category_id")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Integer categoryId;
}
