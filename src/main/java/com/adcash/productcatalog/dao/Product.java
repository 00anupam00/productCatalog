package com.adcash.productcatalog.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "product")
@Getter@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productId;
    @Column(length = 100)
    @NotNull
    private String name;
    @Column(length = 1000)
    @NotNull
    private String description;
    @Column(precision = 2, scale = 10)
    @NotNull
    private double price;
    @Column(name = "discounted_price", scale = 10, precision = 2)
    @NotNull
    private double discountedPrice=0.0;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties("products")
    private Category category;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private Set<ProductCategory> productCategories= new HashSet<>();

}
