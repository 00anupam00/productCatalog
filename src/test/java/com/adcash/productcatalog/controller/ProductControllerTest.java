package com.adcash.productcatalog.controller;

import com.adcash.productcatalog.MyappApplication;
import com.adcash.productcatalog.TestDataUtil;
import com.adcash.productcatalog.dao.Customer;
import com.adcash.productcatalog.dto.CustomerResponseObj;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    ReviewRepository reviewRepository;
    @MockBean
    CategoryRepository categoryRepository;

    @MockBean
    CustomerRepository customerRepository;
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
    public void findAll() throws Exception {
        Mockito.when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(getProductResponseList());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getSearchObj()))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paginationMetadata.currentPage").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows").value(Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void searchProduct() throws Exception{
        Mockito.when(productRepository.findAll(Mockito.any(Pageable.class))).thenReturn(getProductResponseList());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/search")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("query_String","")
                        .param("all_words","on")
                        .content(objectMapper.writeValueAsString(getSearchObj()))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProduct() throws Exception {
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().getContent().get(0)));
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllProductsInCategory() throws Exception {
        Mockito.when(productRepository.findAllByCategory(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().getContent()));
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/inCategory/3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getSearchObj()))
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows.[0].productId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllProductInDepartment() throws Exception {
        Mockito.when(categoryRepository.findAllByDepartment(Mockito.anyInt())).thenReturn(getCategoryList());
        Mockito.when(productRepository.findAllByCategory(Mockito.anyInt())).thenReturn(Optional.ofNullable(getProductResponseList().getContent()));
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/inDepartment/3")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getSearchObj()))
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rows.[0].productId").exists())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getReviewsOfProduct() throws Exception {
        Mockito.when(reviewRepository.findAllReviewsByProduct(Mockito.anyInt())).thenReturn(getReviewList());
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .get("/products/2/reviews")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void postReviewForProduct() throws Exception {
        Mockito.when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(getReviewRequestObj());
        Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getProductResponseList().getContent().get(0)));
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCustomerResponseObj().getCustomer()));
        mockClient.perform(
                MockMvcRequestBuilders
                        .post("/products/2/reviews")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(getReviewRequestObj()))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .header(Constants.HEADER_STRING, this.token))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Anupam"))
                .andDo(MockMvcResultHandlers.print());
    }
}