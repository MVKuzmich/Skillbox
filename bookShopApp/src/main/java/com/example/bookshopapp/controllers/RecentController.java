package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BookDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.dto.BooksPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class RecentController extends BaseController{

    private final BookService bookService;

    @ModelAttribute("recentBooks")
    public List<BookDto> recentBooks(){
        LocalDate now = LocalDate.now();
        return bookService.getRecentBooksBetweenDesc(now.minusMonths(1L), now, 0, 6).getContent();
    }


    @GetMapping("/recent")
    public String recentPage() {
        return "books/recent";
    }

}
