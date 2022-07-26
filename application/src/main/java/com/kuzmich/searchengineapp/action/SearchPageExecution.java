package com.kuzmich.searchengineapp.action;

import com.kuzmich.searchengineapp.dto.searchDto.SearchPageData;
import com.kuzmich.searchengineapp.entity.Index;
import com.kuzmich.searchengineapp.entity.Lemma;
import com.kuzmich.searchengineapp.entity.Page;
import com.kuzmich.searchengineapp.repository.IndexRepository;
import com.kuzmich.searchengineapp.repository.LemmaRepository;
import com.kuzmich.searchengineapp.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.morphology.LuceneMorphology;

import lombok.extern.log4j.Log4j2;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class SearchPageExecution {
    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;
    private final PageRepository pageRepository;


    public List<SearchPageData> getSearchResultListFromUserQuery(String userQuery) {
        try {
            long startMethod = System.currentTimeMillis() / 1_000;
            List<Lemma> lemmaListFromUserQuery = findLemmasByWordsFromUserQuery(userQuery);
            List<SearchPageData> resultList = new ArrayList<>();
            if (!lemmaListFromUserQuery.isEmpty()) {
                List<Index> indexList = indexRepository.findAllByLemmaId(lemmaListFromUserQuery.get(0).getId());
                for (int i = 1; i < lemmaListFromUserQuery.size(); i++) {
                    int position = i;
                    indexList.removeIf(index -> index.getLemma().getId() == lemmaListFromUserQuery.get(position).getId());
                }
                if (!indexList.isEmpty()) {
                    for (Index index : indexList) {
                        long startIndex = System.currentTimeMillis() / 1000;
                        int pageId = index.getPage().getId();
                        Page page = pageRepository.findById(pageId).orElseThrow(() -> new RuntimeException("Page not found"));
                        String siteName = page.getSite().getName();
                        String siteUrl = page.getSite().getUrl();
                        String pageUri = page.getPath();
                        String pageTitle = Jsoup.parse(page.getContent()).title();
                        log.info(pageId + " - " + pageUri + " - " + pageTitle);
                        String pageSnippetByUserQuery = createSnippetFromPage(lemmaListFromUserQuery, page.getContent());
                        float sumRank = 0;

                        for (Lemma lemma : lemmaListFromUserQuery) {
                            Optional<Index> indexOptional = indexRepository.findLemmaRankFromIndex(pageId, lemma.getId());
                            if (indexOptional.isPresent()) {
                                sumRank += indexOptional.get().getRank();
                            }
                        }
                        SearchPageData searchPageData = SearchPageData
                                .builder()
                                .site(siteUrl)
                                .siteName(siteName)
                                .uri(pageUri)
                                .title(pageTitle)
                                .snippet(pageSnippetByUserQuery)
                                .relevance(sumRank)
                                .build();
                        log.info("сниппет: {}, релевантность: {}", pageSnippetByUserQuery, sumRank);
                        resultList.add(searchPageData);
                        long duration = System.currentTimeMillis() / 1000 - startIndex;
                        log.info("search for: pageID {}, path {}; duration: {}", page.getId(), page.getPath(), duration);
                    }
                    resultList.sort(Comparator.comparing(SearchPageData::getRelevance).reversed());
                    long duration = System.currentTimeMillis() / 1000 - startMethod;
                    log.info("ДЛИТЕЛЬНОСТЬ поиска: {}", duration);
                    return resultList;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String cleanUpHtml(String text) {
        String regex = "(\"?[А-яЁё]+?(-[А-яЁё]+|[\"\\s+\\n+\\.,!\\?])?\\s?|\\n\\d+\\s?)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> wordList = new ArrayList<>();
        while (matcher.find()) {
            wordList.add(matcher.group());
        }
        return String.join("...", wordList);
    }

    private String createSnippetFromPage(List<Lemma> lemmaListFromQuery, String text) throws IOException {
        long startSnippet = System.currentTimeMillis() / 1000;
        String parseText = Jsoup.parse(text).text();
        String regexFirstLetter = lemmaListFromQuery.stream()
                .map(Lemma::getLemma)
                .map(w -> w.substring(0, 1))
                .distinct()
                .collect(Collectors.joining("|"));
        String regex = "\\b".concat("(").concat(regexFirstLetter).concat(")").concat("[А-яЁё]+(-[А-яЁё]+)?");
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parseText.toLowerCase(Locale.ROOT));
        TreeMap<String, String> lemma2word = new TreeMap<>();
        Set<Integer> wordIndexes = new TreeSet<>();
        while (matcher.find()) {
            String word = matcher.group();
            String normalWordForm = new RussianLuceneMorphology().getNormalForms(word.toLowerCase(Locale.ROOT)).get(0);
            Set<String> commonObjects = lemmaListFromQuery.stream()
                    .map(Lemma::getLemma)
                    .filter(w -> w.equals(normalWordForm))
                    .collect(Collectors.toSet());
            if (!commonObjects.isEmpty()) {
                for (String lemmaWord : commonObjects) {
                    log.info("лемма " + lemmaWord + " - " + "слово " + word.toUpperCase(Locale.ROOT));
                    String result = lemma2word.putIfAbsent(lemmaWord, word);
                    if (result == null) {
                        wordIndexes.add(text.toLowerCase(Locale.ROOT).indexOf(word));
                    }
                }
            }
            if (wordIndexes.size() == lemmaListFromQuery.size()) {
                break;
            }
        }

        String partHtml = wordIndexes.stream().map(i -> text.substring(i - 100, i + 100)).collect(Collectors.joining(" "));
        String cleanSnippet = cleanUpHtml(partHtml.toLowerCase(Locale.ROOT));
        for (String value : lemma2word.values()) {
            cleanSnippet = cleanSnippet.replaceAll("\\b".concat(value), "<b>".concat(value).concat("</b>"));
        }

        long duration = System.currentTimeMillis() / 1000 - startSnippet;
        log.info("Длительность получения СНИППЕТА: {}", duration);
        return cleanSnippet;
    }

    public List<Lemma> findLemmasByWordsFromUserQuery(String userQuery) throws IOException {
        List<Lemma> lemmaList = new ArrayList<>();
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();
        String[] queryWords = userQuery.split(" ");

        for (String word : queryWords) {
            if (luceneMorph.getMorphInfo(word.toLowerCase(Locale.ROOT)).stream().anyMatch(i -> i.contains("ПРЕДЛ") || i.contains("СОЮЗ")
                    || i.contains("ЧАСТ") || i.contains("МЕЖД"))) {
                continue;
            }
            List<Lemma> formWords = luceneMorph.getNormalForms(word.toLowerCase(Locale.ROOT)).get(0).lines()
                    .map(lemmaRepository::findLemmaObjectByLemmaName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            lemmaList.addAll(formWords);
        }
        lemmaList.sort(Comparator.comparing(Lemma::getFrequency).thenComparing(Lemma::getLemma));
        return lemmaList;
    }
}



