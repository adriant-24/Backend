package com.shop.ordersservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_info_id")
    Long contactInfoId;

    @Column(name= "first_name")
    String firstName;

    @Column(name= "last_name")
    String lastName;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column
    String email;

    @Column
    String country;

    @Column
    String city;

    @Column
    String county;

    @Column(name = "zip_code")
    String zipCode;


    @Column(name = "Street_and_number")
    String streetAndNumber;

    @Column(name = "additional_address")
    String additionalAddress;

    @Column(name = "observations")
    String observations;

//    @Column(name = "is_primary")
//    boolean isPrimary;

    @Column(name = "info_type")
    String infoType;
}
