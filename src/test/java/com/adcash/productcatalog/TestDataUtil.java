package com.adcash.productcatalog;

import com.adcash.productcatalog.dao.*;
import com.adcash.productcatalog.dto.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public abstract class TestDataUtil {

    protected CustomerRequestObj getCustomerRequestObj(){
        CustomerRequestObj customerRequestObj= new CustomerRequestObj();
        customerRequestObj.setName("Anupam");
        customerRequestObj.setPassword("12345pwd");
        customerRequestObj.setEmail("anupam@gmail.com");
        customerRequestObj.setAddress1("Estonia");
        customerRequestObj.setCreditCard(new StringBuilder("xxxx-xxxx-xxxx-xxxx"));
        return customerRequestObj;
    }

    protected CustomerResponseObj getCustomerResponseObj(){
        Customer customer= new Customer();
        customer.setCustomerId(1);
        customer.setName("Anupam");
        customer.setEmail("anupam@gmail.com");
        customer.setPassword("12345pwd");
        customer.setAddress1("Estonia");
        CustomerResponseObj customerRequestObj= new CustomerResponseObj();
        customerRequestObj.setCustomer(customer);
        return customerRequestObj;
    }

    protected CustomerRequestObj getUpdateCustomerPaylod(){
        CustomerRequestObj customerRequestObj= new CustomerRequestObj();
        customerRequestObj.setCustomerId(1);
        customerRequestObj.setName("Anupam Rakshit");
        customerRequestObj.setEmail("anupam@gmail.com");
        customerRequestObj.setAddress1("Estonia updated");
        return customerRequestObj;
    }
    protected CustomerResponseObj getUpdatedCustomerResponseObj(){
        Customer customer= new Customer();
        customer.setCustomerId(1);
        customer.setName("Anupam Rakshit");
        customer.setEmail("anupam@gmail.com");
        customer.setAddress1("Estonia");
        CustomerResponseObj customerResponseObj= new CustomerResponseObj();
        customerResponseObj.setCustomer(customer);
        return customerResponseObj;
    }

    protected CustomerResponseObj getUpdatedCCResponseObj() {
        Customer customer= new Customer();
        customer.setCustomerId(1);
        customer.setCreditCard("123498763456");
        CustomerResponseObj customerRequestObj= new CustomerResponseObj();
        customerRequestObj.setCustomer(customer);
        return customerRequestObj;
    }

    protected List<Category> getCategoryList(){
        List<Category> categories= new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setName("Category 1");
        category1.setDescription("Description 1");
        category1.setProducts(new HashSet<>());
        categories.add(category1);
        Category category2 = new Category();
        category2.setCategoryId(1);
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setProducts(new HashSet<>());
        categories.add(category2);
        return categories;
    }
    protected Category getCategoryData(){
        return getCategoryList().get(1);
    }

    protected List<Product> getProductResponseList(){

        List<Product> products= new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setName("Product Name1");
        product1.setDescription("Product Description1");
        product1.setDiscountedPrice(1034.2);
        product1.setPrice(2014.2);
        product1.setThumbnail("blah blah blah...");

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setName("Product Name2");
        product2.setDescription("Product Description2");
        product2.setDiscountedPrice(104.2);
        product2.setPrice(204.2);
        product2.setThumbnail("blah blah blah...");

        products.add(product1);
        products.add(product2);
        return products;
    }
}
