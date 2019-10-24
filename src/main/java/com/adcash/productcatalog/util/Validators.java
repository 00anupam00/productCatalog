package com.adcash.productcatalog.util;

import com.adcash.productcatalog.exceptions.UserException;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public abstract class Validators {

    protected boolean validateEmail(String email) throws UserException {
        if(Objects.isNull(email)){
            throw new UserException(Constants.USR_02_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_02_MESSAGE, "Email");
        }
        if(!EmailValidator.getInstance().isValid(email)){
            throw new UserException(Constants.USR_03_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_03_MESSAGE, "Email");
        }
        if(email.length() > 100){
            throw new UserException(Constants.USR_07_CODE, HttpStatus.NOT_ACCEPTABLE.value(),
                    Constants.USR_07_MESSAGE.replace("<FIELD NAME>", "email"), "email");
        }
        return true;
    }
    protected boolean isPasswordValid(String pass) throws UserException {
        if(Objects.isNull(pass)){
            throw new UserException(Constants.USR_02_CODE, HttpStatus.UNAUTHORIZED.value(), Constants.USR_02_MESSAGE, "Password");
        }
        if(pass.length() > 100){
            throw new UserException(Constants.USR_07_CODE, HttpStatus.NOT_ACCEPTABLE.value(),
                    Constants.USR_07_MESSAGE.replace("<FIELD NAME>", "password"), "password");
        }
        return true;
    }

    protected boolean validateCreditCard(StringBuilder creditCard) throws UserException {
        if(Objects.isNull(creditCard) || String.valueOf(creditCard).length() != 16)
            throw new UserException(Constants.USR_08_CODE, HttpStatus.NOT_ACCEPTABLE.value(), Constants.USR_08_MESSAGE, "credit_card");
        return true;
    }

    protected boolean isPhoneNumberValid(String phone) throws UserException {
        if (Objects.isNull(phone)) {
            throw new UserException(Constants.USR_06_CODE, HttpStatus.NOT_ACCEPTABLE.value(), Constants.USR_06_MESSAGE, "phone");
        }
        if(phone.length() > 100){
            throw new UserException(Constants.USR_07_CODE, HttpStatus.NOT_ACCEPTABLE.value(),
                    Constants.USR_07_MESSAGE.replace("<FIELD NAME>", "mob_phone"), "mob_phone");
        }
        return true;
    }

    protected boolean validateName(String name) throws UserException {
        if(Objects.nonNull(name) && name.length() > 50){
            throw new UserException(Constants.USR_07_CODE, HttpStatus.NOT_ACCEPTABLE.value(),
                    Constants.USR_07_MESSAGE.replace("<FIELD NAME>", "customer_name"), "customer_name");
        }
        return true;
    }

}
