package com.adcash.productcatalog.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "category")
@Getter@Setter
public class Category implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "category_id")
        private int categoryId;
        @Column(length = 100)
        private String name;
        @Column(length = 1000)
        private String description;

        @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
        @JoinTable(
                name = "product_category",
                joinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "category_id")},
                inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "product_id")}
        )
        @JsonIgnoreProperties("categories")
        private Set<Product> products= new HashSet<>();

        @OneToMany(
                mappedBy = "category",
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        private Set<ProductCategory> productCategories= new HashSet<>();
}
