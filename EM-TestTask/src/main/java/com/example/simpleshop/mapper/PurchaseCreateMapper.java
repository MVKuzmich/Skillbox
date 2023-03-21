package com.example.simpleshop.mapper;

import com.example.simpleshop.dto.PurchaseCreateDto;
import com.example.simpleshop.entity.Product;
import com.example.simpleshop.entity.Purchase;
import org.springframework.stereotype.Component;

@Component
public class PurchaseCreateMapper implements Mapper<Product, Purchase> {

    @Override
    public Purchase map(Product fromObject) {
        Purchase purchase = new Purchase();
        purchase.setProduct(fromObject);
        return purchase;
    }
}
