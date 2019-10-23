package com.adcash.productcatalog.util;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.exceptions.AuthenticationException;
import com.adcash.productcatalog.exceptions.UserException;
import com.adcash.productcatalog.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public abstract class TokenValidator {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CustomerService customerService;

    public Customer isTokenValid(String token) throws AuthenticationException, UserException {
        Customer customer;
        String email= jwtTokenUtil.getCustomerEmailFromKey(token);
        if(!jwtTokenUtil.isTokenValid(token)){
            throw new AuthenticationException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "token");
        }
        customer= customerService.findByEmail(email)
                .orElseThrow(() -> new UserException(Constants.AUT_02_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_02_MESSAGE, "customerId"));
        return customer;
    }
}
