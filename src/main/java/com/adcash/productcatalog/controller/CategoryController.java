package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dto.CategoryResponseObj;
import com.adcash.productcatalog.exceptions.CategoryException;
import com.adcash.productcatalog.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryService categoryService;

    /**
     * This endpoint returns a list of product categories to the user.
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseObj>> getAllCategories(){
        log.info("Finding all categories.");
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * This endpoint returns a single category using the category id.
     * @param category_id
     * @return
     * @throws CategoryException
     */
    @GetMapping("/categories/{category_id}")
    public ResponseEntity<CategoryResponseObj> getCategory(@PathVariable int category_id) throws CategoryException {
        log.info("Get a category having category id: {}", category_id);
        return ResponseEntity.ok(categoryService.findById(category_id));
    }

    /**
     * This endpoint returns the category of a particular product.
     * @param product_id
     * @return
     * @throws CategoryException
     */
    @GetMapping("/categories/inProduct/{product_id}")
    public ResponseEntity<CategoryResponseObj> getAllCategoriesInProduct(@PathVariable int product_id) throws CategoryException {
        log.info("Finding a Category by product id: {}", product_id);
        return ResponseEntity.ok(categoryService.findByProduct(product_id));
    }
}
