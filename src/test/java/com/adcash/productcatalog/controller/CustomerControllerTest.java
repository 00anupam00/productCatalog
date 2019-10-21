package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.MyappApplication;
import com.adcash.productcatalog.TestDataUtil;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerResponseObj;
import com.adcash.productcatalog.repository.CustomerRepository;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import com.adcash.productcatalog.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.StringUtils;
import org.hamcrest.Matchers;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

@RunWith(SpringRunner.class)
@Import({CustomerService.class, JwtTokenUtil.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class CustomerControllerTest extends TestDataUtil {

    @Autowired
    MockMvc mockClient;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    Session session;
    private String token;

    @Before
    public void setUp() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().getCustomer());
        String result= mockClient.perform(MockMvcRequestBuilders
                .post("/customers")
                .content(objectMapper.writeValueAsString(getCustomerRequestObj()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customerId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse()
                .getContentAsString();
        CustomerResponseObj responseObj= objectMapper.readValue(result, CustomerResponseObj.class);
        this.token = StringUtils.isNotEmpty(this.token) ? this.token : responseObj.getAccessToken();
    }

    @Test
    public void create() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().getCustomer());
        mockClient.perform(MockMvcRequestBuilders
                .post("/customers")
                .content(objectMapper.writeValueAsString(getCustomerRequestObj()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customerId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    //Negative test scenario for null password.
    @Test(expected = NestedServletException.class)
    public void login() throws Exception{
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().getCustomer());
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().getCustomer());
        mockClient.perform(MockMvcRequestBuilders
                .post("/customers/login")
                .content(objectMapper.writeValueAsString(getCustomerRequestObj()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Ignore
    public void facebookLogin() {
    }

    @Test
    public void findCustomerById() throws Exception {

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().getCustomer());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(MockMvcRequestBuilders
        .get("/customers")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8)
        .header(Constants.HEADER_STRING,this.token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.notNullValue()))
        .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateCustomer() throws Exception {

        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getUpdatedCustomerResponseObj().getCustomer());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        Mockito.when(session.load(Customer.class,1)).thenReturn(getCustomerResponseObj().getCustomer());
        mockClient.perform(MockMvcRequestBuilders
                .put("/customer")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(Constants.HEADER_STRING,this.token)
                .content(objectMapper.writeValueAsString(getUpdateCustomerPaylod())))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anupam Rakshit"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateCreditCard() throws Exception {
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getUpdatedCCResponseObj().getCustomer());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(MockMvcRequestBuilders
                .put("/customer/creditCard")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header(Constants.HEADER_STRING,this.token)
                .content(objectMapper.writeValueAsString(getCustomerRequestObj())))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creditCard").value(Matchers.notNullValue()))
                .andDo(MockMvcResultHandlers.print());
    }


}
