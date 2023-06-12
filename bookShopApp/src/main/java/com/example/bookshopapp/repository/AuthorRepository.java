package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.author.Author;
import com.example.bookshopapp.dto.AuthorDto;
import com.example.bookshopapp.dto.AuthorUnitModelDto;
import com.example.bookshopapp.dto.BookDto;
import com.example.bookshopapp.dto.BookModelDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select new com.example.bookshopapp.dto.AuthorDto (a.slug, a.name) from Author a")
    List<AuthorDto> findAllAuthorDto();

    @Query("select new com.example.bookshopapp.dto.AuthorUnitModelDto " +
            "(a.id, a.photo, a.slug, a.name, a.description, count(b.id)) " +
            "from Author a " +
            "join a.book2AuthorEntitySet b2a " +
            "join b2a.book b " +
            "where a.slug = :slug " +
            "group by a.id")
    Optional<AuthorUnitModelDto> findAuthorBySlug(String slug);

    @Query(value = "select b.id, b.slug, b.image, b.title, " +
            "b.discount * 100 as discount, " +
            "cast(" +
            "CASE " +
            "WHEN b.is_bestseller = 1 THEN 'true' " +
            "ELSE 'false' " +
            "END as boolean) as bestseller, " +
            "b.description, b.price, b.price * (1 - b.discount) as discountPrice " +
            "from authors a " +
            "join book2author b2a on a.id = b2a.author_id " +
            "join books b on b2a.book_id = b.id " +
            "where a.slug = :authorSlug " +
            "group by b.id, b.slug, b.image, b.title",
            nativeQuery = true
    )
    List<BookModelDto> findBooksByAuthorSLug(String authorSlug, Pageable pageable);

}
