package com.example.bookshopapp.data.author;

import com.example.bookshopapp.data.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "authors")
@Setter @Getter @NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String photo;
    private String slug;
    private String name;
    @Column(columnDefinition = "text")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorSet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Book> bookSet;

}


