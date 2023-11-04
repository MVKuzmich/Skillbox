package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.service.BookService;
import com.example.bookshopapp.util.CookieHandleUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class PostponedController extends BaseController{

    public static final String COOKIE_PATH = "/";
    public static final String POSTPONED_BOOKS_COOKIE_NAME = "postponedContents";
    public static final String IS_POSTPONED_EMPTY = "isPostponedEmpty";

    protected PostponedController(BookService bookService) {
        super(bookService);
    }

    @ModelAttribute(name = "postponedBooks")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/postponed")
    public String handleCartRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        if (postponedContents == null || postponedContents.equals("")) {
            model.addAttribute(IS_POSTPONED_EMPTY, true);
        } else {
            model.addAttribute(IS_POSTPONED_EMPTY, false);
            postponedContents = CookieHandleUtil.checkIfFirstOrEndSlashExist(postponedContents);
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
            Cookie cookie = CookieHandleUtil.removeElementFromCookieValue(slug, postponedContents, POSTPONED_BOOKS_COOKIE_NAME);
            cookie.setPath(COOKIE_PATH);
            response.addCookie(cookie);
            model.addAttribute(IS_POSTPONED_EMPTY, false);
        } else {
            model.addAttribute(IS_POSTPONED_EMPTY, true);
        }

        return "redirect:/books/postponed";
    }


    @PostMapping("/changeBookStatus/postponed/{slug}")
    public String handleAddBookToPostponed(@PathVariable("slug") String bookSlug, @CookieValue(name = "postponedContents",
            required = false) String postponedContents, HttpServletResponse response, Model model) {
        Cookie cookie = CookieHandleUtil.addElementToCookieValue(bookSlug, postponedContents, POSTPONED_BOOKS_COOKIE_NAME);
        cookie.setPath(COOKIE_PATH); //для доступности cookie на всех эндпоинтах books/
        response.addCookie(cookie);
        model.addAttribute(IS_POSTPONED_EMPTY, false);

        return "redirect:/books/" + bookSlug;
    }

}
