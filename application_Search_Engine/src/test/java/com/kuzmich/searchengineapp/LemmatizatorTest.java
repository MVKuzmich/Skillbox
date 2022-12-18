package com.kuzmich.searchengineapp;

import com.kuzmich.searchengineapp.action.Lemmatizator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LemmatizatorTest {

    @SneakyThrows
    @Test
    public void countLemmasAfterLemmatizator() {
        Lemmatizator lemmatizator = new Lemmatizator();
        String text = "конюшня бывает из дерева и покрашена краской";
        Map<String, Integer> result = lemmatizator.getLemmaList(text);
        assertThat(result.keySet().size()).isNotNull();
        assertThat(result.values().size()).isNotNull();

    }

}
