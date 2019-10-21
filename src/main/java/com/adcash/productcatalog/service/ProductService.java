package com.adcash.productcatalog.service;

import com.adcash.productcatalog.dao.Category;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dao.Product;
import com.adcash.productcatalog.dto.ResponseObj;
import com.adcash.productcatalog.dto.ReviewObj;
import com.adcash.productcatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Product> findAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public Product findProductById(int productId) {
        return productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    }

    public ResponseObj getAllProductsByCategory(int categoryId) {
        Optional<List<Product>> products= productRepository.findAllByCategory(categoryId);
        ResponseObj responseObj = new ResponseObj();
        responseObj.setRows(products.orElseGet(LinkedList::new));
        return responseObj;
    }
}
