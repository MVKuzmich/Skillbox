package com.example.bookshopapp.dto;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
