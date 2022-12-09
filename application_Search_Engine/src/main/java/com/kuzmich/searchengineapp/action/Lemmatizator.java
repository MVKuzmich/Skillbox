package com.kuzmich.searchengineapp.action;


import lombok.extern.log4j.Log4j2;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.*;

@Log4j2
public class Lemmatizator {

    public Map<String, Integer> getLemmaList(String text) throws IOException {
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();
        Map<String, Integer> lemmaMap = new HashMap<>();
        Set<String> foundWords = new HashSet<>();
        Collections.addAll(foundWords, text.split("\\s"));
        String wordRegexForWord = "\"?[А-яЁё]+(?:-\1)?[\".?!,:;']?";
        for (String word : foundWords) {
            if (word.matches(wordRegexForWord)) {
                word = word.replaceAll("[\".?!,:;']", "");
                List<String> wordBaseForms = luceneMorph.getMorphInfo(word);
                if (wordBaseForms.stream().anyMatch(w -> !w.matches(".+?ПРЕДЛ|СОЮЗ|ЧАСТ|МЕЖД.+?"))) {
                    luceneMorph.getNormalForms(word)
                            .forEach(w -> {
                                lemmaMap.computeIfPresent(w, (key, value) -> value + 1);
                                lemmaMap.putIfAbsent(w, 1);
                            });
                }
            }
        }
        return lemmaMap;
    }
}


