package com.shop.onlineshop.dto;

import com.shop.onlineshop.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserInfoDto {

    Long userInfoId;
    @NotBlank(message = "is mandatory.")
    String firstName;
    @NotBlank(message = "is mandatory.")
    String lastName;
    @NotBlank(message = "is mandatory.")
    String phone;
    @NotBlank(message = "is mandatory.")
    @Email(message = "invalid email format.")
    String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "is mandatory.")
    Date birthDate;

    User user;
}
