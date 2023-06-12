package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.dto.GenreDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GenresController extends BaseController {

    private final GenreService genreService;
    private final BookService bookService;



    @GetMapping("/genres")
    public String genresPage(Model model) {
        model.addAttribute("genres", genreService.getAllGenres());
        return "genres/index";
    }

    @GetMapping("/genres/{slug}")
    public String getSlugPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("books", genreService.getAllBooksByGenre(slug, 0, 10));
        model.addAttribute("genreName", genreService.getGenreBySlug(slug).getName());
        model.addAttribute("genreSlug", genreService.getGenreBySlug(slug).getSlug());
        model.addAttribute("parents", genreService.getAllParentsBySlug(slug));
        return "genres/slug";
    }

    @GetMapping("/books/genre/{slug}")
    @ResponseBody
    public BooksPageDto getBooksByTagPage(@PathVariable("slug") String slug,
                                          @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) {

        return new BooksPageDto(genreService.getAllBooksByGenre(slug, offset, limit));
    }
}
