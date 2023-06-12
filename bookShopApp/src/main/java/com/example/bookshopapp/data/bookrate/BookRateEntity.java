package com.example.bookshopapp.data.bookrate;

import com.example.bookshopapp.data.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_rates")
@NoArgsConstructor
@Getter
@Setter
public class BookRateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    private Integer value;

    public BookRateEntity(Book book, Integer value) {
        this.book = book;
        this.value = value;
    }
}
