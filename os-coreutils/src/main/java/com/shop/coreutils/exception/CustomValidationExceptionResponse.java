package com.shop.coreutils.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class CustomValidationExceptionResponse extends CustomExceptionResponse {
    Map<String, String> validationMessages;

   public CustomValidationExceptionResponse (LocalDateTime timestamp,
                                             String errorMessage,
                                             String path,
                                             String errorCode,
                                             Map<String,String> validationMessages){
        super(timestamp,errorMessage,path,errorCode);
        this.validationMessages = validationMessages;
    }
}
