package com.shop.onlineshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "Orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long orderId;

    @Column(name="order_tracking_number")
    private String orderTrackingNumber;

    @Column(name="total_quantity")
    private int totalQuantity;

    @Column(name="total_price")
    private BigDecimal totalPrice;

    @Column(name="status")
    private String status;

    @Column(name="pay_type")
    String payType;

    @Column(name="is_paid")
    @JsonProperty("is_paid")
    boolean isPaid;

    @Column(name="date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name="last_updated")
    @UpdateTimestamp
    private Date lastUpdated;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shipping_contact_info_id", referencedColumnName = "contact_info_id")
    ContactInfo shippingContactInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="billing_contact_info_id", referencedColumnName = "contact_info_id")
    ContactInfo billingContactInfo;

    @Column(name = "user_id")
    Long userId;
//    @ManyToOne
//    @JoinColumn(name="user_id")
//    User user;

    public void addOrderItem(OrderItem item) {
        if (item != null) {
            if (this.orderItems == null)
                this.orderItems = new HashSet<>();
            else if (this.orderItems.contains(item))
                return;

            orderItems.add(item);
            item.setOrder(this);
        }
    }
}
