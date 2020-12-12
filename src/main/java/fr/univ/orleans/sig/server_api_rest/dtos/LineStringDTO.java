package fr.univ.orleans.sig.server_api_rest.dtos;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LineStringDTO {

    private final String type;
    private Object coordinates;

    public static LineStringDTO create(LineString lineString) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : lineString.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new LineStringDTO(coordinates);
    }

    public LineStringDTO(Object coordinates) {
        this.type = "LineString";
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

}
