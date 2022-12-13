package com.example.bookshopapp.repository;

import com.example.bookshopapp.data.tag.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    @Query("select t from TagEntity t where t.id = :tagId")
    TagEntity findByTagId(Integer tagId);
}
