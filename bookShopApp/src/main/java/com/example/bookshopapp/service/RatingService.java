package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.data.bookrate.BookRateEntity;
import com.example.bookshopapp.repository.BookRepository;
import com.example.bookshopapp.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Service
public class RatingService {

    private static final int MAX_STAR_COUNT = 5;

    private RatingRepository ratingRepository;
    private BookRepository bookRepository;

    public RatingService(RatingRepository ratingRepository, BookRepository bookRepository) {
        this.ratingRepository = ratingRepository;
        this.bookRepository = bookRepository;
    }

    public BookRateEntity save(Book book, Integer value) {
        return ratingRepository.saveAndFlush(new BookRateEntity(book, value));
    }

    public String[] calculateBookRating(String slug) {
        String[] result = new String[MAX_STAR_COUNT];
        Book book = bookRepository.findBySlug(slug);
        Set<BookRateEntity> bookRates = book.getBookRateList();
        double sumRates = bookRates.stream().map(BookRateEntity::getValue).mapToInt(Integer::intValue).sum();
        int rating = (int) Math.round(sumRates / bookRates.size());

        for (int i = 0; i < result.length; i++) {
            result[i] = (i < rating) ? "star" : "no";
        }
        return result;
    }

    public List<Integer> getCountRateList(String slug) {
        List<Integer> countRateList = new ArrayList<>();
        Book book = bookRepository.findBySlug(slug);
        IntStream.rangeClosed(1, 5).forEach(item -> {
            int count = (int)book.getBookRateList().stream().filter(rate -> rate.getValue() == item).count();
            countRateList.add(count);
        });
        return countRateList;
    }
}
