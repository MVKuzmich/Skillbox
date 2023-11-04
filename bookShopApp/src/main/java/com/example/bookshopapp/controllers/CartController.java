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
public class CartController extends BaseController {

    public static final String IS_CART_EMPTY = "isCartEmpty";
    public static final String CART_BOOKS_COOKIE_NAME = "cartContents";
    public static final String COOKIE_PATH = "/";

    protected CartController(BookService bookService) {
        super(bookService);
    }

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<>();
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute(IS_CART_EMPTY, true);
        } else {
            model.addAttribute(IS_CART_EMPTY, false);
            cartContents = CookieHandleUtil.checkIfFirstOrEndSlashExist(cartContents);
            String[] cookieSlugs = cartContents.split("/");
            List<Book> booksFromCookieSlugs = bookService.getBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", booksFromCookieSlugs);
        }

        return "cart";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCartRequest(@PathVariable("slug") String bookSlug, @CookieValue(name =
            CART_BOOKS_COOKIE_NAME, required = false) String cartContents, HttpServletResponse response, Model model) {

        if (cartContents != null && !cartContents.equals("")) {
            Cookie cookie = CookieHandleUtil.removeElementFromCookieValue(bookSlug, cartContents, CART_BOOKS_COOKIE_NAME);
            cookie.setPath(COOKIE_PATH);
            response.addCookie(cookie);
            model.addAttribute(IS_CART_EMPTY, false);
        } else {
            model.addAttribute(IS_CART_EMPTY, true);
        }
        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleAddBookToCart(@PathVariable("slug") String bookSlug, @CookieValue(name = CART_BOOKS_COOKIE_NAME,
            required = false) String cartContents, HttpServletResponse response, Model model) {

        Cookie cookie = CookieHandleUtil.addElementToCookieValue(bookSlug, cartContents, CART_BOOKS_COOKIE_NAME);
            cookie.setPath(COOKIE_PATH);
            response.addCookie(cookie);
            model.addAttribute(IS_CART_EMPTY, false);

        return "redirect:/books/" + bookSlug;
    }
}
