package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dao.Category;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CategoryRequestObj;
import com.adcash.productcatalog.dto.CategoryResponseObj;
import com.adcash.productcatalog.exceptions.AuthenticationException;
import com.adcash.productcatalog.exceptions.CategoryException;
import com.adcash.productcatalog.exceptions.ProductException;
import com.adcash.productcatalog.exceptions.UserException;
import com.adcash.productcatalog.service.CategoryService;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
class CategoryController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

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
     * This endpoint creates a category by ADMIN users only.
     * @param request
     * @param categoryRequestObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     * @throws ProductException
     */
    @PostMapping("/categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Category> create(HttpServletRequest request, @RequestBody CategoryRequestObj categoryRequestObj) throws AuthenticationException, UserException, ProductException {
        log.info("Attempting authentication against the token received.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Admin User with email: "+customer.getEmail()+", is trying to create a category.");
        Category savedCategory= categoryService.save(categoryRequestObj);
        return ResponseEntity.ok(savedCategory);
    }

    /**
     * This endpoint deletes a category by ADMIN users only
     * @param request
     * @param categoryId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @DeleteMapping("/categories/{categoryId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Integer> delete(HttpServletRequest request, @PathVariable int categoryId) throws AuthenticationException, UserException,CategoryException {
        log.info("Attempting authentication against the token received.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Admin User with email: "+customer.getEmail()+", is trying to DELETE a category with id: "+categoryId);
        categoryService.delete(categoryId);
        return ResponseEntity.ok(categoryId);
    }


    /**
     * This endpoint updates a category by ADMIN users only.
     * @param request
     * @param categoryId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/categories/{categoryId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Category> update(HttpServletRequest request,
                                           @PathVariable int categoryId,
                                           @RequestBody CategoryRequestObj categoryRequestObj) throws AuthenticationException, UserException, CategoryException {
        log.info("Attempting authentication against the token received.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Admin User with email: "+customer.getEmail()+", is trying to create a category.");
        Category category= categoryService.update(categoryId, categoryRequestObj);
        return ResponseEntity.ok(category);
    }

}
