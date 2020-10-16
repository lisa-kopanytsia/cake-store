package com.store.service.impl;

import com.store.domain.*;
import com.store.repository.OrderRepository;
import com.store.service.CartItemService;
import com.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    public synchronized Order createOrder(ShoppingCart shoppingCart, String shippingAddress,  User user,String firstNameRecipient, String lastNameRecipient,String fatherNameRecipient, String phoneRecipient){
        Order order = new Order();
        order.setOrderStatus("created");
        order.setShippingAddress(shippingAddress);
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        for (CartItem cartItem : cartItemList){
            Product product = cartItem.getProduct();
            cartItem.setOrder(order);
        }
        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        order.setShippingAddress(shippingAddress);
        order.setFirstNameRecipient(firstNameRecipient);
        order.setLastNameRecipient(lastNameRecipient);
        order.setFatherNameRecipient(fatherNameRecipient);
        order.setPhoneRecipient(phoneRecipient);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    public Order findOne(Long id){
        return orderRepository.findOne(id);

    }
}
