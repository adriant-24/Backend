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

    public User findByIdJoinFetchAddress(Long id);

    public List<Address> findAllAddressesByUserId(Long id);

    public Address saveAddress(Address address);

    public void deleteAddress(Address address);
    public User save (User user);
  //  public User registerUser (WebUser webUser) throws UserAlreadyExistsException;
    public User findByUserName(String username);
    public VerificationToken findByToken(String token);
    public VerificationToken createToken(String token, User user);
    public Long findUserIdByName(String userName);
    public UserInfo findUserInfoByUserName(String userName);
}
