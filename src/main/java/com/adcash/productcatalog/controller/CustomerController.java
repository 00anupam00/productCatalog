package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerRequestObj;
import com.adcash.productcatalog.dto.CustomerResponseObj;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.JwtTokenUtil;
import com.adcash.productcatalog.util.TokenValidator;
import com.adcash.productcatalog.exceptions.AuthenticationException;
import com.adcash.productcatalog.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class CustomerController extends TokenValidator {

    Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    /**
     * This endpoints allow a user to create a new account.
     * @param customerRequestObj
     * @return
     * @throws UserException
     */
    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerResponseObj> create(@RequestBody CustomerRequestObj customerRequestObj) throws UserException {
        log.info("Attempt to save a customer");
        log.debug("CustomerRequest object found to save a customer as, {}", customerRequestObj);
        CustomerResponseObj savedCustomer= customerService.save(customerRequestObj);
        return ResponseEntity.status(201).body(savedCustomer);
    }

    /**
     * This endpoint allows a user to login to their customer account.
     * @param customerRequestObj
     * @return
     * @throws UserException
     */
    @PostMapping(value = "/customers/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerResponseObj> login(@RequestBody CustomerRequestObj customerRequestObj) throws UserException {
        log.info("Customer login request received.");
        log.debug("Customer with email, {} is trying to authenticate.", customerRequestObj.getEmail());
        return ResponseEntity.ok(customerService.authenticateLogin(customerRequestObj));
    }

    /**
     * This endpoint allows a user to login to the application using facebook.
     * @param request
     * @param accessTokenObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PostMapping("/customers/facebook")
    public ResponseEntity<CustomerResponseObj> facebookLogin(HttpServletRequest request, @RequestBody Map<String, String> accessTokenObj) throws AuthenticationException, UserException {
        isTokenValid(request.getHeader(Constants.HEADER_STRING));
        String accessToken= accessTokenObj.getOrDefault("access_token", "");
        //TODO Facebook Login

        CustomerResponseObj customerResponseObj= new CustomerResponseObj();
        return ResponseEntity.ok(customerResponseObj);
    }

    /**
     * This endpoint retrieves customer information using the customer id in the token provided in the header of the request.
     * @param request
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @GetMapping("/customers")
    public ResponseEntity<Customer> findCustomerById(HttpServletRequest request) throws AuthenticationException, UserException {
        log.info("Fetching a customer with the request token.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        return ResponseEntity.ok(customer);
    }

    /**
     * This endpoint updates the customer details.
     * @param request
     * @param customerRequestObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomer(HttpServletRequest request, @RequestBody CustomerRequestObj customerRequestObj)
            throws AuthenticationException, UserException {
        log.info("Authenticating a customer with the request token.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Updating the existing customer.");
        log.debug("Updating the existing customer with the update request object, {}.",customerRequestObj);
        Customer updatedCustomer= customerService.update(customer, customerRequestObj);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * This endpoint updates the customer address.
     * @param request
     * @param customerRequestObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/customer/address")
    public ResponseEntity<Customer> updateCustomerAddress(HttpServletRequest request, @RequestBody CustomerRequestObj customerRequestObj)
            throws AuthenticationException, UserException {
        log.info("Authenticating a customer with the request token, for customer address update.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Updating a customer Address with the received update request object.");
        log.debug("Customer address update request received with object, {}",customerRequestObj);
        Customer updatedCustomer= customerService.updateCustomerAddress(customer, customerRequestObj);
        return ResponseEntity.ok(updatedCustomer);
    }

    /**
     * This endpoint updates the customer address.
     * @param request
     * @param customerRequestObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/customer/creditCard")
    public ResponseEntity<Customer> updateCreditCard(HttpServletRequest request, @RequestBody CustomerRequestObj customerRequestObj) throws AuthenticationException, UserException {
        log.info("Authenticating a customer with the request token, for credit card update request.");
        Customer customer= isTokenValid(request.getHeader(Constants.HEADER_STRING));
        log.info("Updating credit card information of the customer.");
        log.debug("Updating credit card information of the customer, with request object as, {}.", customerRequestObj);
        Customer customerMono= customerService.updateCreditCard(customer, customerRequestObj);
        return ResponseEntity.ok(customerMono);
    }
}
