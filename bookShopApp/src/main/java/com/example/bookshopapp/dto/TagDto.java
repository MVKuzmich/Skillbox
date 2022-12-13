package com.example.bookshopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TagDto {
    private Integer id;
    private String description;
    private String size;
}
