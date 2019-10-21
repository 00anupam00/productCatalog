package com.adcash.productcatalog;

import com.adcash.productcatalog.dao.*;
import com.adcash.productcatalog.dto.*;
import com.turingapp.myapp.dao.*;
import com.turingapp.myapp.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.sql.Timestamp;
import java.time.Instant;
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

    protected DepartmentResponseObj getDepartment(){
        DepartmentResponseObj department1= new DepartmentResponseObj();
        department1.setDepartment_id(1);
        department1.setDescription("CSE");
        department1.setName("Department 1");
        return department1;
    }
    protected List<Department> getDepartmentList(){
        List<Department> departments= new ArrayList<>();
        Department department1= new Department();
        department1.setDepartmentId(1);
        department1.setDescription("CSE");
        department1.setName("Department 1");
        departments.add(department1);

        Department department2= new Department();
        department2.setDepartmentId(2);
        department2.setDescription("ECE");
        department2.setName("Department 2");
        departments.add(department2);
        return departments;
    }

    protected List<Attribute> getAttributeList(){
        List<Attribute> attributes= new ArrayList<>();

        Attribute attribute1= new Attribute();
        attribute1.setAttributeId(1);
        attribute1.setName("Attribute1");
        attributes.add(attribute1);

        Attribute attribute2= new Attribute();
        attribute2.setAttributeId(2);
        attribute2.setName("Attribute2");
        attributes.add(attribute2);
        return attributes;
    }


    protected Attribute getAttribute() {
        Attribute attribute1= new Attribute();
        attribute1.setAttributeId(100);
        attribute1.setName("Attribute1");
        return attribute1;
    }

    protected List<AttributeValue> getAllAttributeValues(){
        List<AttributeValue> attributeValues= new ArrayList<>();

        AttributeValue attributeValue1= new AttributeValue();
        attributeValue1.setAttribute(new Attribute());
        attributeValue1.setAttributeValueId(1);
        attributeValue1.setValue("Attribute value 1");
        attributeValues.add(attributeValue1);

        AttributeValue attributeValue2= new AttributeValue();
        attributeValue2.setAttribute(new Attribute());
        attributeValue2.setAttributeValueId(1);
        attributeValue2.setValue("Attribute value 2");
        attributeValues.add(attributeValue2);
        return attributeValues;
    }

    protected List<AttributeResponseObj> getAttributeResponseList(){
        List<AttributeResponseObj> attributes= new ArrayList<>();

        AttributeResponseObj attribute1= new AttributeResponseObj();
        attribute1.setAttribute_value("M");
        attribute1.setAttributeName("Size");
        attribute1.setAttribute_value_id(1);
        attributes.add(attribute1);

        AttributeResponseObj attribute2= new AttributeResponseObj();
        attribute2.setAttribute_value("L");
        attribute2.setAttributeName("Size");
        attribute2.setAttribute_value_id(2);
        attributes.add(attribute2);
        return attributes;
    }

    protected List<Category> getCategoryList(){
        List<Category> categories= new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setDepartment(new Department());
        category1.setName("Category 1");
        category1.setDescription("Description 1");
        category1.setProducts(new HashSet<>());
        categories.add(category1);
        Category category2 = new Category();
        category2.setCategoryId(1);
        category2.setDepartment(new Department());
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setProducts(new HashSet<>());
        categories.add(category2);
        return categories;
    }
    protected Category getCategoryData(){
        return getCategoryList().get(1);
    }

    protected OrderRequestObj getOrderData(){
        return new OrderRequestObj("1",1,1);
    }
    protected Orders getOrdersData(){
        Orders order= new Orders();
        order.setOrderId(100);
        return order;
    }

    protected Optional<Set<Orders>> getListOfOrdersData(){
        Set<Orders> orders= new HashSet<>();
        Customer customer =new Customer();
        customer.setName("Anupam");

        Orders orders1= new Orders();
        orders1.setOrderId(100);
        orders1.setTotalAmount(20.40);
        orders1.setCreatedOn(Timestamp.from(Instant.now()));
        orders1.setShippedOn(Timestamp.from(Instant.now()));
        orders1.setCustomer(customer);
        orders.add(orders1);

        Orders orders2= new Orders();
        orders2.setOrderId(1001);
        orders2.setTotalAmount(201.40);
        orders2.setCreatedOn(Timestamp.from(Instant.now()));
        orders2.setShippedOn(Timestamp.from(Instant.now()));
        orders2.setCustomer(customer);
        orders.add(orders2);
        return Optional.of(orders);
    }

    protected Orders getShortOrderData(){
        Customer customer= getCustomerResponseObj().getCustomer();
        Orders orderResponseObj = new Orders();
        orderResponseObj.setOrderId(100);
        orderResponseObj.setShippedOn(Timestamp.from(Instant.now()));
        orderResponseObj.setCreatedOn(Timestamp.from(Instant.now()));
        orderResponseObj.setTotalAmount(201.40);
        orderResponseObj.setCustomer(customer);
        return orderResponseObj;
    }

    protected Page<Product> getProductResponseList(){
        OrderDetail orderDetail= new OrderDetail();
        orderDetail.setAttributes("Color, Black");
        orderDetail.setItemId(1);
        orderDetail.setQuantity(3);

        List<Product> products= new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setName("Product Name1");
        product1.setDescription("Product Description1");
        product1.setDiscountedPrice(1034.2);
        product1.setPrice(2014.2);
        product1.setThumbnail("blah blah blah...");
        product1.setOrderDetail(orderDetail);

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setName("Product Name2");
        product2.setDescription("Product Description2");
        product2.setDiscountedPrice(104.2);
        product2.setPrice(204.2);
        product2.setThumbnail("blah blah blah...");
        product2.setOrderDetail(orderDetail);

        PaginationMetadata paginationMetadata= new PaginationMetadata();
        paginationMetadata.setTotalPages(5);
        paginationMetadata.setCurrentPageSize(20);
        paginationMetadata.setCurrentPage(1);
        paginationMetadata.setTotalRecords(100);

        products.add(product1);
        products.add(product2);

        Page<Product> page= new PageImpl<>(products);

        return page;
    }

    protected SearchObj getSearchObj(){
        SearchObj searchObj = new SearchObj();
        searchObj.setPage(2);
        searchObj.setLimit(1);
        searchObj.setDescription_length(20);
        return searchObj;
    }

    protected Review getReviewRequestObj(){
        Review review= new Review();
        review.setReviewId(1);
        review.setReview("The product was awesome.");
        review.setRating(4);
        review.setProduct(getProductResponseList().get().findFirst().get());
        review.setCustomer(getCustomerResponseObj().getCustomer());
        return review;
    }

    protected Optional<List<Review>> getReviewList(){
        List<Review> reviews= new ArrayList<>();
        reviews.add(getReviewRequestObj());
        return Optional.of(reviews);
    }

    protected List<ShippingRegion> getShippingRegionList(){
        List<ShippingRegion> shippingRegions= new ArrayList<>();
        ShippingRegion shippingRegion1= new ShippingRegion();
        shippingRegion1.setShippingRegionId(1);
        shippingRegion1.setShippingRegion("Europe");
        ShippingRegion shippingRegion2= new ShippingRegion();
        shippingRegion2.setShippingRegionId(2);
        shippingRegion2.setShippingRegion("India");
        shippingRegions.add(shippingRegion1);
        shippingRegions.add(shippingRegion2);
        return shippingRegions;
    }

    protected Set<Shipping> getShippingSet(){
        Set<Shipping> shippingSet= new HashSet<>();
        Shipping shipping1 = new Shipping();
        shipping1.setShippingId(1);
        shipping1.setShippingCost(10.2);
        shipping1.setShippingRegion(getShippingRegionList().get(0));
        shipping1.setShippingType("Next Day Delivery");
        Shipping shipping2 = new Shipping();
        shipping2.setShippingId(2);
        shipping2.setShippingCost(20.2);
        shipping2.setShippingRegion(getShippingRegionList().get(1));
        shipping2.setShippingType("By air (7 days, $25)");
        shippingSet.add(shipping1);
        shippingSet.add(shipping2);
        return shippingSet;
    }

    protected OrderDetail getOrderDetailData(){
        OrderDetail orderDetail= new OrderDetail();
        orderDetail.setItemId(1);
        orderDetail.setOrder(getOrdersData());
        orderDetail.setAttributes("Size, M");
        orderDetail.setProductName(getProductResponseList().getContent().get(0).getName());
        orderDetail.setQuantity(2);
        orderDetail.setUnitCost(10.2);
        orderDetail.setProduct(getProductResponseList().getContent().get(0));
        return orderDetail;
    }

    protected CartRequestObj getCartRequestObj(){
        CartRequestObj cartRequestObj= new CartRequestObj();
        cartRequestObj.setAttributes("Size, M");
        cartRequestObj.setCartId("123456");
        cartRequestObj.setProductId(1);
        cartRequestObj.setQuantity(10);
        return cartRequestObj;
    }

    protected ShoppingCart getShoppingCart(){
        ShoppingCart shoppingCart= new ShoppingCart();
        shoppingCart.setCartId(getCartRequestObj().getCartId());
        shoppingCart.setProductId(1);
        shoppingCart.setItemId(getOrderDetailData().getItemId());
        shoppingCart.setOrderDetails(getOrderDetailData());
        shoppingCart.setAttributes(getOrderDetailData().getAttributes());

        return shoppingCart;
    }

    protected List<Tax> getTaxDataList(){
        List<Tax> taxes= new ArrayList<>();
        Tax tax1 = new Tax();
        tax1.setTaxId(1);
        tax1.setTaxPercentage(8.5);
        tax1.setTaxType("Sales Tax at 8.5%");
        Tax tax2 = new Tax();
        tax2.setTaxId(2);
        tax2.setTaxPercentage(0);
        tax2.setTaxType("No Tax");
        taxes.add(tax1);
        taxes.add(tax2);

        return taxes;
    }
}
