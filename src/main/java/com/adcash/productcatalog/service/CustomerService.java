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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class CustomerService extends Validators implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private Session session;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Logger log= LoggerFactory.getLogger(this.getClass());

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
        Customer customer = new Customer();
        if(Objects.isNull(customerRequestObj.getAuthorities())){
            customer.setAuthorities(Constants.Roles.USER.name());
        }else{
            customer.setAuthorities(customerRequestObj.getAuthorities().toString());
        }
        customer.setName(customerRequestObj.getName());
        customer.setEmail(customerRequestObj.getEmail());
        customer.setPassword(customerRequestObj.getPassword());

        Customer savedCustomer= customerRepository.save(customer);

        String token= jwtTokenUtil.generateToken(savedCustomer.getEmail());
        CustomerResponseObj customerResponseObj= new CustomerResponseObj();
        customerResponseObj.setCustomer(savedCustomer);
        customerResponseObj.setAccessToken(token);
        customerResponseObj.setExpiresIn(jwtTokenUtil.getExpiredDateFromToken(token).toString());
        return customerResponseObj;
    }

    public Optional<Customer> findById(int customerId){
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> findByEmail(String email){
        return Optional.ofNullable(customerRepository.findByEmail(email));
    }

    public Customer update(Customer existingCustomer, CustomerRequestObj customerRequestObj) throws UserException {
        validateEmail(customerRequestObj.getEmail());
        existingCustomer.setEmail(customerRequestObj.getEmail());
        existingCustomer.setName(customerRequestObj.getName());
        existingCustomer.setMobPhone(customerRequestObj.getMobPhone());
        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerAddress(Customer existingCustomer, CustomerRequestObj customerRequestObj) throws UserException {
        existingCustomer.setAddress1(Objects.nonNull(customerRequestObj.getAddress1()) ? customerRequestObj.getAddress1() : existingCustomer.getAddress1());
        existingCustomer.setAddress2(Objects.nonNull(customerRequestObj.getAddress2()) ? customerRequestObj.getAddress2() : existingCustomer.getAddress2());
        existingCustomer.setCity(Objects.nonNull(customerRequestObj.getCity()) ? customerRequestObj.getCity() : existingCustomer.getCity());
        existingCustomer.setRegion(Objects.nonNull(customerRequestObj.getRegion()) ? customerRequestObj.getRegion() : existingCustomer.getRegion());
        existingCustomer.setPostalCode(Objects.nonNull(customerRequestObj.getPostalCode()) ? customerRequestObj.getPostalCode() : existingCustomer.getPostalCode());
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
        if(Objects.isNull(customer.getAuthorities())){
            throw new UserException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "Authority");
        }
        List<GrantedAuthority> authorities= jwtTokenUtil.getAuthoritiesList(customer.getAuthorities());
        if(authorities.contains(new SimpleGrantedAuthority(Constants.Roles.ADMIN.name()))
                || authorities.contains(new SimpleGrantedAuthority(Constants.Roles.USER.name()))){
            throw new UserException(Constants.AUT_01_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.AUT_01_MESSAGE, "Authority");
        }
        String token= jwtTokenUtil.generateToken(customer.getEmail());
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

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Customer customer= customerRepository.findByEmail(s);
        if(Objects.isNull(customer)){
            throw new UsernameNotFoundException("No username found with the mentioned email.");
        }
        return new User(
                customer.getEmail(),
                new BCryptPasswordEncoder().encode(customer.getPassword()),
                jwtTokenUtil.getAuthoritiesList(customer.getAuthorities()));
    }
}
