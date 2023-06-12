package com.example.bookshopapp.service;

import com.example.bookshopapp.dto.BookUnitDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BookServiceIT {

    @Autowired
    private BookService bookService;


    @Test
    void getBookUnitDtoByBookSlug() {
        BookUnitDto bookUnit = bookService.getBookUnitDtoByBookSlug("book-452-npg");

        assertThat(bookUnit.getSlug()).isEqualTo("book-452-npg");
        assertThat(bookUnit.getRating()).isEqualTo(2);
        assertThat(bookUnit.getDescription()).isEqualTo("Curabitur in libero ut massa volutpat convallis.");
        assertThat(bookUnit.getTitle()).isEqualTo("Kronos (a.k.a. Captain Kronos: Vampire Hunter)");
        assertThat(bookUnit.getAuthors()).isNotEmpty();
        assertThat(bookUnit.getTags()).isNotEmpty();
        assertThat(bookUnit.getReviews()).isNotEmpty();
    }
}