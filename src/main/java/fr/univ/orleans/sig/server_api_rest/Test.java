package fr.univ.orleans.sig.server_api_rest;

import fr.univ.orleans.sig.server_api_rest.services.SuperService;
import fr.univ.orleans.sig.server_api_rest.services.modele.Segment;
import fr.univ.orleans.sig.server_api_rest.services.modele.Vecteur;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;

public class Test {

    public static double coeffDirecteur(LineString lineString) {
        return (lineString.getEndPoint().getY() - lineString.getStartPoint().getY()) / (lineString.getEndPoint().getX() - lineString.getStartPoint().getX());
    }

    public static Point segmentsSecants(Segment segment1, Segment segment2) throws ParseException {
        Vecteur I = segment1.toVecteur();
        Vecteur J = segment2.toVecteur();
        Point A = segment1.getDebut();
        Point C = segment2.getDebut();

        if (I.getX() * J.getY() - I.getY() * J.getX() != 0) { // pas parallele
            double m = - ( I.getX() * C.getY() + I.getY() * A.getX() - I.getX() * A.getY() - I.getY() * C.getX() ) /
                    ( I.getX() * J.getY() - I.getY() * J.getX() );
            double k = - ( A.getX() * J.getY() - C.getX() * J.getY() - J.getX() * A.getY() + J.getX() * C.getY() ) /
                    ( I.getX() * J.getY() - I.getY() * J.getX() );
            if (m > 0 && k > 0 && m < 1 && k < 1) {
                double x = A.getX() + k * I.getX();
                double y = A.getY() + k * I.getY();
                return (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Point A = (Point) SuperService.wktToGeometry("POINT (5 10)");
            Point B = (Point) SuperService.wktToGeometry("POINT (8 12)");
            Point C = (Point) SuperService.wktToGeometry("POINT (4 11)");
            Point D = (Point) SuperService.wktToGeometry("POINT (9 10)");

            Segment AB = new Segment(A, B);
            Segment CD = new Segment(C, D);

            Point point = segmentsSecants(AB, CD);
            if (point != null) {
                System.out.println(point);
            }


//            Soit le segment [AB], et le segment [CD].
//            On note I le vecteur AB, et J le vecteur CD
//
//            Soit k le parametre du point d'intersection du segment CD sur la droite AB. on sera sur le segment si 0 < k < 1
//            Soit m le parametre du point d'intersection du segment AB sur la droite CD, on sera sur le segment si 0 < m < 1
//
//            Soit P le point d'intersection
//            P = A + k * I; // equation (1)
//            P = C + m * J;
//
//            D'ou :
//            A + k * I = C + m * J
//
//            On décompose les points et vecteurs, on a :
//            Ax + k * Ix = Cx + m * Jx
//            Ay + k * Iy = Cy + m * Jy
//
//            2 équations, 2 inconnues, en résolvant, on trouve :
//
//            m = -( -Ix * Ay + Ix * Cy + Iy * Ax - Iy * Cx ) / ( Ix * Jy - Iy * Jx )
//            k = -( Ax * Jy - Cx * Jy - Jx * Ay + Jx * Cy ) / ( Ix * Jy - Iy * Jx )
//
//            (Noter que les dénominateurs sont les meme)
//            Attention, si Ix * Jy - Iy * Jx = 0 , alors les droites sont paralleles, pas d'intersection.
//            Sinon, on trouve m et k
//
//            On vérifie que 0 < m < 1 et 0 < k < 1 --> sinon, cela veut dire que les droites s'intersectent, mais pas
//            au niveau du segment.
//
//            Ensuite, pour retrouver P, on réinjecte m ou k dans une des 2 équations (1)

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
