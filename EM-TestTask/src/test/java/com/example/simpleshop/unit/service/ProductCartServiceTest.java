package com.example.simpleshop.unit.service;

import com.example.simpleshop.dto.ProductCartReadDto;
import com.example.simpleshop.dto.UserReadDto;
import com.example.simpleshop.entity.ProductCart;
import com.example.simpleshop.entity.User;
import com.example.simpleshop.enums.Role;
import com.example.simpleshop.mapper.DeliveryMapper;
import com.example.simpleshop.mapper.ProductCartReadMapper;
import com.example.simpleshop.mapper.PurchaseCreateMapper;
import com.example.simpleshop.repository.*;
import com.example.simpleshop.service.ProductCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCartServiceTest {

    @InjectMocks
    private ProductCartService productCartService;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductCartRepository productCartRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductCartReadMapper productCartReadMapper;
    @Mock
    private DeliveryMapper deliveryMapper;
    @Mock
    private PurchaseCreateMapper purchaseCreateMapper;
    @Mock
    private DeliveryRepository deliveryRepository;
    @Mock
    private PurchaseRepository purchaseRepository;

    @Mock
    Principal principal;


    @Test
    void checkCreateCartMethod() {
        User user = getUserEntity(88L, "mk", "mk@test.com", "123");
        UserReadDto userReadDto = getUserReadDto(user);
        ProductCart productCart = getProductCart();

        ProductCartReadDto cartReadDto = getProductCartReadDto(userReadDto, productCart);

        doReturn("mk@test.com").when(principal).getName();
        doReturn(Optional.of(user)).when(userRepository).findByEmail("mk@test.com");
        doReturn(productCart).when(productCartRepository).saveAndFlush(any(ProductCart.class));
        doReturn(cartReadDto).when(productCartReadMapper).toCartReadDto(any(ProductCart.class), any(User.class));

        ProductCartReadDto actualProductCartReadDto = productCartService.createCart(principal);

        Assertions.assertEquals(cartReadDto, actualProductCartReadDto);
    }

    private ProductCartReadDto getProductCartReadDto(UserReadDto userReadDto, ProductCart productCart) {
        return ProductCartReadDto.builder()
                .id(productCart.getId())
                .createDate(productCart.getCreateDate())
                .productList(new ArrayList<>())
                .userReadDto(userReadDto)
                .build();
    }

    private ProductCart getProductCart() {

        return ProductCart.builder()
                .id(99L)
                .createDate(LocalDateTime.now())
                .paidDate(null)
                .build();
    }

    private UserReadDto getUserReadDto(User user) {
        return UserReadDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .balance(user.getBalance())
                .role(user.getRole().name())
                .build();
    }
    private User getUserEntity(Long id, String username, String email, String password) {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .balance(BigDecimal.TEN)
                .role(Role.USER)
                .productCart(null)
                .build();
    }


}