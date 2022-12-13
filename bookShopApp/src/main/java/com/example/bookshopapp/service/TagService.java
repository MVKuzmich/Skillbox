package com.example.bookshopapp.service;

import com.example.bookshopapp.data.book.links.Book2TagEntity;
import com.example.bookshopapp.data.tag.TagEntity;
import com.example.bookshopapp.dto.TagDto;
import com.example.bookshopapp.repository.Book2TagRepository;
import com.example.bookshopapp.repository.TagRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    public static int BASE_HEIGHT_10 = 10;
    public static int TRANSITION_INDEX = 5;


    private TagRepository tagRepository;
    private Book2TagRepository book2TagRepository;

    public TagService(TagRepository tagRepository, Book2TagRepository book2TagRepository) {
        this.tagRepository = tagRepository;
        this.book2TagRepository = book2TagRepository;
    }

    public List<TagDto> getAllTagsDto() {
        return tagRepository.findAll().stream()
                .map(entity -> new TagDto(entity.getId(), entity.getDescription(), calculateTagSize(entity.getId())))
                .collect(Collectors.toList());
    }

    public String calculateTagSize(Integer tagId) {
        List<Book2TagEntity> book2TagList = book2TagRepository.findAll();
         int countTag = (int) book2TagList.stream()
                .filter(book2tag -> book2tag.getTagId().intValue() == tagId.intValue())
                .count();
         int tagFrequency = countTag * 100 / book2TagList.size();

         return String.valueOf(BASE_HEIGHT_10 + tagFrequency * TRANSITION_INDEX).concat("px");
    }

    public TagEntity getTagByTagId(Integer tagId) {
        return tagRepository.findByTagId(tagId);
    }
}
