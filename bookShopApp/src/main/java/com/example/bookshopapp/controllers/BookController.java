package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.ResourceStorage;
import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.dto.SearchWordDto;
import com.example.bookshopapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/books")
@Slf4j
public class BookController {

    private BookService bookService;
    private ResourceStorage storage;
    private RatingService ratingService;
    private BookReviewService bookReviewService;
    private UserService userService;
    private BookReviewLikeService bookReviewLikeService;

    @Autowired
    public BookController(BookService bookService, ResourceStorage storage, RatingService ratingService,
                          BookReviewService bookReviewService, UserService userService, BookReviewLikeService bookReviewLikeService) {
        this.bookService = bookService;
        this.storage = storage;
        this.ratingService = ratingService;
        this.bookReviewService = bookReviewService;
        this.userService = userService;
        this.bookReviewLikeService = bookReviewLikeService;
    }

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDto() {
        return new SearchWordDto();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResults() {
        return new ArrayList<>();
    }


    @GetMapping("/{slug}")
    public String chosenBookPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("book", bookService.getBookBySlug(slug));
        model.addAttribute("starList", ratingService.calculateBookRating(slug));
        model.addAttribute("countRateList", ratingService.getCountRateList(slug));
        return "books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.saveBook(bookToUpdate); //save new path in db here

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        log.info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        log.info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        log.info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/rateBook")
    public String rateBook(@RequestParam("bookId") String bookId,
                           @RequestParam("value") String value) {
        Book book = bookService.getBookByBookId(Integer.parseInt(bookId));
        ratingService.save(book, Integer.parseInt(value));
        return ("redirect:/books/" + book.getSlug());
    }

    @PostMapping("/bookReview")
    public String reviewBook(@RequestParam("bookId") String slug, @RequestParam("text") String text) {
        Book book = bookService.getBookBySlug(slug);
        UserEntity user = userService.getUserById(new Random().nextInt(500) + 1);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime formatDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        BookReviewEntity bookReviewEntity = new BookReviewEntity(
                book,
                user,
                formatDate,
                text);
        bookReviewService.saveBookReview(bookReviewEntity);

        return ("redirect:/books/" + book.getSlug());
    }

    @PostMapping("/rateBookReview")
    public String bookReviewLikeOrDislike(@RequestParam("reviewid") String reviewId, @RequestParam("value") String value) {
        BookReviewEntity bookReviewEntity = bookReviewService.getReviewById(Integer.parseInt(reviewId));
        UserEntity user = userService.getUserById(new Random().nextInt(500) + 1);
        BookReviewLikeEntity bookReviewLikeEntity = new BookReviewLikeEntity(
                bookReviewEntity,
                user,
                LocalDateTime.now(),
                Short.parseShort(value));
        bookReviewLikeService.saveBookReviewLikeOrDislike(bookReviewLikeEntity);

        return ("redirect:/books/" + bookReviewEntity.getBook().getSlug());
    }

}
