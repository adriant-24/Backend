package com.shop.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebUser {

    Long userId;

    String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    short enabled;

    List<AddressDto> addresses;

    UserInfoDto userInfo;
}
