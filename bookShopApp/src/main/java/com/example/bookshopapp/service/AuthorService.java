package com.example.bookshopapp.service;

import com.example.bookshopapp.data.author.Author;
import com.example.bookshopapp.data.book.Book;
import com.example.bookshopapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AuthorService {

    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        return authorRepository.findAll().stream().collect(Collectors.groupingBy((Author a) -> a.getName().substring(0,1)));
    }

    public Author getAuthorBySlug(String slug) {
        return  authorRepository.findAuthorBySlug(slug);
    }

    public List<Book> getBooksByAuthorSlug(String slug, Integer offset, Integer limit) {
        return getAuthorBySlug(slug).getBookSet().stream()
                .skip((offset < 1) ? offset : (long)offset * limit)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<String> getAuthorDescription(String slug, Integer offset, Integer limit) {
        String description = getAuthorBySlug(slug).getDescription();
        List<String> sentences = new ArrayList<>();
        Pattern pattern = Pattern.compile("[^.?!]+[.?!]\\s*");
        Matcher matcher = pattern.matcher(description);
        while(matcher.find()) {
            sentences.add(matcher.group());
        }
        return (limit == null) ? sentences.stream().skip(offset).collect(Collectors.toList())
                : sentences.stream().skip(offset).limit(limit).collect(Collectors.toList());
    }
}
