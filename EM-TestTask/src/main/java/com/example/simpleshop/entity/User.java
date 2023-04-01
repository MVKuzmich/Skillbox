package com.example.simpleshop.entity;

import com.example.simpleshop.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
- Информация о пользователе состоит из:
	- Юзернейма;
	- Почты;
	- Пароля;
	- Баланса.
 */
@Entity
@Data
@ToString(exclude = {"userRates", "userReviews", "purchaseList",
        "userDeliveries", "messageSendingList"})
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
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
    @Builder.Default
    private List<Purchase> purchaseList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Rate> userRates = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> userReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Delivery> userDeliveries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<MessageSending> messageSendingList = new ArrayList<>();
}
