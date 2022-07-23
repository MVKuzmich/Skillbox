import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final String FILE_PATH = "src/main/resources/metro.json";

    public static void main(String[] args) throws Exception {
        JsonFileCreation file = new JsonFileCreation();
        JSONObject metroJSON = file.createJsonFile();
        file.writeJSONFile(metroJSON);
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            JSONObject rootJSONObject = (JSONObject) parser.parse(reader);
            JSONArray lines = (JSONArray) rootJSONObject.get("lines");
            for (Object item : lines) {
                JSONObject lineJSON = (JSONObject) item;
                String lineNumber = (String) lineJSON.get("number");
                String lineName = (String) lineJSON.get("name");
                Line metroLine = new Line(lineNumber, lineName);
                JSONObject stationsJSON = (JSONObject) rootJSONObject.get("stations");
                JSONArray lineStationsJSON = (JSONArray) stationsJSON.get(metroLine.getNumber());
                for (Object obj : lineStationsJSON) {
                    String lineStation = (String) obj;
                    Station station = new Station(lineStation, metroLine);
                    metroLine.addStation(station);
                }
                System.out.println("Линия № "  + metroLine.getNumber() + "\"" + metroLine.getName() + "\" " + "- количество станций " +
                        metroLine.getStations().size());
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

}


