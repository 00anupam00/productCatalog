package com.adcash.productcatalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequestObj {

    private int productId;
    private String name;
    private String description;
    private double price;
    private double discountedPrice=0.0;
    private int categoryId;
}
