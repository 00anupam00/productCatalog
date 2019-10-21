package com.adcash.productcatalog.repository;

import com.adcash.productcatalog.dao.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("Select c from category c where c.categoryId in (Select distinct p.category.categoryId from product_category p where p.product.productId = ?1)")
    Optional<Category> findByProducts(@Param("productId") int productId);

    @Query("Select c from category c where c.department.departmentId = ?1")
    List<Category> findAllByDepartment(@Param("departmentId") int departmentId);
}
