package fr.univ.orleans.sig.server_api_rest.dtos;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;

public class LineStringDTO {

    private final String type;
    private coordinates;

    ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (
    Coordinate coordinate : lineString.getCoordinates()) {
        ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
        coordinates.add(coords);
    }
        return new HashMap<>() {{
        put("type", "LineString");
        put("coordinates", coordinates);
    }};
}
