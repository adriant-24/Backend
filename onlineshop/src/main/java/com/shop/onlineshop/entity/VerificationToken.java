package com.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "Verification_Token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationToken {

    static final int TOKEN_EXPIRE_TIME = 30;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    Long tokenId;

    @Column(name="token")
    String token;

    @Column(name="expiry_date")
    Date expiryDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    public VerificationToken (String token, User user){
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(TOKEN_EXPIRE_TIME);
    }
    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal  = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
