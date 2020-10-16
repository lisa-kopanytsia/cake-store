package com.store.service;

import com.store.domain.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart updateShoppingCart (ShoppingCart shoppingCart);

    void clearShoppingCart(ShoppingCart shoppingCart);
}
