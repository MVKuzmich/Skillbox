package com.kuzmich.searchengineapp.repository;

import com.kuzmich.searchengineapp.entity.Lemma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LemmaRepository extends JpaRepository<Lemma, Integer> {

    @Query("select l from Lemma l where l.lemma = :lemmaName and l.site.id = :siteId")
    Optional<Lemma> findLemmaObjectByLemmaNameAndSiteId(String lemmaName, int siteId);

    @Query("select l from Lemma l where l.lemma = :lemmaName")
    Optional<Lemma> findLemmaObjectByLemmaName(String lemmaName);

    int countLemmaBySiteId(int siteId);

}
