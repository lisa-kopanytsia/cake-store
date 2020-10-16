package com.store.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date orderDate;
    private Date shippingDate;
    private String orderStatus;
    private BigDecimal orderTotal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;

    @ManyToOne
    private User user;

    private String shippingAddress;

    private String firstNameRecipient;
    private String lastNameRecipient;
    private String fatherNameRecipient;
    private String phoneRecipient;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

     public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstNameRecipient() {
        return firstNameRecipient;
    }

    public void setFirstNameRecipient(String firstNameRecipient) {
        this.firstNameRecipient = firstNameRecipient;
    }

    public String getLastNameRecipient() {
        return lastNameRecipient;
    }

    public void setLastNameRecipient(String lastNameRecipient) {
        this.lastNameRecipient = lastNameRecipient;
    }

    public String getFatherNameRecipient() {
        return fatherNameRecipient;
    }

    public void setFatherNameRecipient(String fatherNameRecipient) {
        this.fatherNameRecipient = fatherNameRecipient;
    }

    public String getPhoneRecipient() {
        return phoneRecipient;
    }

    public void setPhoneRecipient(String phoneRecipient) {
        this.phoneRecipient = phoneRecipient;
    }
}
