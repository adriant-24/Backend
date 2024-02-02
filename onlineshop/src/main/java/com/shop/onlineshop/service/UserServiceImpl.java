package com.shop.onlineshop.service;

import com.shop.onlineshop.dto.WebUser;
import com.shop.onlineshop.entity.*;
import com.shop.onlineshop.exception.UserAlreadyExistsException;
import com.shop.onlineshop.mapper.UserMapper;
import com.shop.onlineshop.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    UserInfoRepository userInfoRepository;

    AddressRepository addressRepository;

    RoleRepository roleRepository;

//    BCryptPasswordEncoder passwordEncoder;

    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserInfoRepository userInfoRepository,
                           AddressRepository addressRepository,
                        //   BCryptPasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           VerificationTokenRepository verificationTokenRepository){
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.addressRepository = addressRepository;
     //   this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }
    public User findByIdUserOnly(Long id) {
        return userRepository.findByIdUserOnly(id);
    }

    public User findByIdJoinFetchAddress(Long id) {
        return userRepository.findByIdJoinFetchAddress(id);
    }

    public List<Address> findAllAddressesByUserName(String userName) {
        return addressRepository.findAllByUserName(userName);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

    public User save (User user){
        return userRepository.save(user);
    }

//    public User registerUser (WebUser webUser) throws UserAlreadyExistsException {
//
//        if(this.findByUserName(webUser.getUserName()) != null)
//        {
//           // logger.warn("User name already exists: " + webUser.getUserName());
//            throw new UserAlreadyExistsException("There is an account with that username already:" + webUser.getUserName());
//
//        }
//        User user = UserMapper.webUserToUser(webUser);
//        user.getUserInfo().setUser(user);
//        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
//
//        return userRepository.save(user);
//    }
    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public Long findUserIdByName(String userName){
        return userRepository.findUserIdByName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if(user ==  null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                mapRolesToGrantedAuthority(user.getRoles()));

    }

    public Collection<? extends GrantedAuthority> mapRolesToGrantedAuthority (Collection<Role> roles){
        return roles.stream().map(role-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public VerificationToken findByToken(String token){
        return verificationTokenRepository.findByToken(token);
    }

    @Transactional
    public VerificationToken createToken(String token, User user){
        VerificationToken verificationToken = new VerificationToken(token, user);
        return verificationTokenRepository.save(verificationToken);
    }

    public UserInfo findUserInfoByUserName(String userName){
        return userInfoRepository.findUserInfoByUserName(userName);
    }
}
