package com.example.bookshopapp.data.book.links;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book2tag")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book2TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "tag_id")
    private Integer tagId;

}
