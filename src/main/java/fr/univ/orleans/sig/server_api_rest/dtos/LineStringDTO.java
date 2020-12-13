package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

import java.util.ArrayList;
import java.util.Arrays;

public class LineStringDTO {

    private final String type;
    private ArrayList<ArrayList<Double>> coordinates;
    private EtageDTO attributes;

    public static LineStringDTO create(LineString lineString, Etage etage) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : lineString.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new LineStringDTO(coordinates, EtageDTO.create(etage));
    }

    private LineStringDTO() {
        this.type = "LineString";
    }

    private LineStringDTO(ArrayList<ArrayList<Double>> coordinates, EtageDTO attributes) {
        this.type = "LineString";
        this.coordinates = coordinates;
        this.attributes = attributes;
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

    public EtageDTO getAttributes() {
        return attributes;
    }

    public void setAttributes(EtageDTO attributes) {
        this.attributes = attributes;
    }

}
