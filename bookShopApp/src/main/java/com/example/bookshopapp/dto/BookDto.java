package com.example.bookshopapp.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class BookDto {
    Integer id;
    String slug;
    String image;
    String authors;
    String title;
    Integer discount;
    Boolean isBestseller;
    String status;
    Integer price;
    Integer discountPrice;

}