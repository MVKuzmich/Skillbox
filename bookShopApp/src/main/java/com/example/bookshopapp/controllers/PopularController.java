package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BookDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PopularController extends BaseController {

    private final BookService bookService;


    @ModelAttribute("popularBooks")
    public List<BookDto> popularBooks() {
        return bookService.getPopularBooks(0, 20).getContent();
    }


    @GetMapping("/popular")
    public String popularPage() {
        return "books/popular";
    }

}
