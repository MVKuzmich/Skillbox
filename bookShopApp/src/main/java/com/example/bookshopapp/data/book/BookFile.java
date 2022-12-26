package com.example.bookshopapp.data.book;


import com.example.bookshopapp.data.enums.BookFileType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
@Getter
@Setter
@NoArgsConstructor
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String hash;

    @Column(name = "type_id")
    private int typeId;

    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @JsonProperty
    public String getBookFileExtensionString() {
        return BookFileType.getExtensionStringByTypeId(typeId);
    }


}
