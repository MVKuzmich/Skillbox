package com.example.simpleshop.service;

import com.example.simpleshop.CartExistException;
import com.example.simpleshop.dto.CartReadDto;
import com.example.simpleshop.dto.DeliveryReadDto;
import com.example.simpleshop.dto.PurchaseCreateDto;
import com.example.simpleshop.entity.Delivery;
import com.example.simpleshop.entity.Purchase;
import com.example.simpleshop.entity.PurchaseCart;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.mapper.CartReadMapper;
import com.example.simpleshop.mapper.PurchaseReadMapper;
import com.example.simpleshop.mapper.UserDeliveryMapper;
import com.example.simpleshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseCartRepository purchaseCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final CartReadMapper cartReadMapper;
    private final PurchaseReadMapper purchaseReadMapper;
    private final UserDeliveryMapper userDeliveryMapper;


    @Transactional
    public CartReadDto createCart(Principal principal) throws CartExistException {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        int count = purchaseCartRepository.countUserCartWithNullDelivery(user.getId());
        if (count != 0) {
            throw new CartExistException("You have an opened purchase cart!");
        } else {
            PurchaseCart purchaseCart = new PurchaseCart();
            purchaseCart.setUser(user);
            purchaseCart.setCreateDate(LocalDateTime.now());
            purchaseCart = purchaseCartRepository.saveAndFlush(purchaseCart);
            return cartReadMapper.toCartReadDto(purchaseCart);
        }
    }

    @Transactional
    public CartReadDto postponeIntoCart(PurchaseCreateDto purchaseDto, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        PurchaseCart purchaseCart = user.getPurchaseCartList().stream().filter(cart -> cart.getDelivery() == null).findFirst().orElseThrow();

        productRepository.findByName(purchaseDto.getProductName())
                .filter(product -> product.getQuantityInStore() > purchaseDto.getProductAmount())
                .map(product -> {
                    product.setQuantityInStore(product.getQuantityInStore() - purchaseDto.getProductAmount());
                    Purchase purchase = new Purchase();
                    purchase.setProduct(product);
                    purchase.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf((double) purchaseDto.getProductAmount())));
                    purchase.setProductAmount(purchaseDto.getProductAmount());
                    purchase.setPurchaseCart(purchaseCart);
                    purchaseCart.getPurchaseList().add(purchase);
                    return purchase;
                })
                .orElseThrow();

        return cartReadMapper.toCartReadDto(purchaseCart);

    }

    @Transactional
    public DeliveryReadDto payAndPassToDelivery(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        PurchaseCart purchaseCart = user.getPurchaseCartList().stream()
                .filter(cart -> cart.getDelivery() == null).findFirst().orElseThrow();
        Delivery delivery = new Delivery();
        delivery.setUser(user);
        delivery.setCreateDate(LocalDateTime.now());
        deliveryRepository.saveAndFlush(delivery);
        delivery.setPurchaseCart(purchaseCart);
        purchaseCart.setDelivery(delivery);
        return new DeliveryReadDto(delivery.getId(),
                purchaseCart.getPurchaseList().stream()
                        .map(purchaseReadMapper::toPurchaseReadDto)
                        .collect(Collectors.toList()),
                userDeliveryMapper.toUserDeliveryDto(user));
    }
}
