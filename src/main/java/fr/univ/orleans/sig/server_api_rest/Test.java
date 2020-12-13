package fr.univ.orleans.sig.server_api_rest;

import fr.univ.orleans.sig.server_api_rest.services.SuperService;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;

public class Test {

    public static void main(String[] args) {
        try {
            Point point = (Point) SuperService.wktToGeometry("POINT (0 2)");
            Point point2 = (Point) SuperService.wktToGeometry("POINT (0 3)");
            Point point3 = (Point) SuperService.wktToGeometry("POINT (0 4)");
            Polygon polygon = (Polygon) SuperService.wktToGeometry("POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))");
            System.out.println(polygon.contains(point));
            LineString lineString = (LineString) SuperService.wktToGeometry(
                    "LINESTRING ("+
                            point.getX() + " " + point.getY() + ", " +
                            point2.getX() + " " + point2.getY() + ", " +
                            point3.getX() + " " + point3.getY() + ")");
            System.out.println(lineString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
