package com.kuzmich.searchengineapp;

import com.kuzmich.searchengineapp.action.SearchPageExecution;
import com.kuzmich.searchengineapp.repository.LemmaRepository;
import com.kuzmich.searchengineapp.repository.SiteRepository;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SearchPageExecutionTest {

    @InjectMocks
    private SearchPageExecution searchPageExecution;
    @Mock
    private LemmaRepository lemmaRepository;
    @Mock
    private SiteRepository siteRepository;


    @BeforeEach
    void prepare() {
        searchPageExecution = new SearchPageExecution(lemmaRepository, siteRepository);
    }


    @Test
    void createRegexTest() {
        List<String> wordList = List.of("лучший", "друг");
        try {
            Method method = SearchPageExecution.class.getDeclaredMethod("createRegex", List.class);
            method.setAccessible(true);
            assertThat(method.invoke(searchPageExecution, wordList)).isEqualTo("\\b(л|д)([А-яЁё]+)(-\1)?");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    @ParameterizedTest
    @CsvFileSource(resources = "/findSentenceTestData.csv", delimiter = '|', numLinesToSkip = 2)
    void getSentenceTest(String word, String text, String expected) {
        try {
            Method method = SearchPageExecution.class.getDeclaredMethod(
                    "getSentenceFromTextElement", String.class, String.class);
            method.setAccessible(true);
            assertThat(method.invoke(searchPageExecution, word, text)).isEqualTo(expected);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }


}
