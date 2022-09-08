package com.kuzmich.searchengineapp.controller;


import com.kuzmich.searchengineapp.dto.ResultDTO;
import com.kuzmich.searchengineapp.dto.statistics.Result;
import com.kuzmich.searchengineapp.service.IndexingService;
import com.kuzmich.searchengineapp.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
public class IndexingController {

    private final StatisticsService statisticsService;
    private final IndexingService indexingService;


    @GetMapping("/statistics")
    @ResponseBody
    public Result getStatisticInformation() {
        return statisticsService.getStatisticInformation();
    }


    @GetMapping("/startIndexing")
    @ResponseBody
    public ResultDTO startIndexing() {
        return indexingService.executeIndexation();
    }

    @GetMapping("/stopIndexing")
    @ResponseBody
    public ResultDTO stopIndexing() {
        log.info("ОСТАНОВКА ИНДЕКСАЦИИ - ОСТАНОВКА ИНДЕКСАЦИИ - ОСТАНОВКА ИНДЕКСАЦИИ");
        return indexingService.stopIndexation();
    }

    @PostMapping("/indexPage")
    @ResponseBody
    public ResultDTO executePageIndexing(@RequestParam("url") String url) {
        return indexingService.executePageIndexation(url);
    }


}

