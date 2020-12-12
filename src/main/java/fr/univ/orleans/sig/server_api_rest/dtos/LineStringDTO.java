package fr.univ.orleans.sig.server_api_rest.dtos;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

import java.util.ArrayList;
import java.util.Arrays;

public class LineStringDTO {

    private final String type;
    private ArrayList<ArrayList<Double>> coordinates;

    public static LineStringDTO create(LineString lineString) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : lineString.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new LineStringDTO(coordinates);
    }

    private LineStringDTO() {
        this.type = "LineString";
    }

    private LineStringDTO(ArrayList<ArrayList<Double>> coordinates) {
        this.type = "LineString";
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public ArrayList<ArrayList<Double>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Double>> coordinates) {
        this.coordinates = coordinates;
    }

}
