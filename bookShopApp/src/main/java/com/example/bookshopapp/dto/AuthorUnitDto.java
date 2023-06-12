package com.example.bookshopapp.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;


@Value
@Builder
public class AuthorUnitDto {

    Integer id;
    String photo;
    String slug;
    String name;
    String[] description;
    List<BookModelDto> books;
    Long countBooksByAuthor;
}
