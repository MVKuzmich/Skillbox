package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.SearchWordDto;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }
}
