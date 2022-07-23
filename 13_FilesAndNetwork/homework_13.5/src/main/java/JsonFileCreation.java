import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFileCreation {

    private String filePath = "data/code.html";

    public JSONObject createJsonFile() throws Exception {
        StringBuilder builder = new StringBuilder();
        List<String> htmlLines = Files.readAllLines(Paths.get(filePath));

        htmlLines.forEach(l -> builder.append(l).append("\n"));
        String htmlFile = builder.toString();

        // Парсинг сайта
        Document document = Jsoup.parse(htmlFile);
        Elements elements = document.select("span[data-line]");

        // Получение номеров линий
        List<String> lineNumbers = elements.stream().map(e -> e.attr("data-line")).collect(Collectors.toList());

        Line line;
        Station station;

        JSONObject metroJSON = new JSONObject();
        JSONArray linesJSON = new JSONArray();
        JSONObject stationsLineJSON = new JSONObject();

        // Получение названий линий и станций по линиям
        for (String lineNumber : lineNumbers) {
            String lineName = elements.select("[data-line=" + lineNumber + "]").text();
            Elements stationsElements = document.select("div[data-line=" + lineNumber + "] span.name");
            List<String> stationsList = stationsElements.stream().map(Element::text).collect(Collectors.toList());
            line = new Line(lineNumber, lineName);
            JSONObject lineJSON = new JSONObject();
            lineJSON.put("number", line.getNumber());
            lineJSON.put("name", line.getName());
            linesJSON.add(lineJSON);
            JSONArray stationsJSON = new JSONArray();
            for (String stationName : stationsList) {
                station = new Station(stationName, line);
                stationsJSON.add(station.getName());
            }
            stationsLineJSON.put(line.getNumber(), stationsJSON);
        }
        //Добавление линий и станций в общий JSON metro
        metroJSON.put("stations", stationsLineJSON);
        metroJSON.put("lines", linesJSON);

        return metroJSON;
    }

    public void writeJSONFile(JSONObject object) {
        try (FileWriter writer = new FileWriter("src/main/resources/metro.json")) {
            writer.write(object.toJSONString());
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
