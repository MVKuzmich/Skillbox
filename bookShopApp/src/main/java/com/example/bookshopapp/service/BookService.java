package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BooksPageDto;
import com.example.bookshopapp.errors.BookstoreApiWrongParameterException;
import com.example.bookshopapp.repository.BookRepository;
import org.apache.catalina.WebResource;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class BookService {

    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooksData() {
        return bookRepository.findAll();
    }

    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);

    }

    public Page<Book> getPageOfRecentBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllOrderByPubDateDesc(nextPage);
    }

    public Page<Book> getPageOfPopularBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAllByPopularity(nextPage);
    }

    public Page<Book> getPageOfSearchResultsBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    public Page<Book> getPageOfRecentBooksBetweenDesc(LocalDate fromDate, LocalDate endDate, Integer offset, Integer limit) {
        if (fromDate == null && endDate == null) {
            return getPageOfRecentBooks(offset, limit);
        } else {
            if (fromDate == null) {
                fromDate = LocalDate.of(1500, 1, 1);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }
            return bookRepository.findAllByPubDateBetweenDesc(fromDate, endDate, PageRequest.of(offset, limit));
        }
    }

    public Page<Book> getBooksByTagId(Integer tagId, Integer offset, Integer limit) {
        return bookRepository.findBooksByTagId(tagId, PageRequest.of(offset, limit));
    }

    public Book getBookBySlug(String slug) {
        return bookRepository.findBySlug(slug);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    /*
    METHODS for REST API (BookRestApiController)
     */

    public List<Book> getBooksByAuthor(String authorName) {
        return bookRepository.findBooksByAuthorNameContaining(authorName);
    }

    public List<Book> getBooksByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.length() <= 1) {
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<Book> data = bookRepository.findBooksByTitleContaining(title);
            if (data.isEmpty()) {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters...");
            } else {
                return data;
            }
        }
    }

    public List<Book> getBooksWithPriceBetween(Integer min, Integer max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    public List<Book> getBooksWithPrice(Integer price) {
        return bookRepository.findBooksByPriceOldIs(price);
    }

    public List<Book> getBooksWithMaxPrice() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    public List<Book> getBestsellers() {
        return bookRepository.getBestsellers();
    }

    public List<Book> getBooksBySlugIn(String[] cookieSlugs) {
        return bookRepository.findBooksBySlugIn(cookieSlugs);
    }

    public Book getBookByBookId(Integer bookId) {
        return bookRepository.findById(bookId).get();
    }
}
