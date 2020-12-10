package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.SalleRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class Service {

//    @Autowired
//    private static SalleRepository salleRepository;

    public static Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

    public static void main(String[] args) {
        String point = "POINT (0 0)";
        String str = "010600000001000000010300000001000000050000000000000000000000000000000000000000000000000000000000000000001440000000000000204000000000000014400000000000002040000000000000000000000000000000000000000000000000";
        try {
            System.out.println(wktToGeometry(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Optional<Salle> salle = salleRepository.findById(1);
//        Polygon geom = salle.get().getGeo();
//        System.out.println(geom.toString());
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
