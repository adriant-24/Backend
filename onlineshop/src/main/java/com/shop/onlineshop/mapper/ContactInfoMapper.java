package com.shop.onlineshop.mapper;

import com.shop.onlineshop.dto.ContactInfoDto;
import com.shop.onlineshop.entity.ContactInfo;

public class ContactInfoMapper {

    public static ContactInfoDto contactInfoToContactInfoDto(ContactInfo contactInfo){
        return ContactInfoDto.builder()
                .contactInfoId(contactInfo.getContactInfoId())
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .phoneNumber(contactInfo.getPhoneNumber())
                .email(contactInfo.getEmail())
                .country(contactInfo.getCountry())
                .city(contactInfo.getCity())
                .county(contactInfo.getCounty())
                .zipCode(contactInfo.getZipCode())
                .streetAndNumber(contactInfo.getStreetAndNumber())
              //  .isPrimary(contactInfo.isPrimary())
                .additionalAddress(contactInfo.getAdditionalAddress())
                .observations(contactInfo.getObservations())
                .infoType(contactInfo.getInfoType())
                .build();
    }

    public static ContactInfo contactInfoDtoToContactInfo(ContactInfoDto contactInfo){
        return ContactInfo.builder()
                .contactInfoId(contactInfo.getContactInfoId())
                .firstName(contactInfo.getFirstName())
                .lastName(contactInfo.getLastName())
                .phoneNumber(contactInfo.getPhoneNumber())
                .email(contactInfo.getEmail())
                .country(contactInfo.getCountry())
                .city(contactInfo.getCity())
                .county(contactInfo.getCounty())
                .zipCode(contactInfo.getZipCode())
                .streetAndNumber(contactInfo.getStreetAndNumber())
              //  .isPrimary(contactInfo.isPrimary())
                .additionalAddress(contactInfo.getAdditionalAddress())
                .observations(contactInfo.getObservations())
                .infoType(contactInfo.getInfoType())
                .build();
    }




}
