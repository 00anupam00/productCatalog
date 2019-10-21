package com.adcash.productcatalog.util;

public class Constants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRED_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "USER-KEY";
    public static final String ORDER_STATUS_SHIPPED = "SHIPPED";
    public static final String ORDER_STATUS_PREPARE = "PREPARING TO SHIP";
    public static final String CURRENCY = "$";
    public static final String PAYMENT_SUCCESS = "Payment Successful";

    //Apis to give permissions without authentication headers
    public static final String SAVE_CUSTOMERS = "/customers";
    public static final String LOGIN_CUSTOMERS = "/customers/login";
    public static final String GET_ALL_TAXES = "/tax";
    public static final String GET_TAX = "/tax/*";
    public static final String ALL_SHIPPING_REGIONS = "/shipping/regions";
    public static final String SHIPPING_REGION = "/shipping/regions/*";
    public static final String PRODUCTS_API = "/products";
    public static final String ALL_PRODUCTS_API = "/products/**/*";
    public static final String DEPARTMENTS_API = "/departments";
    public static final String ALL_DEPARTMENTS_API = "/departments/**/*";
    public static final String CATEGORY_API = "/categories";
    public static final String ALL_CATEGORY_API = "/categories/**/*";
    public static final String ATTRIBUTES_API = "/attributes";
    public static final String ALL_ATTRIBUTES_API = "/attributes/**/*";

    //Exception codes
    public static final String AUT_01_CODE = "AUT_01";
    public static final String AUT_01_MESSAGE = "Authorization code is empty.";
    public static final String AUT_02_CODE = "AUT_02";
    public static final String AUT_02_MESSAGE = "Access Unauthorized.";
    public static final String PAG_01_CODE = "PAG_01";
    public static final String PAG_01_MESSAGE = "The order is not matched 'field,(DESC|ASC)'.";
    public static final String PAG_02_CODE = "PAG_02";
    public static final String PAG_02_MESSAGE = "The field of order is not allowed sorting.";
    public static final String USR_01_CODE = "USR_01";
    public static final String USR_01_MESSAGE = "Email or Password is invalid.";
    public static final String USR_02_CODE = "USR_02";
    public static final String USR_02_MESSAGE = "The field(s) are/is required.";
    public static final String USR_03_CODE = "USR_03";
    public static final String USR_03_MESSAGE = "The email is invalid.";
    public static final String USR_04_CODE = "USR_04";
    public static final String USR_04_MESSAGE = "The email already exists.";
    public static final String USR_05_CODE = "USR_05";
    public static final String USR_05_MESSAGE = "The email doesn't exist.";
    public static final String USR_06_CODE = "USR_06";
    public static final String USR_06_MESSAGE = "This is an invalid phone number.";
    public static final String USR_07_CODE = "USR_07";
    public static final String USR_07_MESSAGE = "This is too long <FIELD NAME>.";
    public static final String USR_08_CODE = "USR_08";
    public static final String USR_08_MESSAGE = "This is an invalid Credit Card.";
    public static final String USR_09_CODE = "USR_09";
    public static final String USR_09_MESSAGE = "The Shipping Region ID is not number.";
    public static final String CAT_01_CODE = "CAT_01";
    public static final String CAT_01_MESSAGE = "Don't exist category with this ID.";
    public static final String DEP_01_CODE = "DEP_01";
    public static final String DEP_01_MESSAGE = "The ID is not a number.";
    public static final String DEP_02_CODE = "DEP_02";
    public static final String DEP_02_MESSAGE = "Don'exist department with this ID.";

}
