import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class ParseLink extends RecursiveTask<Set<String>> {
    private Logger logger = LogManager.getRootLogger();

    Set<String> allLinks = new HashSet<>();
    List<ParseLink> parseLinksTasks = new ArrayList<>();
    String path;

    public ParseLink(String path) {
        this.path = path;
    }

    @Override
    protected Set<String> compute() {
        try {
            Document document = Jsoup.connect(path).get();
            logger.info(path);
            Elements elements = document.select("[abs:href^=" + path + "]");
            Set<String> links = elements.stream().map(e -> e.attr("abs:href")).collect(Collectors.toSet());
            if (links.isEmpty()) {
                throw new IllegalStateException("Блок закончен");
            } else {
                links.remove(path);
                Set<String> cutLinks = links.stream().filter(l -> l.contains("#")).collect(Collectors.toSet());
                links.removeAll(cutLinks);
                Set<String> cutLinksToDifferFiles = links.stream().filter(l -> l.matches(".+\\..+\\..+")).collect(Collectors.toSet());
                links.removeAll(cutLinksToDifferFiles);
                for (String link : links) {
                    ParseLink task = new ParseLink(link);
                    task.fork();
                    parseLinksTasks.add(task);
                    Thread.sleep((long) (100 + Math.random() * 200));
                }
                allLinks.add(path);
                for (ParseLink t : parseLinksTasks) {
                    Set<String> s = t.join();
                    allLinks.addAll(s);
                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        } catch (
                IllegalStateException e) {
            e.getMessage();
        }
        return allLinks;
    }


}
