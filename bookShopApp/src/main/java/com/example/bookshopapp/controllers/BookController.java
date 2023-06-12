package com.example.bookshopapp.controllers;

import com.example.bookshopapp.data.ResourceStorage;
import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.book.review.BookReviewEntity;
import com.example.bookshopapp.data.book.review.BookReviewLikeEntity;
import com.example.bookshopapp.data.user.UserEntity;
import com.example.bookshopapp.dto.BookRateCreateDto;
import com.example.bookshopapp.dto.BookReviewCreateDto;
import com.example.bookshopapp.dto.RateRangeDto;
import com.example.bookshopapp.dto.ReviewRateDto;
import com.example.bookshopapp.service.*;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/books")
@Slf4j
@RequiredArgsConstructor
public class BookController extends BaseController {

    private final BookService bookService;
    private final ResourceStorage storage;
    private final RatingService ratingService;
    private final BookReviewService bookReviewService;

    private final UserService userService;
    private final BookReviewLikeService bookReviewLikeService;


    @GetMapping("/{slug}")
    public String chosenBookPage(@PathVariable("slug") String slug, Model model) {
        List<RateRangeDto> bookRateRangeList = ratingService.getBookRateRangeList(slug);
        model.addAttribute("bookRateCreateDto", new BookRateCreateDto());
        model.addAttribute("book", bookService.getBookUnitDtoByBookSlug(slug));
        model.addAttribute("countRateList", bookRateRangeList);
        model.addAttribute("rateTotalCount", bookRateRangeList.stream().mapToInt(RateRangeDto::getRateCount).sum());
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
    public String rateBook(@RequestBody BookRateCreateDto bookRateCreateDto) {
        Book book = bookService.getBookByBookId(Integer.parseInt(bookRateCreateDto.getBookId()));
        ratingService.save(book, Integer.parseInt(bookRateCreateDto.getValue()));
        return ("redirect:/books/" + book.getSlug());
    }

    @PostMapping("/bookReview")
    // TODO: 22.05.2023 только авторизованные пользователи - изменить функционал по определению пользователя
    public String reviewBook(@RequestBody BookReviewCreateDto bookReviewCreateDto) {
        Book book = bookService.getBookBySlug(bookReviewCreateDto.getBookSlug());
        UserEntity user = userService.getUserById(new Random().nextInt(500) + 1);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime formatDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        BookReviewEntity bookReviewEntity = new BookReviewEntity(
                book,
                user,
                formatDate,
                bookReviewCreateDto.getText());
        bookReviewService.saveBookReview(bookReviewEntity);

        return ("redirect:/books/" + book.getSlug());
    }

    @PostMapping("/rateBookReview")
    // TODO: 22.05.2023 проверить по ТЗ, кто может ставить лайки
    public String bookReviewLikeOrDislike(@RequestBody ReviewRateDto reviewRateDto) {
        BookReviewEntity bookReviewEntity = bookReviewService.getReviewById(Integer.parseInt(reviewRateDto.getReviewId()));
        UserEntity user = userService.getUserById(new Random().nextInt(500) + 1);
        BookReviewLikeEntity bookReviewLikeEntity = new BookReviewLikeEntity(
                bookReviewEntity,
                user,
                LocalDateTime.now(),
                Short.parseShort(reviewRateDto.getValue()));
        bookReviewLikeService.saveBookReviewLikeOrDislike(bookReviewLikeEntity);

        return ("redirect:/books/" + bookReviewEntity.getBook().getSlug());
    }

}
