package com.example.bookshopapp.controllers;


import com.example.bookshopapp.data.author.Author;
import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AuthorsController {

    private final AuthorService authorService;

    @Autowired
    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }
    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorsMap() {
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @GetMapping("/authors/{slug}")
    public String personalAuthorPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("author", authorService.getAuthorBySlug(slug));
        model.addAttribute("books", authorService.getBooksByAuthorSlug(slug, 0, 5));
        model.addAttribute("description", authorService.getAuthorDescription(slug, 0, 2));
        model.addAttribute("hiddenDescription", authorService.getAuthorDescription(slug, 2, null));
        return "authors/slug";
    }

    @GetMapping("/books/author/{slug}")
    public String authorBooksPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("author", authorService.getAuthorBySlug(slug));
        model.addAttribute("books", authorService.getBooksByAuthorSlug(slug, 0, 10));
        return "books/author";
    }

    @GetMapping(value = "books/author/{slug}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BooksPageDto getAllBooksByAuthor(@PathVariable("slug") String slug,
                                            @RequestParam("offset") Integer offset,
                                            @RequestParam("limit") Integer limit) {
        return new BooksPageDto(authorService.getBooksByAuthorSlug(slug, offset, limit));
    }

}
