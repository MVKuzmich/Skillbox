package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.author.Author;
import com.example.bookshopapp.data.book.Book;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    @Query("select a from Author a where a.slug = :slug")
    Author findAuthorBySlug(String slug);

}
