package com.store.service.impl;

import com.store.domain.Product;
import com.store.repository.ProductRepository;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll(){
        return (List<Product>) productRepository.findAll();
    }

    public Product findOne(Long id){
        return productRepository.findOne(id);
    }

    public List<Product> findByCategory(String category){
        List<Product> productList = productRepository.findByCategory(category);
        List<Product> activeProductList = new ArrayList<>();

        for (Product product : productList){
            if(product.isActive()){
                activeProductList.add(product);
            }
        }
        return activeProductList;
    }
}
