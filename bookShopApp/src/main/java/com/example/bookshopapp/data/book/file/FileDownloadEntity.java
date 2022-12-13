package com.example.bookshopapp.data.book.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "file_download")
@Getter @Setter @NoArgsConstructor
public class FileDownloadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", columnDefinition = "INT NOT NULL")
    private int userId;

    @Column(name = "book_id", columnDefinition = "INT NOT NULL")
    private int bookId;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1")
    private int count;


}
