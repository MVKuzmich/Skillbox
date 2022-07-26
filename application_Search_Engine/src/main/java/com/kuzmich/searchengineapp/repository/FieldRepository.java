package com.kuzmich.searchengineapp.repository;

import com.kuzmich.searchengineapp.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Integer> {
}
