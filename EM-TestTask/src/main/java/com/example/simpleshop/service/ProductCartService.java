package com.example.simpleshop.service;

import com.example.simpleshop.dto.DeliveryReadDto;
import com.example.simpleshop.entity.*;
import com.example.simpleshop.exception.*;
import com.example.simpleshop.dto.CartItemCreateDto;
import com.example.simpleshop.dto.ProductCartReadDto;
import com.example.simpleshop.mapper.DeliveryMapper;
import com.example.simpleshop.mapper.ProductCartReadMapper;
import com.example.simpleshop.mapper.PurchaseCreateMapper;
import com.example.simpleshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCartService {

    private final CartItemRepository cartItemRepository;
    private final ProductCartRepository productCartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductCartReadMapper productCartReadMapper;
    private final DeliveryMapper deliveryMapper;
    private final PurchaseCreateMapper purchaseCreateMapper;
    private final DeliveryRepository deliveryRepository;
    private final PurchaseRepository purchaseRepository;


    @Transactional
    public ProductCartReadDto createCart(Principal principal) throws CartExistException {
        User user = getUser(principal);
        Optional<ProductCart> productCartOptional = Optional.ofNullable(user.getProductCart());
        productCartOptional.ifPresent(product -> {
            throw new CartExistException("You have an opened purchase cart!");
        });

        ProductCart productCart = new ProductCart();
        productCart.setCreateDate(LocalDateTime.now());
        productCartRepository.saveAndFlush(productCart);
        user.setProductCart(productCart);

        return productCartReadMapper.toCartReadDto(productCart, user);
    }


    @Transactional
    public ProductCartReadDto postpone(CartItemCreateDto cartItemCreateDto, Principal principal) {
        User user = getUser(principal);

        ProductCart productCart = Optional.ofNullable(user.getProductCart())
                .orElseThrow(() -> new ProductCartNotFoundException("Product cart is not found! Create!"));

        Product product = productRepository.findByName(cartItemCreateDto.getProductName()).stream()
                .filter(prod -> prod.getQuantityInStore() >= cartItemCreateDto.getProductAmount())
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product is not found or quantity is not enough"));

        productCart.getProductList().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresent(item -> {
                    throw new CartItemExistException("Product is added into cart! Increase amount!");
                });

        CartItem cartItem = cartItemRepository.saveAndFlush(new CartItem(
                product,
                productCart,
                cartItemCreateDto.getProductAmount(),
                product.getPrice().multiply(new BigDecimal(cartItemCreateDto.getProductAmount()))));

        product.setQuantityInStore(product.getQuantityInStore() - cartItemCreateDto.getProductAmount());
        productCart.getProductList().add(cartItem);
        return productCartReadMapper.toCartReadDto(productCart, user);

    }

    @Transactional
    public DeliveryReadDto payAndPassToDelivery(Principal principal) {
        User user = getUser(principal);
        Delivery delivery = Optional.ofNullable(user.getProductCart())
                .map(cart -> {
                    List<Purchase> purchaseList = makePurchase(cart, user);
                    removeCart(cart, user);
                    return passToDelivery(purchaseList, user);
                })
                .orElseThrow(() -> new CartNotFoundException("Cart is not found"));
        return deliveryMapper.map(delivery);
    }

    private void removeCart(ProductCart cart, User user) {
        user.setProductCart(null);
        productCartRepository.delete(cart);
        productCartRepository.flush();
    }

    private User getUser(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow();
    }

    private List<Purchase> makePurchase(ProductCart cart, User user) {
        return cart.getProductList().stream()
                .map(cartItem -> purchaseCreateMapper.toPurchase(cartItem, user))
                .map(purchaseRepository::save)
                .collect(Collectors.toList());
    }

    private Delivery passToDelivery(List<Purchase> purchaseList, User user) {
        Delivery delivery = deliveryRepository.save(new Delivery(user, LocalDateTime.now()));
        purchaseList.forEach(purchase -> purchase.setDelivery(delivery));
        delivery.setPurchaseList(purchaseList);
        return delivery;
    }
}
