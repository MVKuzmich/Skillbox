package com.example.bookshopapp.data.book.links;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.user.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book2user")
@Getter @Setter @NoArgsConstructor
public class Book2UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @OneToOne
    @JoinColumn(name = "type_id", columnDefinition = "INT NOT NULL")
    private Book2UserTypeEntity type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", columnDefinition = "INT NOT NULL")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", columnDefinition = "INT NOT NULL")
    private UserEntity user;


}
