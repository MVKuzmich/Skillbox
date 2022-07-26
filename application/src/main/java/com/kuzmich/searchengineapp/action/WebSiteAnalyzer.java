package com.kuzmich.searchengineapp.action;

import com.kuzmich.searchengineapp.entity.*;
import com.kuzmich.searchengineapp.repository.FieldRepository;
import com.kuzmich.searchengineapp.repository.IndexRepository;
import com.kuzmich.searchengineapp.repository.LemmaRepository;
import com.kuzmich.searchengineapp.repository.PageRepository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class WebSiteAnalyzer extends RecursiveAction {

    private static final String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" +
            " (KHTML, like Gecko) Chrome/100.0.4896.75 Safari/537.36";
    private static final String REFERRER = "https://www.google.com";

    @Setter
    private Site site;
    @Setter
    String mainPath;

    private final PageRepository pageRepository;
    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;
    private final FieldRepository fieldRepository;

    @Getter
    private List<WebSiteAnalyzer> taskList;

    @Setter
    @Getter
    private static boolean isIndexationStopped;

    @Override
    protected void compute() {
        if (!isIndexationStopped) {
            log.info("выполняется поток: {}, парсинг страницы: {}", Thread.currentThread().getName(), mainPath);
            taskList = new ArrayList<>();
            int twoSlashIndex = mainPath.indexOf("//") + 2;
            int slashIndex = mainPath.indexOf("/", twoSlashIndex);
            try {
                Connection jsoupConnection = Jsoup.connect(mainPath);
                Document document = jsoupConnection.userAgent(USERAGENT).referrer(REFERRER).get();
                String pathFormat = (slashIndex == -1) ? "/" : mainPath.substring(slashIndex);
                int statusCode = jsoupConnection.execute().statusCode();
                String htmlString = document.toString();
                Page page = Page.builder().path(pathFormat).code(statusCode).content(htmlString).site(site).build();
                Page savedPage = pageRepository.saveAndFlush(page);
                log.info("SAVED page: {}, pageId: {}", page.getPath(), page.getId());
                if (savedPage.getCode() == 200)
                    new PageIndexExecutor(fieldRepository, lemmaRepository, indexRepository).executePageIndexing(savedPage);
                Elements elements = document.select("[abs:href^=" + mainPath + "]");
                Set<String> links = elements.stream()
                        .map(e -> e.attr("abs:href"))
                        .filter(e -> e.startsWith(mainPath))
                        .collect(Collectors.toSet());
                if (!links.isEmpty()) {
                    links.removeIf(link -> link.equals(mainPath) || link.equals(mainPath.concat("/")));
                    Set<String> cutLinks = links.stream()
                            .filter(l -> l.contains("#") || l.contains("?") || l.endsWith(".css") || l.endsWith(".ico") || l.contains("=")
                                    || l.endsWith(".png") || l.endsWith(".svg") || l.endsWith("jpg") || l.endsWith(".jpeg") || l.endsWith(".JPG"))
                            .collect(Collectors.toSet());
                    links.removeAll(cutLinks);
                    for (String link : links) {
                        if (isIndexationStopped()) {
                            break;
                        }
                        WebSiteAnalyzer task = new WebSiteAnalyzer(pageRepository, lemmaRepository, indexRepository, fieldRepository);
                        task.setSite(site);
                        task.setMainPath(link);
                        task.fork();
                        taskList.add(task);
                        Thread.sleep((long) (500 + Math.random() * 4500));
                    }
                    for (WebSiteAnalyzer web : taskList) {
                        web.join();
                    }
                }
            } catch (HttpStatusException hse) {
                String pathFormat = (slashIndex == -1) ? "/" : hse.getUrl().substring(slashIndex);
                Page page = Page.builder().path(pathFormat).code(hse.getStatusCode()).content("no content").site(site).build();
                pageRepository.saveAndFlush(page);
            } catch (IllegalStateException | IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}






