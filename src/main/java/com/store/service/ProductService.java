package com.store.service;

import com.store.domain.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findOne(Long id);

    List<Product> findByCategory(String category);
}
