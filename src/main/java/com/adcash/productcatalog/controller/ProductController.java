package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dao.Product;
import com.adcash.productcatalog.dto.ProductRequestObj;
import com.adcash.productcatalog.dto.ResponseObj;
import com.adcash.productcatalog.exceptions.AuthenticationException;
import com.adcash.productcatalog.exceptions.CategoryException;
import com.adcash.productcatalog.exceptions.ProductException;
import com.adcash.productcatalog.exceptions.UserException;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.TokenValidator;
import com.adcash.productcatalog.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
public class ProductController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    /**
     * This endpoint returns a list of products in the database.
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        log.info("Fetching all products from the data store.");
        return ResponseEntity.ok(productService.findAllProducts());
    }

    /**
     * This endpoint creates a product by user with role ADMIN.
     * @param productRequestObj
     * @return
     * @throws CategoryException
     */
    @PostMapping("/products")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Product> create(HttpServletRequest request, @RequestBody ProductRequestObj productRequestObj) throws CategoryException, AuthenticationException, UserException {
        log.info("Authorizing the request with received token");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Creating a product.");
        return ResponseEntity.ok(productService.save(productRequestObj));

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

    /**
     * This endpoint deletes a product by productid, by ADMIN users only
     * @param request
     * @param productId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @DeleteMapping("/products/{productId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable int productId) throws AuthenticationException, UserException {
        log.info("Authenticating against the provided token.");
        isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Attempting to delete a product.");
        productService.delete(productId);
        return ResponseEntity.ok("Deleted.");
    }


    /**
     * This endpoint updates a product by ADMIN users only.
     * @param request
     * @param productId
     * @param productRequestObj
     * @return
     */
    @PutMapping("/products/{productId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Product> update(HttpServletRequest request,
                                          @PathVariable int productId,
                                          @RequestBody ProductRequestObj productRequestObj) throws AuthenticationException, UserException, ProductException, CategoryException {

        log.info("Authenticating against the provided token.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Updating a product by user with email: "+customer.getEmail());
        Product updatedProduct= productService.update(productId, productRequestObj);
        return ResponseEntity.ok(updatedProduct);
    }
}
