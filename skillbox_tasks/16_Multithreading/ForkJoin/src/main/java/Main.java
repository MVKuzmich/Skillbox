import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        ForkJoinPool pool = new ForkJoinPool();

        ParseLink task = new ParseLink("https://skillbox.ru/");

        Set<String> links = pool.invoke(task);

        List<String> structuredLinks = links.stream()
                .map(l -> "\t".repeat(l.split("/").length) + l)
                .sorted(Comparator.comparing(String::trim))
                .collect(Collectors.toList());

        try {
            Files.write(Paths.get("src/main/resources/sortedLinksFromLogger.txt"), structuredLinks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
