package fr.univ.orleans.sig.server_api_rest;

import fr.univ.orleans.sig.server_api_rest.services.SuperService;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

public class Test {

    public static double coeffDirecteur(LineString lineString) {
        return (lineString.getEndPoint().getY() - lineString.getStartPoint().getY()) / (lineString.getEndPoint().getX() - lineString.getStartPoint().getX());
    }

    public static void main(String[] args) {
        try {
            Point A = (Point) SuperService.wktToGeometry("POINT (5 10)");
            Point B = (Point) SuperService.wktToGeometry("POINT (8 12)");
            Point C = (Point) SuperService.wktToGeometry("POINT (9 11)");
            Point D = (Point) SuperService.wktToGeometry("POINT (3 7)");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
