package com.adcash.productcatalog.service;

import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerRequestObj;
import com.adcash.productcatalog.dto.CustomerResponseObj;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.JwtTokenUtil;
import com.adcash.productcatalog.util.Validators;
import com.adcash.productcatalog.exceptions.UserException;
import com.adcash.productcatalog.repository.CustomerRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CustomerService extends Validators {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ShippingRegionRepository shippingRegionRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private Session session;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostConstruct
    private void setUp(){
        EntityManager entityManager= this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        this.session= entityManager.unwrap(Session.class);
    }

    public CustomerResponseObj save(CustomerRequestObj customerRequestObj) throws UserException {
        validateEmail(customerRequestObj.getEmail());
        errorIfEmailExists(customerRequestObj);
        isPasswordValid(customerRequestObj.getPassword());
        validateName(customerRequestObj.getName());
        ShippingRegion shippingRegion= this.session.load(ShippingRegion.class,1);
        shippingRegion.setShippingRegionId(1);
        Customer customer = new Customer();
        customer.setName(customerRequestObj.getName());
        customer.setEmail(customerRequestObj.getEmail());
        customer.setPassword(customerRequestObj.getPassword());
        customer.setShippingRegion(shippingRegion);

        Customer savedCustomer= customerRepository.save(customer);

        String token= jwtTokenUtil.generateToken(savedCustomer.getCustomerId());
        CustomerResponseObj customerResponseObj= new CustomerResponseObj();
        customerResponseObj.setCustomer(savedCustomer);
        customerResponseObj.setAccessToken(token);
        customerResponseObj.setExpiresIn(jwtTokenUtil.getExpiredDateFromToken(token).toString());
        return customerResponseObj;
    }

    public Optional<Customer> findById(int customerId){
        return customerRepository.findById(customerId);
    }

    public Customer update(Customer existingCustomer, CustomerRequestObj customerRequestObj) throws UserException {
        validateEmail(customerRequestObj.getEmail());
        isPhoneNumberValid(customerRequestObj.getMobPhone());
        existingCustomer.setEmail(customerRequestObj.getEmail());
        existingCustomer.setName(customerRequestObj.getName());
        existingCustomer.setDayPhone(customerRequestObj.getDayPhone());
        existingCustomer.setEvePhone(customerRequestObj.getEvePhone());
        existingCustomer.setMobPhone(customerRequestObj.getMobPhone());
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerAddress(Customer existingCustomer, CustomerRequestObj customerRequestObj) throws UserException {
        isShippingRegionIdValid(customerRequestObj.getShippingRegionId());
        existingCustomer.setAddress1(Objects.nonNull(customerRequestObj.getAddress1()) ? customerRequestObj.getAddress1() : existingCustomer.getAddress1());
        existingCustomer.setAddress2(Objects.nonNull(customerRequestObj.getAddress2()) ? customerRequestObj.getAddress2() : existingCustomer.getAddress2());
        existingCustomer.setCity(Objects.nonNull(customerRequestObj.getCity()) ? customerRequestObj.getCity() : existingCustomer.getCity());
        existingCustomer.setRegion(Objects.nonNull(customerRequestObj.getRegion()) ? customerRequestObj.getRegion() : existingCustomer.getRegion());
        existingCustomer.setPostalCode(Objects.nonNull(customerRequestObj.getPostalCode()) ? customerRequestObj.getPostalCode() : existingCustomer.getPostalCode());
        existingCustomer.setShippingRegion(shippingRegionRepository.findById(customerRequestObj.getShippingRegionId())
                .orElseThrow(()-> new UserException(Constants.USR_09_CODE, HttpStatus.NOT_ACCEPTABLE.value(), Constants.USR_09_MESSAGE, "shippingId")));
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCreditCard(Customer customer, CustomerRequestObj customerRequestObj) throws UserException {
        validateCreditCard(customerRequestObj.getCreditCard());
        customer.setCreditCard(String.valueOf(customerRequestObj.getCreditCard()));
        return customerRepository.save(customer);
    }

    public CustomerResponseObj authenticateLogin(CustomerRequestObj customerRequestObj) throws UserException {

       validateEmail(customerRequestObj.getEmail());
       isPasswordValid(customerRequestObj.getPassword());

        Customer customer= customerRepository.findByEmail(customerRequestObj.getEmail());
        if(Objects.isNull(customer)){
            throw new UserException(Constants.USR_05_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_05_MESSAGE, "Email");
        }
        if(!customerRequestObj.getPassword().equals(customer.getPassword())){
            throw new UserException(Constants.USR_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_01_MESSAGE, "Email|Password");
        }
        String token= jwtTokenUtil.generateToken(customer.getCustomerId());
        CustomerResponseObj customerResponseObj= new CustomerResponseObj();
        customerResponseObj.setCustomer(customer);
        customerResponseObj.setAccessToken(token);
        customerResponseObj.setExpiresIn(jwtTokenUtil.getExpiredDateFromToken(token).toString());
        return customerResponseObj;
    }

    private void errorIfEmailExists(CustomerRequestObj customerRequestObj) throws UserException {
        Customer customer= customerRepository.findByEmail(customerRequestObj.getEmail());
        if(Objects.nonNull(customer)){
            throw new UserException(Constants.USR_04_CODE, HttpStatus.CONFLICT.value(), Constants.USR_04_MESSAGE, "Email");
        }
    }
}
