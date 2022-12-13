package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.tag.TagEntity;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.dto.TagDto;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TagController {

    private TagService tagService;
    private BookService bookService;

    public TagController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }
    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }

    @ModelAttribute("tags")
    public List<TagDto> tagList() {
        return tagService.getAllTagsDto();
    }


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
