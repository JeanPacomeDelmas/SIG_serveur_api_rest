package fr.univ.orleans.sig.server_api_rest.dtos;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.util.*;

public class PolygonDTO {

    private final String type;
    private Object coordinates;

    public static PolygonDTO create(Polygon polygon) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (
                Coordinate coordinate : polygon.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new PolygonDTO(new ArrayList<>(Collections.singletonList(coordinates)));
    }

    private PolygonDTO(Object coordinates) {
        this.type = "Polygon";
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
