package com.example.simpleshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/*
- Организация состоит из:
	- Имени;
	- Описания
	- Логотипа;
	- Товаров.

 */
@Entity
@Data
@ToString(exclude = "products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String logo;

    @OneToMany(mappedBy = "company")
    @Builder.Default
    List<Product> products = new ArrayList<>();


}
