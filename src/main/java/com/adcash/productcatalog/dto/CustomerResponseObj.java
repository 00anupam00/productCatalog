package com.adcash.productcatalog.dto;

import com.adcash.productcatalog.dao.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerResponseObj implements Serializable {

    private Customer customer;
    private String accessToken;
    private String expiresIn;
}
