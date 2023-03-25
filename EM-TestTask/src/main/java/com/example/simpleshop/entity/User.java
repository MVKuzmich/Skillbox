package com.example.simpleshop.entity;

import com.example.simpleshop.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
- Информация о пользователе состоит из:
	- Юзернейма;
	- Почты;
	- Пароля;
	- Баланса.
 */
@Entity
@Data
@ToString(exclude = {"userRates", "userReviews", "purchaseList", "userDeliveries"})
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    ProductCart productCart;

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rate> userRates = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> userReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Delivery> userDeliveries = new ArrayList<>();
}
