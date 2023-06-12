package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.BookDto;
import com.example.bookshopapp.dto.BookUnitDto;
import com.example.bookshopapp.errors.BookstoreApiWrongParameterException;
import com.example.bookshopapp.mapper.BookMapper;
import com.example.bookshopapp.dto.AuthorDto;
import com.example.bookshopapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final BookFileRepository bookFileRepository;
    private final BookMapper bookMapper;



    public Page<Book> getPageOfSearchResultsBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
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


    public List<AuthorDto> getAuthors(Integer bookId) {
        return bookRepository.findAuthorsByBookId(bookId);

    }

    public BookUnitDto getBookUnitDtoByBookSlug(String slug) {
        return bookRepository.findBookUnitBySlug(slug)
                .map(bookUnitModelDto -> bookMapper.toBookUnitDto(bookUnitModelDto,
                        getAuthors(bookUnitModelDto.getId()),
                        reviewRepository.findReviewsByBookId(bookUnitModelDto.getId()),
                        tagRepository.findTagsByBookId(bookUnitModelDto.getId()),
                        bookFileRepository.findBookFileByBookId(bookUnitModelDto.getId())))
                .orElseThrow();

    }

    public Page<BookDto> getRecommendedBooks(Integer offset, Integer limit) {
        return bookRepository.findAllRecommendedBooks(PageRequest.of(offset, limit))
                .map(modelDto -> bookMapper.toBookDto(modelDto, getAuthors(modelDto.getId())));

    }

    public Page<BookDto> getRecentBooks(Integer offset, Integer limit) {
        return bookRepository.findAllRecentBooks(PageRequest.of(offset, limit))
                .map(modelDto -> bookMapper.toBookDto(modelDto, getAuthors(modelDto.getId())));
    }
    public Page<BookDto> getRecentBooksBetweenDesc(LocalDate fromDate, LocalDate endDate, Integer offset, Integer limit) {
        if (fromDate == null && endDate == null) {
            return getRecentBooks(offset, limit);
        } else {
            if (fromDate == null) {
                fromDate = LocalDate.of(1500, 1, 1);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }
            return bookRepository.findAllRecentBooksBetween(fromDate, endDate, PageRequest.of(offset, limit))
                    .map(modelDto -> bookMapper.toBookDto(modelDto, getAuthors(modelDto.getId())));
        }
    }
    public Page<BookDto> getPopularBooks(Integer offset, Integer limit) {
        return bookRepository.findPopularBooks(PageRequest.of(offset, limit))
                .map(modelDto -> bookMapper.toBookDto(modelDto, getAuthors(modelDto.getId())));
    }

    public Page<BookDto> getBooksByTagId(Integer tagId, Integer offset, Integer limit) {
        return bookRepository.findBooksByTagId(tagId, PageRequest.of(offset, limit))
                .map(bookModelDto -> bookMapper.toBookDto(bookModelDto, getAuthors(bookModelDto.getId())));
    }


}
