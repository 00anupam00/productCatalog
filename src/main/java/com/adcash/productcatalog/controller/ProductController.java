package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dao.Product;
import com.adcash.productcatalog.dto.ResponseObj;
import com.adcash.productcatalog.util.TokenValidator;
import com.adcash.productcatalog.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ProductController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    /**
     * This endpoint returns a list of products in the database. It should return a paginated response.
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        log.info("Fetching all products from the data store.");
        return ResponseEntity.ok(productService.findAllProducts());
    }

    /**
     * This endpoint returns a single product object using the product id.
     * @param product_id
     * @param description_length
     * @return
     */
    @GetMapping("/products/{product_id}")
    public ResponseEntity<Product> getProduct(@PathVariable int product_id,
                                              @RequestParam(required = false) Integer description_length){
        description_length = description_length == null  ? 200 : description_length;
        log.info("Find a product with productId, {}", product_id);
        return ResponseEntity.ok(productService.findProductById(product_id));
    }

    /**
     * This endpoint should return list of products in a category using the category id in the request params.
     * @param category_id
     * @return
     */
    @GetMapping("/products/inCategory/{category_id}")
    public ResponseEntity<ResponseObj> getAllProductsInCategory(
            @PathVariable int category_id){

        log.info("Fetching all products in a category with category id, {}", category_id);
        ResponseObj response= productService.getAllProductsByCategory(category_id);
        return ResponseEntity.ok(response);
    }
}
