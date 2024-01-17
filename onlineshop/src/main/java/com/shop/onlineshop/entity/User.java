package com.shop.onlineshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    Long userId;

    @Column (name = "username")
    String username;

    @Column (name = "password")
    String password;

    @Column (name = "enabled")
    short enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
        enabled = 0;
    }

    @OneToMany(mappedBy ="user",
            fetch = FetchType.EAGER,
            cascade= CascadeType.ALL)
  //  @JoinColumn(name = "user_id")
    List<Address> addresses;

//    @OneToMany(mappedBy ="user",
//            fetch = FetchType.LAZY,
//            cascade= CascadeType.ALL)
//    //  @JoinColumn(name = "user_id")
//    List<Order> orders;

    @OneToOne(orphanRemoval = true,mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    UserInfo userInfo;

    public boolean isEnabled(){
        return enabled == 1;
    }

    public void addAddress(Address address)
    {
        if (this.addresses == null)
            this.addresses = new ArrayList<>();
        else if(this.addresses.contains(address))
            return;

        address.setUser(this);

        this.addresses.add(address);
    }

    public void removeAddress(Address address)
    {
        if (this.addresses == null)
            return;

        if(!this.addresses.contains(address))
            return;

        address.setUser(null);

        this.addresses.remove(address);
    }

//    public void addOrder(Order order)
//    {
//        if (this.orders == null)
//            this.orders = new ArrayList<>();
//        else if(this.orders.contains(order))
//            return;
//
//        order.setUserId(this.userId);
//
//        this.orders.add(order);
//    }
//
//    public void removeOrder(Order order)
//    {
//        if (this.orders == null)
//            return;
//
//        if(!this.orders.contains(order))
//            return;
//
//        order.setUserId(-1L);
//
//        this.orders.remove(order);
//    }

    public void addRole(Role role)
    {
        if (this.roles == null)
            this.roles = new ArrayList<>();
        else if(this.roles.contains(role))
            return;

        this.roles.add(role);
    }

    public void removeRole(Role role)
    {
        if (this.roles == null)
            return;

        if(!this.roles.contains(role))
            return;

        this.roles.remove(role);
    }

    public User(Long userId, String username, String password, short enabled) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User(Long userId, String username, String password, short enabled, Collection<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", userInfo=" + userInfo +
                '}';
    }
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public short getEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(short enabled) {
//        this.enabled = enabled;
//    }
//
//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }

}