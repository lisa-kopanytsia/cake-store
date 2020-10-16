package com.store.service;

import com.store.domain.Order;
import com.store.domain.ShoppingCart;
import com.store.domain.User;

public interface OrderService {

    Order createOrder(ShoppingCart shoppingCart, String shippingAddress, User user,String firstNameRecipient, String lastNameRecipient,String fatherNameRecipient, String phoneRecipient);

    Order findOne(Long id);

}
