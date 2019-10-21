package com.adcash.productcatalog.repository;

import com.adcash.productcatalog.dao.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    Page<Product> findAll(Pageable pageable);

    @Query("select p from product p where p.productId in (select pc.product.productId from product_category pc where pc.category.categoryId=?1)")
    Optional<List<Product>> findAllByCategory(@Param("categoryId") int category_id);

    @Query("select a from attribute a where a.attributeId in " +
            "(select pa.attributeValue.attribute.attributeId from product_attribute pa where pa.product.productId = ?1)")
    List<Attribute> findAttributesById(@Param("productId") int productId);
}
