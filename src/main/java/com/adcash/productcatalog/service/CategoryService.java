package com.adcash.productcatalog.service;

import com.adcash.productcatalog.dao.Category;
import com.adcash.productcatalog.dao.Product;
import com.adcash.productcatalog.dto.CategoryRequestObj;
import com.adcash.productcatalog.dto.CategoryResponseObj;
import com.adcash.productcatalog.exceptions.CategoryException;
import com.adcash.productcatalog.exceptions.ProductException;
import com.adcash.productcatalog.repository.ProductRepository;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.repository.CategoryRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;


    public List<CategoryResponseObj> findAll() {
        List<Category> categoryList= (List<Category>)categoryRepository.findAll();
        List<CategoryResponseObj> categoryResponseObjs= new ArrayList<>();
        categoryList.forEach(
                category -> {
                    CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
                    categoryResponseObj.setCategory_id(category.getCategoryId());
                    categoryResponseObj.setDescription(category.getDescription());
                    categoryResponseObj.setName(category.getName());
                    categoryResponseObjs.add(categoryResponseObj);
                }
        );
        return categoryResponseObjs;
    }

    public CategoryResponseObj findById(int categoryId) throws CategoryException {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "category_id"));
        CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
        categoryResponseObj.setCategory_id(category.getCategoryId());
        categoryResponseObj.setDescription(category.getDescription());
        categoryResponseObj.setName(category.getName());
        return categoryResponseObj;
    }

    public CategoryResponseObj findByProduct(int productId) throws CategoryException {
        Category category= categoryRepository.findByProducts(productId)
                .orElseThrow(() -> new CategoryException(Constants.PROD_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.PROD_01_MESSAGE, "product_id"));
        CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
        categoryResponseObj.setCategory_id(category.getCategoryId());
        categoryResponseObj.setDescription(category.getDescription());
        categoryResponseObj.setName(category.getName());
        return categoryResponseObj;
    }

    public Category save(CategoryRequestObj categoryRequestObj) throws ProductException {

        Set<Product> products= new HashSet<>();
        categoryRequestObj.getProductIds().forEach(
                id -> products.add(productRepository.findById(id)
                            .orElse(new Product()))
        );

        Category category= new Category();
        category.setDescription(categoryRequestObj.getDescription());
        category.setName(categoryRequestObj.getName());
        category.setProducts(products);
        return categoryRepository.save(category);
    }

    public void delete(int categoryId) throws CategoryException {
        Category category =categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "categoryId"));
        categoryRepository.delete(category);
    }

    public Category update(int categoryId, CategoryRequestObj categoryRequestObj) throws CategoryException {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "categoryId"));

        if(!Collections.isEmpty(categoryRequestObj.getProductIds())){
            Set<Product> products= new HashSet<>();
            categoryRequestObj.getProductIds().forEach(
                    id -> products.add(productRepository.findById(id)
                            .orElse(new Product()))
            );
            category.setProducts(products);
        }
        category.setName(categoryRequestObj.getName());
        category.setDescription(categoryRequestObj.getDescription());
        return category;
    }
}
