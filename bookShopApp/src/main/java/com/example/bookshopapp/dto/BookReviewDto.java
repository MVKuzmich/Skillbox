package com.example.bookshopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookReviewDto {

    private Integer id;
    private String userName;
    private Integer rating;
    private String[] stars;
    private LocalDateTime time;
    private String reviewText;
    private Integer countLikes;
    private Integer countDislikes;

}
