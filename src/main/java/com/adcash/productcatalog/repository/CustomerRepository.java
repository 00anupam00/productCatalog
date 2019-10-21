package com.adcash.productcatalog.repository;

import com.adcash.productcatalog.dao.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    Customer findByEmail(String email);
}
