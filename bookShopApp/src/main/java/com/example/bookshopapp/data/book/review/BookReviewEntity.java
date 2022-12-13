package com.example.bookshopapp.data.book.review;


import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "book_review")
@Getter
@Setter
@NoArgsConstructor
public class BookReviewEntity implements Comparable<BookReviewEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @OneToMany(mappedBy = "bookReviewEntity", fetch = FetchType.LAZY)
    private Set<BookReviewLikeEntity> bookReviewLikeSet = new HashSet<>();

    public BookReviewEntity(Book book, UserEntity user, LocalDateTime time, String text) {
        this.book = book;
        this.user = user;
        this.time = time;
        this.text = text;
    }

    public Integer countLikes() {
        return (int)bookReviewLikeSet.stream().filter(entry -> entry.getValue() > 0).count();
    }
    public Integer countDislikes() {
        return (int)bookReviewLikeSet.stream().filter(entry -> entry.getValue() < 0).count();
    }

    public String[] calcBookReviewRating() {
        int maxStarCount = 5;
        int reviewRating = countLikes() - countDislikes();
        if(bookReviewLikeSet.isEmpty()) {
            reviewRating = 0;
        } else if(reviewRating < 5) {
            reviewRating = 1;
        } else if(reviewRating < 20) {
            reviewRating = 2;
        } else if(reviewRating < 100) {
            reviewRating = 3;
        } else if(reviewRating < 200) {
            reviewRating = 4;
        } else {
            reviewRating = 5;
        }
        String[] reviewStarList = new String[maxStarCount];
        for(int i = 0; i < maxStarCount; i++) {
            reviewStarList[i] = (i < reviewRating) ? "star" : "no";
        }
        return reviewStarList;
    }

    @Override
    public int compareTo(BookReviewEntity o) {
        return (int) Arrays.stream(this.calcBookReviewRating()).filter(item -> item.equals("star")).count() -
                (int) Arrays.stream(o.calcBookReviewRating()).filter(item -> item.equals("star")).count();
    }
}
