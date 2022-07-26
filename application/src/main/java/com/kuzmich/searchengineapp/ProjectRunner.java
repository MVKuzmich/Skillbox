package com.kuzmich.searchengineapp;

import com.kuzmich.searchengineapp.action.SearchPageExecution;
import com.kuzmich.searchengineapp.action.SitesConcurrencyIndexingExecutor;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class ProjectRunner implements CommandLineRunner {

    private final SearchPageExecution searchPageExecution;
    private final SitesConcurrencyIndexingExecutor sitesParsingExecutor;


    @Override
    public void run(String... args) {


    }
}
