package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.repository.BookRepository;
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
public class CartController extends BaseController {

    private final BookService bookService;

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlugs);
        }

        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name =
            "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books"); //для доступности cookie на всех эндпоинтах books/
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }

        return "redirect:/books/" + slug;
    }
}
