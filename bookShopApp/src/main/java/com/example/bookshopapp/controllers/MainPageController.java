package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.tag.TagEntity;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.TagDto;
import com.example.bookshopapp.errors.EmptySearchException;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks() {
        return bookService.getPageOfRecentBooks(0, 6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks() {
        return bookService.getPageOfPopularBooks(0, 6).getContent();
    }

    @ModelAttribute("tags")
    public List<TagDto> tagList() {
        return tagService.getAllTagsDto();
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping(value = "/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksBetween(@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,
                                              @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to,
                                              @RequestParam(value = "offset", required = false) Integer offset,
                                              @RequestParam(value = "limit", required = false) Integer limit) {

        return new BooksPageDto(bookService.getPageOfRecentBooksBetweenDesc(from, to, offset, limit).getContent());
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer
            limit) {
        return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }
}
