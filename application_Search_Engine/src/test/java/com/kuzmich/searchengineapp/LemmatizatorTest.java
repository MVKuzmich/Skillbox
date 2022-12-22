package com.kuzmich.searchengineapp;

import com.kuzmich.searchengineapp.action.Lemmatizator;
import lombok.SneakyThrows;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LemmatizatorTest {

    private Lemmatizator lemmatizator;

    private LuceneMorphology luceneMorphology;

    @SneakyThrows
    @BeforeEach
    void prepare() {
        luceneMorphology = new RussianLuceneMorphology();
        lemmatizator = new Lemmatizator(luceneMorphology);
    }


    @SneakyThrows
    @Test
    void countLemmasAfterLemmatizator() {
        String text = "конюшня бывает из дерева и покрашена краской";

        Map<String, Integer> result = lemmatizator.getLemmaList(text);
        assertThat(result.keySet()).hasSize(5);


    }

}
