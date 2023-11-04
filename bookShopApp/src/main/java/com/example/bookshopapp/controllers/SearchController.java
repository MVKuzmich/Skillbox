package com.example.bookshopapp.controllers;

import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.errors.EmptySearchException;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SearchController extends BaseController {

    public SearchController(BookService bookService, UserService userService) {
        super(bookService, userService);
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false) SearchWordDto
                                           searchWordDto, Model model) throws EmptySearchException {
        if (searchWordDto == null) {
            throw new EmptySearchException("Search is impossible, enter any word...");
        } else {
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResults", bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), 0, 5).getContent());
            return "/search/index";
        }
    }
}
