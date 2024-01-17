package com.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    Long addressId;

    @Column
    String country;

    @Column
    String city;

    @Column
    String county;

    @Column(name = "zip_code")
    String zipCode;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "Street_and_number")
    String streetAndNumber;

    @Column(name = "additional_address")
    String additionalAddress;

    @Column(name = "observations")
    String observations;

    @Column(name = "is_primary")
    boolean isPrimary;

//    @Column(name = "address_type")
//    String addressType;


//    @Column(name = "user_id")
//    int userId;

//    @Override
//    public String toString() {
//        return "Address{" +
//                "addressId=" + addressId +
//                ", country='" + country + '\'' +
//                ", city='" + city + '\'' +
//                ", zipCode='" + zipCode + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", streetAndNumber='" + streetAndNumber + '\'' +
//                ", additionalAddress='" + additionalAddress + '\'' +
//                ", observations='" + observations + '\'' +
//                '}';
//    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user;
}
