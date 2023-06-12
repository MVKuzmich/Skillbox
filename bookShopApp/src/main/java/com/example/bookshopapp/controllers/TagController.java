package com.example.bookshopapp.controllers;

import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TagController extends BaseController {

    private final TagService tagService;
    private final BookService bookService;



    @GetMapping("/tags/{tagId}")
    public String booksByTagePage(@PathVariable("tagId") Integer tagId, Model model) {
        model.addAttribute("tag", tagService.getTagByTagId(tagId));
        model.addAttribute("booksByTagId",
                bookService.getBooksByTagId(tagId, 0, 10).getContent());
        return "tags/index";
    }

    @GetMapping("/books/tag/{tagId}")
    @ResponseBody
    public BooksPageDto getBooksByTagPage(@PathVariable("tagId") Integer tagId,
                                          @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) {

        return new BooksPageDto(bookService.getBooksByTagId(tagId, offset, limit).getContent());
    }
}
