package com.shop.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AddressDto {

    Long addressId;
    @NotBlank(message = "is mandatory.")
    String country;
    @NotBlank(message = "is mandatory.")
    String city;

    String county;
    @NotBlank(message = "is mandatory.")
    String zipCode;
    @NotBlank(message = "is mandatory.")
    String phoneNumber;
    @NotBlank(message = "is mandatory.")
    String streetAndNumber;
    @JsonProperty(value="isPrimary")
    boolean isPrimary;

    String additionalAddress;

    String observations;

    String addressType;

    Long userId;
}
