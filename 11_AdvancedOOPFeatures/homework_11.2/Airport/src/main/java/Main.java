import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {


    }
    //Метод должден вернуть список рейсов вылетающих в ближайшие два часа.
    public static List<Flight> findPlanesLeavingInTheNextTwoHours(Airport airport) {
        List<Flight> planesLeavingInTheNextTwoHours = airport.getTerminals().stream()
                .flatMap(t -> t.getFlights().stream())
                .filter(f -> f.getType() == Flight.Type.DEPARTURE)
                .filter(f -> f.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().
                        isAfter(LocalDateTime.now()) && f.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().
                        isBefore(LocalDateTime.now().plusHours(2)))
                .collect(Collectors.toList());
        return planesLeavingInTheNextTwoHours;
    }

}