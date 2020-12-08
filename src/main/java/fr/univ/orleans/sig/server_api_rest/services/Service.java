package fr.univ.orleans.sig.server_api_rest.services;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

public class Service {

    public Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
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

}
