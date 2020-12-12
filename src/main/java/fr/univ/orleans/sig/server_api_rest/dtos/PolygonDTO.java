package fr.univ.orleans.sig.server_api_rest.dtos;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.util.*;

public class PolygonDTO {

    private final String type;
    private ArrayList<ArrayList<ArrayList<Double>>> coordinates;

    public static PolygonDTO create(Polygon polygon) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : polygon.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new PolygonDTO(new ArrayList<>(Collections.singletonList(coordinates)));
    }

    private PolygonDTO() {
        this.type = "Polygon";
    }

    private PolygonDTO(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.type = "Polygon";
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public ArrayList<ArrayList<ArrayList<Double>>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<ArrayList<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

}
