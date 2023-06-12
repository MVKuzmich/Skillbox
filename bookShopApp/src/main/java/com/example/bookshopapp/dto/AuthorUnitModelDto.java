package com.example.bookshopapp.dto;

import lombok.Value;

@Value
public class AuthorUnitModelDto {
    Integer id;
    String photo;
    String slug;
    String name;
    String description;
    Long countBooksByAuthor;
}
