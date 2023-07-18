package com.example.bookshopapp.repository;


import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.dto.AuthorDto;
import com.example.bookshopapp.dto.BookFileDto;
import com.example.bookshopapp.dto.BookModelDto;
import com.example.bookshopapp.dto.BookUnitModelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

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

    @Query("select new com.example.bookshopapp.dto.AuthorDto(a.slug, a.name) " +
            "from Book b " +
            "join b.book2AuthorEntitySet b2a " +
            "join b2a.author a " +
            "where b.id = :bookId " +
            "order by b2a.sortIndex")
    List<AuthorDto> findAuthorsByBookId(Integer bookId);

    @Query("select b.id as id, b.slug as slug, b.image as image, b.title as title, " +
            "b.price * 100 as discount, " +
            "b.isBestseller as bestseller, " +
            "b.description, b.priceOld, b.priceOld * (1 - b.price) as discountPrice " +
            "from Book b")
    Page<BookModelDto> findAllRecommendedBooks(Pageable pageable);

    @Query("select b.id as id, b.slug as slug, b.image as image, b.title as title, " +
            "b.price * 100 as discount, " +
            "b.isBestseller as bestseller, " +
            "b.description, b.priceOld, b.priceOld * (1 - b.price) as discountPrice " +
            "from Book b " +
            "where b.pubDate > :fromDate and b.pubDate < :endDate " +
            "order by b.pubDate desc")
    Page<BookModelDto> findAllRecentBooksBetween(LocalDate fromDate, LocalDate endDate, Pageable pageable);

    @Query("select b.id as id, b.slug as slug, b.image as image, b.title as title, " +
            "b.price * 100 as discount, " +
            "b.isBestseller as bestseller, " +
            "b.description, b.priceOld, b.priceOld * (1 - b.price) as discountPrice " +
            "from Book b " +
            "order by b.pubDate desc")
    Page<BookModelDto> findAllRecentBooks(Pageable pageable);

    @Query(value = "select res.id,  res.slug, res.bestseller, res.image, res.title, res.discount, " +
            "res.description, res.price, res.discountPrice " +
            "from (" +
            "select b.id, b.slug, b.image, b.title, " +
            "b.discount * 100 as discount, " +
            "b.is_bestseller as bestseller, " +
            "b.description, b.price, b.price * (1 - b.discount) as discountPrice, " +
            "(count(case when b2ut.code = 'PAID' then 1 else null end) + " +
            "count(case when b2ut.code = 'CART' then 1 else null end) * 0.7 + " +
            "count(case when b2ut.code = 'KEPT' then 1 else null end) * 0.4) " +
            "as popularity " +
            "from books b " +
            "join book2user as b2u on b.id = b2u.book_id " +
            "join book2user_type b2ut on b2u.type_id = b2ut.id " +
            "group by b.id, b.slug, b.image, b.title) as res " +
            "order by popularity desc, res.title",
            nativeQuery = true,
            countQuery = "select count(*) from (" +
                    "select b.id, b.slug, b.image, b.title, b.discount * 100 as discount, " +
                    "b.is_bestseller as bestseller, " +
                    "b.price, b.price * (1 - b.discount) as discountPrice, " +
                    "(count(case when b2ut.code = 'PAID' then 1 else null end) + " +
                    "count(case when b2ut.code = 'CART' then 1 else null end) * 0.7 + " +
                    "count(case when b2ut.code = 'KEPT' then 1 else null end) * 0.4) " +
                    "as popularity " +
                    "from books b " +
                    "join book2user as b2u on b.id = b2u.book_id " +
                    "join book2user_type b2ut on b2u.type_id = b2ut.id " +
                    "group by b.id, b.slug, b.image, b.title) as res")
    Page<BookModelDto> findPopularBooks(Pageable pageable);

    @Query(value = "select b.id as id, b.slug as slug, b.image as image, b.title as title, " +
            "b.discount * 100 as discount, " +
            "b.is_bestseller as bestseller, " +
            "round(avg(br.value)) as rating, " +
            "b.description as description, b.price as price, b.price * (1 - b.discount) as discountPrice " +
            "from books b " +
            "left join book_rates br on b.id = br.book_id " +
            "where b.slug = :bookSlug " +
            "group by b.id, b.slug, b.image, b.title",
            nativeQuery = true)
    Optional<BookUnitModelDto> findBookUnitBySlug(String bookSlug);


    @Query(nativeQuery = true,
            value = "select b.id, b.slug, b.image, b.title, " +
                    "b.discount * 100 as discount, " +
                    "cast( " +
                    "CASE " +
                    "WHEN b.is_bestseller = 1 THEN 'true' " +
                    "ELSE 'false' " +
                    "END as boolean) as bestseller, " +
                    "b.description, " +
                    "b.price, b.price * (1 - b.discount) as discountPrice " +
                    "from books b " +
                    "join book2tag b2t on b.id = b2t.book_id " +
                    "join tags t on b2t.tag_id = t.id " +
                    "where t.id = :tagId order by b.pub_date desc",
            countQuery = "select count(*) from books b " +
                    "join book2tag b2t on b.id = b2t.book_id " +
                    "join tags t on b2t.tag_id = t.id " +
                    "where t.id = :tagId")
    Page<BookModelDto> findBooksByTagId(Integer tagId, Pageable pageable);


}


