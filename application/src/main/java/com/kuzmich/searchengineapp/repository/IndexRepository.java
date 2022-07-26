package com.kuzmich.searchengineapp.repository;

import com.kuzmich.searchengineapp.entity.Index;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IndexRepository extends JpaRepository<Index, Integer> {
    @Query("SELECT i FROM Index i WHERE i.lemma.id = :lemmaId")
    List<Index> findAllByLemmaId(int lemmaId);

    @Query("SELECT i FROM Index i WHERE i.page.id = :pageId AND i.lemma.id = :lemmaId")
    Optional<Index> findLemmaRankFromIndex(int pageId, int lemmaId);
}
