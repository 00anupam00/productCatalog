package com.adcash.productcatalog.service;

import com.adcash.productcatalog.dao.Category;
import com.adcash.productcatalog.dto.CategoryResponseObj;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    public List<CategoryResponseObj> findAll() {
        List<Category> categoryList= (List<Category>)categoryRepository.findAll();
        List<CategoryResponseObj> categoryResponseObjs= new ArrayList<>();
        categoryList.forEach(
                category -> {
                    CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
                    categoryResponseObj.setCategory_id(category.getCategoryId());
                    categoryResponseObj.setDepartment_id(category.getDepartment().getDepartmentId());
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
        categoryResponseObj.setDepartment_id(category.getDepartment().getDepartmentId());
        categoryResponseObj.setDescription(category.getDescription());
        categoryResponseObj.setName(category.getName());
        return categoryResponseObj;
    }

    public CategoryResponseObj findByProduct(int productId) throws CategoryException {
        Category category= categoryRepository.findByProducts(productId)
                .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "product_id"));
        CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
        categoryResponseObj.setCategory_id(category.getCategoryId());
        categoryResponseObj.setDepartment_id(category.getDepartment().getDepartmentId());
        categoryResponseObj.setDescription(category.getDescription());
        categoryResponseObj.setName(category.getName());
        return categoryResponseObj;
    }

    public List<CategoryResponseObj> findAllByDepartment(int departmentId) {
        List<Category> categoryList= categoryRepository.findAllByDepartment(departmentId);
        List<CategoryResponseObj> categoryResponseObjs= new ArrayList<>();
        categoryList.forEach(
                category -> {
                    CategoryResponseObj categoryResponseObj= new CategoryResponseObj();
                    categoryResponseObj.setCategory_id(category.getCategoryId());
                    categoryResponseObj.setDepartment_id(category.getDepartment().getDepartmentId());
                    categoryResponseObj.setDescription(category.getDescription());
                    categoryResponseObj.setName(category.getName());
                    categoryResponseObjs.add(categoryResponseObj);
                }
        );
        return categoryResponseObjs;
    }
}
