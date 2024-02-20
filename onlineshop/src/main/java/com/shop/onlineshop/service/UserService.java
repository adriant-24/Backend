package com.shop.onlineshop.service;

import com.shop.onlineshop.entity.Address;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.entity.UserInfo;
import com.shop.onlineshop.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public User findUserById(Long id);
    public User findByIdUserOnly(Long id);

    public User findByUserName(String username);

    public Long findUserIdByName(String userName);

    public User findByIdJoinFetchAddress(Long id);

    public User save (User user);

    public UserInfo findUserInfoByUserName(String userName);

    public void deleteUserById (long userId);

    public List<Address> findAllAddressesByUserName(String userName);

    public Address saveAddress(Address address);

    public Address findAddressById(long id);
    public void deleteAddressFromUserById(long id);
    public void deleteAddressById(long id);

    public VerificationToken findByToken(String token);
    public VerificationToken createToken(String token, User user);

}
