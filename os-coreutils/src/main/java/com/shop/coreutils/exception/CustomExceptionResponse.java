package com.shop.coreutils.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomExceptionResponse {

    LocalDateTime timestamp;

    String errorMessage;

    String path;

    String errorCode;
}
