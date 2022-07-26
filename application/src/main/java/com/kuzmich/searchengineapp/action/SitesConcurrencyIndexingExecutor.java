package com.kuzmich.searchengineapp.action;

import com.kuzmich.searchengineapp.config.SiteConfig;
import com.kuzmich.searchengineapp.entity.Page;
import com.kuzmich.searchengineapp.entity.Site;
import com.kuzmich.searchengineapp.entity.Status;
import com.kuzmich.searchengineapp.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class SitesConcurrencyIndexingExecutor {

    private final SiteConfig siteConfig;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final LemmaRepository lemmaRepository;
    private final IndexRepository indexRepository;
    private final FieldRepository fieldRepository;

    @Setter
    @Getter
    private boolean isExecuting;

    public void executeSitesIndexing() {
        if (!isExecuting) {
            long start = System.currentTimeMillis() / 1000;
            List<SiteConfig.SiteObject> siteObjects = siteConfig.getSiteArray();

            siteObjects.stream().map(siteObject -> {
                        Optional<Site> foundSite = siteRepository.findByNameAndUrl(siteObject.getName(), siteObject.getUrl());
                        foundSite.ifPresent(s -> siteRepository.removeSiteById(s.getId()));
                        siteRepository.flush();
                        WebSiteAnalyzer wsa = new WebSiteAnalyzer(pageRepository, lemmaRepository, indexRepository, fieldRepository);
                        Site site = getSite(siteObject);
                        wsa.setSite(site);
                        wsa.setMainPath(site.getUrl());
                        return wsa;
                    })
                    .forEach(wsa -> {
                        Thread thread = new Thread(() -> new ForkJoinPool().invoke(wsa));
                        thread.start();
                        setExecuting(true);

                    });

            setExecuting(false);
            siteRepository.findAll()
                    .forEach(site -> siteRepository.updateSiteStatusAfterIndexation(Status.INDEXED, site.getId()));
        }
    }

    private Site getSite(SiteConfig.SiteObject siteObject) {
        Site site = new Site(
                Status.INDEXING,
                System.currentTimeMillis(),
                "",
                siteObject.getUrl(),
                siteObject.getName()
        );
        return siteRepository.saveAndFlush(site);

    }

}



