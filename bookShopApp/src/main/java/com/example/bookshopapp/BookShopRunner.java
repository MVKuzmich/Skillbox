package com.example.bookshopapp;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.genre.GenreEntity;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.dto.GenreDto;
import com.example.bookshopapp.repository.BookRepository;
import com.example.bookshopapp.repository.GenreRepository;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.service.GenreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookShopRunner implements CommandLineRunner {

    private GenreService genreService;

    public BookShopRunner(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public void run(String... args) throws Exception {
//        List<GenreEntity> list = genreService.getAllParentsBySlug("horror-thriller");
//        String s = "";

    }
}
