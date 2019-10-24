package com.adcash.productcatalog.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ErrorResponseObj {

    private String code;
    private HttpStatus status;
    private String message;
    private String field;
}
