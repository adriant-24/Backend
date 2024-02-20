package com.shop.onlineshop.service;

import com.shop.onlineshop.entity.Address;
import com.shop.onlineshop.entity.User;
import com.shop.onlineshop.entity.UserInfo;
import com.shop.onlineshop.service.UserService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    static User user;

    static Address address;

    static UserInfo userInfo;

    static long newCreatedUserId = -1;

    @BeforeAll
    static void setUser(){
        Date birthDate = new Date();
        try {
            birthDate = new SimpleDateFormat("dd-MM-yyyy").parse("12-03-1995");
        } catch (ParseException ignored) {

        }
        userInfo = UserInfo.builder()
                .email("john.tuck@onlineshop.com")
                .birthDate(birthDate)
                .firstName("John")
                .lastName("Tuck")
                .phone("+40765111222")
                .build();

        address = Address.builder()
                .city("Craiova")
                .country("Romania")
                .streetAndNumber("Bld. Decebal, nr. 33")
                .county("Dolj")
                .additionalAddress("Bl. 12A, ap. 5")
                .isPrimary(true)
                .zipCode("200233")
                .phoneNumber("+40765111222")
                .build();
        user = User.builder()
                .username("john.tuck")
                .enabled((short) 1)
                .userInfo(userInfo)
                .build();
        user.addAddress(address);
        userInfo.setUser(user);
    }

//    @BeforeEach
//    void createUsers(){
//        jdbcTemplate.execute("Insert into user");
//    }
    Condition<User> userNameJohn = new Condition<>(
            u -> u.getUsername().equals("john.tuck"), "User name should be john.tuck");

    Condition<User> userLastNameJohn = new Condition<>(
            u -> u.getUserInfo().getLastName().equals("Tuck"), "User name should be john.tuck");
    @Test
    @Order(1)
    void createUser(){
        User newUser = userService.save(user);
        newCreatedUserId = newUser.getUserId();

        assertThat(newUser).isNotNull();
        assertEquals(newUser.getUsername(), user.getUsername());
        assertNotNull(newUser.getUserInfo());
        assertThat(newUser.getAddresses()).isNotNull().hasSize(1);
    }
    @Test
    @Order(2)
    void findUserByUserName(){
        User userTest = userService.findByUserName("john.tuck");
        assertThat(userTest).isNotNull();
        assertEquals(userTest.getUsername(), "john.tuck");

        UserInfo userInfoTest = userService.findUserInfoByUserName("john.tuck");
        assertNotNull(userTest.getUserInfo());
        assertEquals(userTest.getUserInfo().getEmail(), "john.tuck@onlineshop.com");
        assertThat(userTest.getAddresses()).isNotNull().hasSize(1);
        assertEquals(userTest.getAddresses().get(0).getCity(), "Craiova");
    }

    @Test
    @Order(3)
    void updateUserPhoneNumber(){
        String oldPhoneNumber = user.getUserInfo().getPhone();
        user.getUserInfo().setPhone("+40765333444");
        User userTest = userService.save(user);
        assertThat(userTest).isNotNull();
        assertEquals(userTest.getUsername(), "john.tuck");
        assertNotNull(userTest.getUserInfo());
        assertEquals(userTest.getUserInfo().getPhone(), "+40765333444");
        assertNotEquals(userTest.getUserInfo().getPhone(), oldPhoneNumber);
    }

    @Test
    @Order(4)
    void addAddress(){
        User userTest = userService.findByUserName("john.tuck");
        Address newAddress = Address.builder()
                .city("Rome")
                .country("Italy")
                .streetAndNumber("St. Ranoa, nr. 33")
                .county("Rome")
                .additionalAddress("Bl. 12A, ap. 5")
                .isPrimary(false)
                .zipCode("200222")
                .phoneNumber("+24765111222")
                .user(userTest)
                .build();
        Address addressTest = userService.saveAddress(newAddress);

        List<Address> addressList = userService.findAllAddressesByUserName("john.tuck");
        assertEquals(2,addressList.size(),"Address list should have 2 elements");
    }
    @Test
    @Order(5)
    void updateAddress(){
        Address addressUpdateTest = userService.findAddressById(2);
        addressUpdateTest.setCity("OldRome");
        Address addressTest = userService.saveAddress(addressUpdateTest);

        List<Address> addressList = userService.findAllAddressesByUserName("john.tuck");
        assertEquals("OldRome",addressList.get(1).getCity(),"Address city should be OldRome");
    }

    @Test
    @Order(6)
    void deleteAddressFromUser(){
        userService.deleteAddressFromUserById(2);
        List<Address> addressList = userService.findAllAddressesByUserName("john.tuck");
        assertEquals(1,addressList.size(),"Address list should have 1 element");
    }

    @Sql("/test-sql-files/insertAddress.sql")
    @Test
    @Order(7)
    void deleteAddressDirectly(){
        List<Address> addressList = userService.findAllAddressesByUserName("john.tuck");
        assertEquals(2,addressList.size(),"Address list should have 2 element");
        userService.deleteAddressById(1001);
        addressList = userService.findAllAddressesByUserName("john.tuck");
        assertEquals(1,addressList.size(),"Address list should have 1 element");
    }


    @Test
    @Order(Integer.MAX_VALUE)
    void deleteUser(){
        String oldPhoneNumber = user.getUserInfo().getPhone();
        user.getUserInfo().setPhone("+40765333444");
        userService.deleteUserById(newCreatedUserId);
        assertNull(userService.findUserById(newCreatedUserId));
        assertThrows(NoSuchElementException.class,()->{
            UserInfo ui = userService.findUserInfoByUserName("john.tuck");
            if (ui == null)
                throw new NoSuchElementException();
        });
        List<Address> addressList = userService.findAllAddressesByUserName("john.tuck");
        assertTrue(addressList.isEmpty(),"Address list should be empty");
    }

    @Test
    @Order(11)
    @Disabled
    void findUserByName(){

        fail("test failed");
    }
}
