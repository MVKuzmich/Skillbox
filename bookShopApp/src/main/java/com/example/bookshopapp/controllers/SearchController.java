package com.example.bookshopapp.controllers;

import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.errors.EmptySearchException;
import com.example.bookshopapp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    private BookService bookService;

    public SearchController(BookService bookService) {
        this.bookService = bookService;
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

        @GetMapping("/search/page/{searchWord}")
        @ResponseBody
        public BooksPageDto getNextSearchPage (@RequestParam("offset") Integer offset,
                                               @RequestParam("limit") Integer limit,
                                               @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto){
            return new BooksPageDto(bookService.getPageOfSearchResultsBooks(searchWordDto.getExample(), offset, limit).getContent());
        }
}
