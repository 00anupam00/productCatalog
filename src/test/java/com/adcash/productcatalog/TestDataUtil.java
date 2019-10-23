package com.adcash.productcatalog;

import com.adcash.productcatalog.dao.*;
import com.adcash.productcatalog.dto.*;
import com.adcash.productcatalog.util.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public abstract class TestDataUtil {

    protected List<CustomerRequestObj> getCustomerRequestObj(){
        List<CustomerRequestObj> customerRequestObjs= new ArrayList<>();

        CustomerRequestObj customerRequestObj= new CustomerRequestObj();
        customerRequestObj.setName("Anupam");
        customerRequestObj.setPassword("12345pwd");
        customerRequestObj.setEmail("anupam@gmail.com");
        customerRequestObj.setAddress1("Estonia");
        customerRequestObj.setCreditCard(new StringBuilder("xxxx-xxxx-xxxx-xxxx"));
        customerRequestObj.setAuthorities(Arrays.asList(Constants.Roles.ADMIN.name()));
        customerRequestObjs.add(customerRequestObj);

        CustomerRequestObj customerRequestObj1= new CustomerRequestObj();
        customerRequestObj1.setName("Anupam");
        customerRequestObj1.setPassword("12345pwd");
        customerRequestObj1.setEmail("anupam12@gmail.com");
        customerRequestObj1.setAddress1("Estonia");
        customerRequestObj1.setCreditCard(new StringBuilder("xxxx-xxxx-xxxx-xxxx"));
        customerRequestObj1.setAuthorities(Arrays.asList(Constants.Roles.USER.name()));
        customerRequestObjs.add(customerRequestObj1);

        return customerRequestObjs;
    }

    protected List<CustomerResponseObj> getCustomerResponseObj(){
        List<CustomerResponseObj> customerResponseObjs= new ArrayList<>();
        Customer customer= new Customer();
        customer.setCustomerId(1);
        customer.setName("Anupam");
        customer.setEmail("anupam@gmail.com");
        customer.setPassword("12345pwd");
        customer.setAddress1("Estonia");
        customer.setAuthorities(Constants.Roles.ADMIN.name());
        CustomerResponseObj customerRequestObj= new CustomerResponseObj();
        customerRequestObj.setCustomer(customer);
        customerResponseObjs.add(customerRequestObj);

        Customer customer1= new Customer();
        customer1.setCustomerId(1);
        customer1.setName("Anupam");
        customer1.setEmail("anupam12@gmail.com");
        customer1.setPassword("12345pwd");
        customer1.setAddress1("Estonia");
        customer1.setAuthorities(Constants.Roles.USER.name());
        CustomerResponseObj customerRequestObj1= new CustomerResponseObj();
        customerRequestObj1.setCustomer(customer1);
        customerResponseObjs.add(customerRequestObj1);
        return customerResponseObjs;
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

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setName("Product Name2");
        product2.setDescription("Product Description2");
        product2.setDiscountedPrice(104.2);
        product2.setPrice(204.2);

        products.add(product1);
        products.add(product2);
        return products;
    }

    protected List<ProductRequestObj> getProductRequestDataList(){
        List<ProductRequestObj> productRequestObjs= new ArrayList<>();
        ProductRequestObj product1 = new ProductRequestObj();
        product1.setProductId(1);
        product1.setName("Product Name1");
        product1.setDescription("Product Description1");
        product1.setDiscountedPrice(1034.2);
        product1.setPrice(2014.2);
        product1.setCategoryId(1);
        productRequestObjs.add(product1);

        ProductRequestObj product2 = new ProductRequestObj();
        product2.setProductId(1);
        product2.setName("Product Name1");
        product2.setDescription("Product Description1");
        product2.setDiscountedPrice(1034.2);
        product2.setPrice(2014.2);
        product2.setCategoryId(1);
        productRequestObjs.add(product2);

        return productRequestObjs;
    }
}
