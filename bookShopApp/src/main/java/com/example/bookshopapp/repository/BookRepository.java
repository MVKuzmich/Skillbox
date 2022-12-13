package com.example.bookshopapp.repository;


import com.example.bookshopapp.data.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("select b from Book b order by b.pubDate desc")
    Page<Book> findAllOrderByPubDateDesc(Pageable pageable);

    @Query("select b from Book b order by b.isBestseller")
    Page<Book> findAllOrderByBestseller(Pageable pageable);

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    @Query("select b from Book b where b.pubDate > :fromDate and b.pubDate < :endDate order by b.pubDate desc")
    Page<Book> findAllByPubDateBetweenDesc(LocalDate fromDate, LocalDate endDate, Pageable pageable);

    @Query(value = "select res.id, res.pub_date, res.is_bestseller, res.slug, res.title, res.image, res.description, res.price, " +
            "res.discount " +
            "from (" +
            "select b.id, b.pub_date, b.is_bestseller, b.slug, b.title, b.image, b.description, b.price, b.discount, " +
            "(count(case when b2ut.code = 'PAID' then 1 else null end) " +
            "+ count(case when b2ut.code = 'CART' then 1 else null end) * 0.7 " +
            "+ count(case when b2ut.code = 'KEPT' then 1 else null end) * 0.4) " +
            "as popularity " +
            "from books b " +
            "join book2user as b2u on b.id = b2u.book_id " +
            "join book2user_type b2ut on b2u.type_id = b2ut.id " +
            "group by b.id) as res " +
            "order by popularity desc, res.title",
            countQuery = "select count(*) from (" +
                    "select b.id, b.pub_date, b.is_bestseller, b.slug, b.title, b.image, b.description, b.price, b.discount, " +
                    "(count(case when b2ut.code = 'PAID' then 1 else null end) " +
                    "+ count(case when b2ut.code = 'CART' then 1 else null end) * 0.7 " +
                    "+ count(case when b2ut.code = 'KEPT' then 1 else null end) * 0.4) " +
                    "as popularity " +
                    "from books b " +
                    "join book2user as b2u on b.id = b2u.book_id " +
                    "join book2user_type b2ut on b2u.type_id = b2ut.id " +
                    "group by b.id) as res",
            nativeQuery = true)
    Page<Book> findAllByPopularity(Pageable pageable);


    @Query(nativeQuery = true,
            value = "select b.id, b.pub_date, b.is_bestseller, b.slug, b.title, b.image, b.description, b.price, b.discount " +
                    "from books b " +
                    "join book2tag b2t on b.id = b2t.book_id " +
                    "join tags t on b2t.tag_id = t.id " +
                    "where t.id = :tagId order by b.pub_date desc",
            countQuery = "select count(*) from books b " +
                    "join book2tag b2t on b.id = b2t.book_id " +
                    "join tags t on b2t.tag_id = t.id " +
                    "where t.id = :tagId")
    Page<Book> findBooksByTagId(Integer tagId, Pageable pageable);

    @Query("select b from Book b where b.slug = :slug")
    Book findBySlug(String slug);

    /*
    SPECIAL METHODS FOR REST API
     */

    @Query(nativeQuery = true,
    value = "select * from books b " +
            "join book2author b2a on b.id = b2a.book_id " +
            "join authors a on b2a.author_id = a.id " +
            "where a.name like '%:authorName%'")
    List<Book> findBooksByAuthorNameContaining(String authorName);

    List<Book> findBooksByTitleContaining(String bookTitle);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestsellers();

    @Query(value = "SELECT * FROM books WHERE discount = (SELECT MAX(discount) FROM books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();


    List<Book> findBooksBySlugIn(String[] cookieSlugs);
}

