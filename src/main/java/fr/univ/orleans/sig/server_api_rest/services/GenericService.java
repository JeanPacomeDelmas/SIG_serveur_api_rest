package fr.univ.orleans.sig.server_api_rest.services;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.*;

public interface GenericService<T> {

    static Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

    static Map<String, Object> polygonDTO(Polygon polygon) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : polygon.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new HashMap<>() {{
            put("type", "Polygon");
            put("coordinates", new ArrayList<>(Collections.singletonList(coordinates)));
        }};
    }

    static Map<String, Object> lineStringDTO(LineString lineString) {
        ArrayList<Double> coordinates = new ArrayList<>();
        for (Coordinate coordinate : lineString.getCoordinates()) {
            coordinates.add(coordinate.x);
            coordinates.add(coordinate.y);
        }
        return new HashMap<>() {{
            put("type", "LineString");
            put("coordinates", new ArrayList<>(Collections.singletonList(coordinates)));
        }};
    }

    Collection<T> findAll();
    T findById(int id);
    T save(T entity);
    T update(T entity);
    boolean delete(T entity);

}

//    public void insertPolygon(String polygon) {
//        PointEntity entity = new PointEntity();
//        entity.setPoint((Point) wktToGeometry(point));
//        session.persist(entity);
//    }
//
//    public Geometry createCircle(double x, double y, double radius) {
//        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
//        shapeFactory.setNumPoints(32);
//        shapeFactory.setCentre(new Coordinate(x, y));
//        shapeFactory.setSize(radius * 2);
//        return shapeFactory.createCircle();
//    }