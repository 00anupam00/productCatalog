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
    public void getAllCategories() throws Exception {
        Mockito.when(categoryRepository.findAll()).thenReturn(getCategoryList());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].categoryId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getCategory() throws Exception {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCategoryData()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Category 2"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllCategoriesInProduct() throws Exception {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        Mockito.when(categoryRepository.findByProducts(Mockito.anyInt())).thenReturn(Optional.of(getCategoryList().get(0)));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories/inProduct/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllCategoriesByDepartment() throws Exception {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        Mockito.when(categoryRepository.findAllByDepartment(Mockito.anyInt())).thenReturn(getCategoryList());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/categories/inDepartment/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}