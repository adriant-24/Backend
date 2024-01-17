package com.shop.onlineshop.mapper;

import com.shop.onlineshop.dto.AddressDto;
import com.shop.onlineshop.dto.UserInfoDto;
import com.shop.onlineshop.entity.Address;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.entity.UserInfo;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static WebUser userToWebUser(User user){

        return WebUser.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .enabled(user.getEnabled())
                .userInfo(userInfoToUserInfoDto(user.getUserInfo()))
                .addresses(addressListToAddressDtoList(user.getAddresses()))
                .build();
    }

    public static User webUserToUser(WebUser webUser){

        return User.builder()
                .userId(webUser.getUserId())
                .username(webUser.getUserName())
                .password(webUser.getPassword())
                .addresses(addressDtoListToAddressList(webUser.getAddresses()))
                .userInfo(userInfoDtoToUserInfo(webUser.getUserInfo()))
                .build();
    }

    public static UserInfo userInfoDtoToUserInfo(UserInfoDto userInfoDto){

        return UserInfo.builder()
                .userInfoId(userInfoDto.getUserInfoId())
                .birthDate(userInfoDto.getBirthDate())
                .phone(userInfoDto.getPhone())
                .email(userInfoDto.getEmail())
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .build();
    }

    public static UserInfoDto userInfoToUserInfoDto(UserInfo userInfo){

        return UserInfoDto.builder()
                .userInfoId(userInfo.getUserInfoId())
                .birthDate(userInfo.getBirthDate())
                .phone(userInfo.getPhone())
                .email(userInfo.getEmail())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .build();
    }

    public static List<Address> addressDtoListToAddressList(List<AddressDto> addressDtoList){
        return addressDtoList!= null ?
                addressDtoList.stream()
                        .map(AddressMapper::addressDtoToAddress)
                        .collect(Collectors.toList()) : null;
    }

    public static List<AddressDto> addressListToAddressDtoList(List<Address> addressList){
        return addressList != null ?
                addressList.stream()
                        .map(AddressMapper::addressToAddressDto)
                        .collect(Collectors.toList()) : null;
    }


}
