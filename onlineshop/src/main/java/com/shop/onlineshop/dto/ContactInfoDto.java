package com.shop.onlineshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ContactInfoDto {

    Long contactInfoId;

    @NotBlank(message = "is mandatory.")
    String firstName;
    @NotBlank(message = "is mandatory.")
    String lastName;
    @NotBlank(message = "is mandatory.")
    String phoneNumber;
    @NotBlank(message = "is mandatory.")
    @Email(message = "invalid email format.")
    String email;
    @NotBlank(message = "is mandatory.")
    String country;
    @NotBlank(message = "is mandatory.")
    String city;
    String county;
    @NotBlank(message = "is mandatory.")
    String zipCode;
    @NotBlank(message = "is mandatory.")
    String streetAndNumber;
//    @JsonProperty(value="isPrimary")
//    boolean isPrimary;

    String additionalAddress;

    String observations;

    String infoType;
}
