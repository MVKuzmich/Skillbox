package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class PostponedController extends BaseController{

    private final BookService bookService;

    @ModelAttribute(name = "postponedBooks")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public String handleCartRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute("isPostponedEmpty", true);
        } else {
            model.addAttribute("isPostponedEmpty", false);
            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length() - 1) : postponedContents;
            String[] cookieSlugs = postponedContents.split("/");
            List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn(cookieSlugs);
            model.addAttribute("postponedBooks", booksFromCookieSlugs);
        }

        return "postponed";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "postponedContents", required = false) String postponedContents, HttpServletResponse response, Model model) {

        if (postponedContents != null && !postponedContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else {
            model.addAttribute("isPostponedEmpty", true);
        }

        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents",
            required = false) String postponedContents, HttpServletResponse response, Model model) {

        if (postponedContents == null || postponedContents.equals("")) {
            Cookie cookie = new Cookie("postponedContents", slug);
            cookie.setPath("/books"); //для доступности cookie на всех эндпоинтах books/
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else if (!postponedContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(postponedContents).add(slug);
            Cookie cookie = new Cookie("postponedContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        }

        return "redirect:/books/" + slug;
    }

}
