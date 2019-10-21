package com.adcash.productcatalog.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class ProductCategoryId implements Serializable {

    //@Column(name = "product_id")
    private int productId;
    //@Column(name = "category_id")
    private int categoryId;
}
