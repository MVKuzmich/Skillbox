package com.kuzmich.searchengineapp.action;

import com.kuzmich.searchengineapp.dto.searchDto.SearchPageData;
import com.kuzmich.searchengineapp.entity.Index;
import com.kuzmich.searchengineapp.entity.Lemma;
import com.kuzmich.searchengineapp.entity.Page;
import com.kuzmich.searchengineapp.repository.LemmaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.morphology.LuceneMorphology;
import lombok.extern.log4j.Log4j2;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
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

    public List<SearchPageData> getSearchResultListFromUserQuery(String userQuery) {
        try {
            List<Lemma> lemmaObjectListFromUserQuery = findLemmasByWordsFromUserQuery(userQuery);
            List<String> lemmaWordsFromUserQuery = lemmaObjectListFromUserQuery.stream()
                    .map(Lemma::getLemma)
                    .distinct()
                    .collect(Collectors.toList());
            List<Index> totalIndexList = lemmaObjectListFromUserQuery.stream()
                    .flatMap(lemma -> lemma.getIndexList().stream())
                    .collect(Collectors.toList());
            if (lemmaWordsFromUserQuery.size() != 1) {
                Set<Integer> ids = new HashSet<>();
                List<Integer> commonPageIds = totalIndexList.stream().map(Index::getPage).map(Page::getId).filter(id -> !ids.add(id)).collect(Collectors.toList());
                totalIndexList.removeIf(index -> !commonPageIds.contains(index.getPage().getId()));
            }
            return (!totalIndexList.isEmpty())
                    ? getSearchPageDataFromIndexes(totalIndexList, lemmaWordsFromUserQuery) : new ArrayList<>();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
    public List<Lemma> findLemmasByWordsFromUserQuery(String userQuery) throws IOException {
        List<Lemma> lemmaList = new ArrayList<>();
        LuceneMorphology luceneMorph = new RussianLuceneMorphology();
        String[] queryWords = userQuery.toLowerCase().trim().split(" ");

        for (String word : queryWords) {
            if (luceneMorph.getMorphInfo(word).stream().anyMatch(i -> i.contains("ПРЕДЛ") || i.contains("СОЮЗ")
                    || i.contains("ЧАСТ") || i.contains("МЕЖД"))) {
                continue;
            }
            List<Lemma> formWords = luceneMorph.getNormalForms(word.toLowerCase(Locale.ROOT)).get(0).lines()
                    .map(lemmaRepository::findLemmaObjectByLemmaName)
                    .filter(Optional::isPresent)
                    .flatMap(lemmaListOpt -> lemmaListOpt.get().stream())
                    .collect(Collectors.toList());
            lemmaList.addAll(formWords);
        }
        lemmaList.sort(Comparator.comparing(Lemma::getFrequency).thenComparing(Lemma::getLemma));
        return lemmaList;
    }

    private List<SearchPageData> getSearchPageDataFromIndexes(List<Index> indexList, List<String> lemmaWordsFromUserQuery) throws IOException {
        long startMethod = System.currentTimeMillis() / 1_000;
        List<SearchPageData> resultList = new ArrayList<>();
        float maxRank = Float.MIN_VALUE;
        List<Integer> pageIds = indexList.stream().map(Index::getPage).map(Page::getId).distinct().collect(Collectors.toList());
        for (Integer id : pageIds) {
            long startIndex = System.currentTimeMillis() / 1000;
            Index indexForSearchPageData = indexList.stream().filter(index -> index.getPage().getId() == id).findFirst()
                    .orElseThrow(() -> new RuntimeException("Something wrong"));
            Element elementBody = Jsoup.parse(indexForSearchPageData.getPage().getContent()).body();
            float sumRank = (float) indexList.stream().filter(ind -> ind.getPage().getId() == id)
                    .mapToDouble(Index::getRank).sum();
            if (sumRank > maxRank) {
                maxRank = sumRank;
            }
            SearchPageData searchPageData = SearchPageData
                    .builder()
                    .site(indexForSearchPageData.getPage().getSite().getUrl())
                    .siteName(indexForSearchPageData.getPage().getSite().getName())
                    .uri(indexForSearchPageData.getPage().getPath())
                    .title(Jsoup.parse(indexForSearchPageData.getPage().getContent()).title())
                    .snippet(createSnippetFromPage(lemmaWordsFromUserQuery, elementBody))
                    .relevance(sumRank)
                    .build();
            log.info("сниппет: {}, релевантность: {}", searchPageData.getSnippet(), searchPageData.getRelevance());
            resultList.add(searchPageData);
            long duration = System.currentTimeMillis() / 1000 - startIndex;
            log.info("search for: pageID {}, path {}; SEARCH RESULT duration: {}", id, searchPageData.getUri(), duration);
        }
        long duration = System.currentTimeMillis() / 1000 - startMethod;
        log.info("длительность ПОИСКА: {}", duration);
        return updateSearchPageDataRelevance(maxRank, resultList).stream()
                .sorted(Comparator.comparing(SearchPageData::getRelevance).reversed())
                .collect(Collectors.toList());
    }

    private String createSnippetFromPage(List<String> lemmaWordsFromUserQuery, Element textElement) throws IOException {
        Map<Integer, String> index2textWord = findWordsAndIndexesInTextByQueryLemmas(lemmaWordsFromUserQuery, textElement);
        String partHtml = index2textWord.keySet().stream().map(wordIndex -> textElement.toString().substring(wordIndex - 100, wordIndex + 100)).collect(Collectors.joining(" "));
        String cleanSnippet = cleanUpHtml(partHtml);
        for (String word : index2textWord.values()) {
            cleanSnippet = cleanSnippet.replaceAll("\\b".concat(word), "<b>".concat(word).concat("</b>"));
        }
        return cleanSnippet;
    }

    private List<SearchPageData> updateSearchPageDataRelevance(float maxRank, List<SearchPageData> resultList) {
        return resultList.stream().peek(result -> result.setRelevance(result.getRelevance() / maxRank))
                .collect(Collectors.toList());
    }

    private Map<Integer, String> findWordsAndIndexesInTextByQueryLemmas(List<String> lemmaWordsFromUserQuery, Element element) throws IOException {
        List<String> lemmaWords = new ArrayList<>(lemmaWordsFromUserQuery);
        String text = element.toString().toLowerCase();
        String parseText = element.text().toLowerCase();
        Pattern pattern = Pattern.compile(createRegex(lemmaWords));
        Matcher matcher = pattern.matcher(parseText);
        TreeMap<Integer, String> index2word = new TreeMap<>();
        while (matcher.find()) {
            String word = matcher.group();
            String normalWordForm = new RussianLuceneMorphology().getNormalForms(word).get(0);
            if (lemmaWords.contains(normalWordForm)) {
                log.info("лемма: {}, слово: {}", normalWordForm, word.toUpperCase());
                index2word.put(text.indexOf(word), word);
                lemmaWords.remove(normalWordForm);
            }
            if (lemmaWords.isEmpty()) {
                break;
            }
        }
        return index2word;
    }

    private String createRegex(List<String> lemmaWords) {
        String regexFirstLetter = lemmaWords.stream()
                .filter(word -> word.length() > 1)
                .map(word -> word.substring(0, 1))
                .collect(Collectors.joining("|"));
        return "\\b".concat("(").concat(regexFirstLetter).concat(")").concat("([А-яЁё]+)(-\1)?");
    }

    private String cleanUpHtml(String text) {
        String regex = "(\"?(([А-яЁё\\d]+)(-\3|[\":;.,!?])?))\\s+?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> wordList = new ArrayList<>();
        while (matcher.find()) {
            wordList.add(matcher.group(1));
        }
        return String.join(" ", wordList);
    }

}



