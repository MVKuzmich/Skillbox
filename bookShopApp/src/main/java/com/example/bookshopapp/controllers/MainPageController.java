package com.example.bookshopapp.controllers;

import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class MainPageController extends BaseController {

    private final BookService bookService;
    private final TagService tagService;


    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("recommendedBooks", bookService.getRecommendedBooks(0, 6).getContent());
        model.addAttribute("recentBooks", bookService.getRecentBooks(0, 6).getContent());
        model.addAttribute("popularBooks", bookService.getPopularBooks(0, 6).getContent());
        model.addAttribute("tags", tagService.getAllTagsDto());

        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping(value = "/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksBetween(@RequestParam(value = "from", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate from,
                                              @RequestParam(value = "to", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate to,
                                              @RequestParam(value = "offset", required = false) Integer offset,
                                              @RequestParam(value = "limit", required = false) Integer limit) {

        return new BooksPageDto(bookService.getRecentBooksBetweenDesc(from, to, offset, limit).getContent());
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer
            limit) {
        return new BooksPageDto(bookService.getPopularBooks(offset, limit).getContent());
    }
}
