package com.store.repository;

import com.store.domain.CartItem;
import com.store.domain.ProductToCartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long> {
    void deleteByCartItem(CartItem cartItem);
}
