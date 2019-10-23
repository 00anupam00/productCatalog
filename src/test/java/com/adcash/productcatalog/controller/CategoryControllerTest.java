package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.MyappApplication;
import com.adcash.productcatalog.TestDataUtil;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerResponseObj;
import com.adcash.productcatalog.repository.CategoryRepository;
import com.adcash.productcatalog.repository.CustomerRepository;
import com.adcash.productcatalog.service.CategoryService;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.util.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
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

import java.util.Optional;


@RunWith(SpringRunner.class)
@Import({CustomerService.class, CategoryService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class CategoryControllerTest extends TestDataUtil {

    @Autowired
    MockMvc mockClient;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    CategoryRepository categoryRepository;

    private String user_token;
    private String admin_token;

    @Before
    public void setUp() throws Exception {
        //For USER users.
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().get(1).getCustomer());
        String result= mockClient.perform(MockMvcRequestBuilders
                .post("/customers")
                .content(objectMapper.writeValueAsString(getCustomerRequestObj().get(1)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customerId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse()
                .getContentAsString();
        CustomerResponseObj responseObj= objectMapper.readValue(result, CustomerResponseObj.class);
        this.user_token = StringUtils.isNotEmpty(this.user_token) ? this.user_token : responseObj.getAccessToken();

        //For ADMIN users.
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        String admin_result= mockClient.perform(MockMvcRequestBuilders
                .post("/customers")
                .content(objectMapper.writeValueAsString(getCustomerRequestObj().get(0)))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.customerId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn().getResponse()
                .getContentAsString();
        CustomerResponseObj responseObjA= objectMapper.readValue(admin_result, CustomerResponseObj.class);
        this.admin_token = StringUtils.isNotEmpty(this.admin_token) ? this.admin_token : responseObjA.getAccessToken();
    }

    @Test
    public void getAllCategories() throws Exception {
        Mockito.when(categoryRepository.findAll()).thenReturn(getCategoryList());
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.admin_token))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].category_id").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCategory() throws Exception {
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCategoryData()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.admin_token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 2"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}