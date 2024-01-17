package com.shop.onlineshop.mapper;

import com.shop.onlineshop.dto.AddressDto;
import com.shop.onlineshop.entity.Address;

public class AddressMapper {

    public static AddressDto addressToAddressDto(Address address){
        return AddressDto.builder()
                .addressId(address.getAddressId())
                .city(address.getCity())
                .country(address.getCountry())
                .county(address.getCounty())
                .additionalAddress(address.getAdditionalAddress())
                .zipCode(address.getZipCode())
                .observations(address.getObservations())
                .streetAndNumber(address.getStreetAndNumber())
                .phoneNumber(address.getPhoneNumber())
                .isPrimary(address.isPrimary())
              //  .addressType(address.getAddressType())
                .userId(address.getUser().getUserId())
                .build();
    }

    public static Address addressDtoToAddress(AddressDto addressDto){
        return Address.builder()
                .addressId(addressDto.getAddressId())
                .city(addressDto.getCity())
                .country(addressDto.getCountry())
                .county(addressDto.getCounty())
                .additionalAddress(addressDto.getAdditionalAddress())
                .zipCode(addressDto.getZipCode())
                .observations(addressDto.getObservations())
                .streetAndNumber(addressDto.getStreetAndNumber())
                .phoneNumber(addressDto.getPhoneNumber())
                .isPrimary(addressDto.isPrimary())
             //   .addressType(addressDto.getAddressType())
                .build();
    }
}
