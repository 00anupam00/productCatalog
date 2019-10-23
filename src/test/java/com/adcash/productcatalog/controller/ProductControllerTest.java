package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.MyappApplication;
import com.adcash.productcatalog.TestDataUtil;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dao.Product;
import com.adcash.productcatalog.dto.CustomerResponseObj;
import com.adcash.productcatalog.dto.ProductRequestObj;
import com.adcash.productcatalog.repository.CategoryRepository;
import com.adcash.productcatalog.repository.CustomerRepository;
import com.adcash.productcatalog.repository.ProductRepository;
import com.adcash.productcatalog.service.CustomerService;
import com.adcash.productcatalog.service.ProductService;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RunWith(SpringRunner.class)
@Import({CustomerService.class, ProductService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyappApplication.class
)
@AutoConfigureMockMvc
public class ProductControllerTest extends TestDataUtil {

    @Autowired
    MockMvc mockClient;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CustomerRepository customerRepository;
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
    public void findAll() throws Exception {
        Mockito.when(productRepository.findAll()).thenReturn(getProductResponseList());
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProduct() throws Exception {
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().get(0)));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllProductsInCategory() throws Exception {
        Mockito.when(productRepository.findAllByCategory(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList()));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/inCategory/3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows.[0].productId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createProduct() throws Exception{
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(getProductResponseList().get(0));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.admin_token)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createProductUnauthorized() throws Exception{
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(getProductResponseList().get(0));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(1).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.user_token)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProduct() throws Exception{
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyInt());
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .delete("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.admin_token)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProduct() throws Exception{
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().get(0)));
        Mockito.when(categoryRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getCategoryData()));
        Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(getCustomerResponseObj().get(0).getCustomer());
        mockClient.perform(
                MockMvcRequestBuilders
                        .put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getProductRequestDataList().get(0)))
                        .header(Constants.HEADER_STRING, this.admin_token)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

}