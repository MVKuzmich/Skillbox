package com.example.simpleshop.entity;

import com.example.simpleshop.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(exclude = {"userRates", "userReviews"})
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

    @OneToMany(mappedBy = "user")
    private List<Rate> userRates = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> userReviews = new ArrayList<>();
}
