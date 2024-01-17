package com.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User_Info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_info_id")
    Long userInfoId;
    @Column(name= "first_name")
    String firstName;

    @Column(name= "last_name")
    String lastName;

    @Column
    String phone;

    @Override
    public String toString() {
        return "UserInfo{" +
                "userInfoId=" + userInfoId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Column
    String email;

    @Column(name = "birth_date")
    Date birthDate;

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id")
    User user;

//    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
//    User user;

}
