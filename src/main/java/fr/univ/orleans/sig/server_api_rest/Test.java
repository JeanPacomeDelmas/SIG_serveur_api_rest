//package fr.univ.orleans.sig.server_api_rest;
//
//import fr.univ.orleans.sig.server_api_rest.entities.Etage;
//import fr.univ.orleans.sig.server_api_rest.entities.Porte;
//import fr.univ.orleans.sig.server_api_rest.entities.Salle;
//import fr.univ.orleans.sig.server_api_rest.services.SuperService;
//import fr.univ.orleans.sig.server_api_rest.services.modele.Noeud2;
//import org.locationtech.jts.geom.LineString;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.geom.Polygon;
//import org.locationtech.jts.io.ParseException;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@SuppressWarnings("DuplicatedCode")
//public class Test {
//
//    private Map<LineString, Etage> findTrajet(Porte porteDepart, Salle salleArrivee) {
//        Map<LineString, Etage> trajets = new HashMap<>();
//        trajets.put(porteDepart.getGeom(), porteDepart.getSalle1().getEtage());
//        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
//            for (LineString lineString : escalierService.escalierToEscalierByLineString(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage())) {
//                trajets.put(lineString, porteDepart.getSalle1().getEtage());
//            }
////            trajets.put(escalierService.escalierToEscalierByLineString(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage()));
//        }
//        Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
//        for (Salle salle : salles) {
//            if (salle.getGid() == salleArrivee.getGid()) {
//                trajets.put(porteService.findPorteBySalle(salle,
//                        salleService.findSalleByEtageAndFonctionCouloir(salle.getEtage(),
//                                fonctionSalleService.findByNom("couloir"))).getGeom(), salle.getEtage());
//                break;
//            }
//        }
//        return trajets;
//    }
//
//    private Point milieuLineString(LineString lineString) throws ParseException {
//        Point A = lineString.getStartPoint();
//        Point B = lineString.getEndPoint();
//        double x = (A.getX() + B.getX()) / 2;
//        double y = (A.getY() + B.getY()) / 2;
//        return (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
//    }
//
//    private static Polygon polygonCouloirByEtage(Etage etage) {
//        return salleService.findSalleByEtageAndFonctionCouloir(etage, fonctionSalleService.findByNom("couloir")).getGeom();
//    }
//
//    private boolean pointInLineString(Point point, LineString lineString) {
//        return lineString.contains(point);
//    }
//
//    private Collection<LineString> borduresPolygon(Polygon polygon) throws ParseException {
//        ArrayList<LineString> lineStrings = new ArrayList<>();
//        for (int i = 0; i < polygon.getCoordinates().length - 1; i++) {
//            lineStrings.add((LineString) SuperService.wktToGeometry(
//                    "LINESTRING ("+
//                            polygon.getCoordinates()[i].getX() + " " + polygon.getCoordinates()[i].getY() + ", " +
//                            polygon.getCoordinates()[i + 1].getX() + " " + polygon.getCoordinates()[i + 1].getY() + ")"));
//        }
//        return lineStrings;
//    }
//
//    private static boolean pointInPolygon(Point point, Polygon polygon) throws ParseException {
//        if (polygon.contains(point)) {
//            return true;
//        }
//        for (LineString lineString : borduresPolygon(polygon)) {
//            if (lineString.contains(point)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private static PriorityQueue<Noeud2> voisinsNoeud(Noeud2 noeud, Noeud2 objectif) throws ParseException {
//        PriorityQueue<Noeud2> voisins = new PriorityQueue<>();
//        for (int i = - 1; i < 2; i++) {
//            for (int j = - 1; j < 2; j++) {
//                if (i != 0 || j != 0) {
////                    double x = noeud.getPoint().getX() + i * 0.5;
////                    double y = noeud.getPoint().getY() + j * 0.5;
//                    double x = noeud.getPoint().getX() + i;
//                    double y = noeud.getPoint().getY() + j;
//                    Point point = (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
//                    if (pointInPolygon(point, polygonCouloirByEtage(noeud.getEtage()))) {
//                        voisins.add(new Noeud2(point, noeud.getEtage(), 0, Math.sqrt(
//                                (noeud.getPoint().getX() - objectif.getPoint().getX()) * (noeud.getPoint().getX() - objectif.getPoint().getX()) +
//                                        (noeud.getPoint().getY() - objectif.getPoint().getY()) * (noeud.getPoint().getY() - objectif.getPoint().getY()))));
//                    }
//                }
//            }
//        }
//        return voisins;
//    }
//
//    private static boolean containsAvecCoupInf(PriorityQueue<Noeud2> openList, Noeud2 v) {
//        for (Noeud2 noeud : openList) {
//            if (noeud.equals(v)) {
//                if (noeud.getCout() < v.getCout()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private static boolean auVoisinage(int range, Point point, Point objectif) {
//        double distance = Math.sqrt((point.getX() - objectif.getX()) * (point.getX() - objectif.getX()) +
//                (point.getY() - objectif.getY()) * (point.getY() - objectif.getY()));
//        return distance <= range;
////        return u.getPoint().getX() == objectif.getPoint().getX() && u.getPoint().getY() == objectif.getPoint().getY();
//    }
//
//
//
//
//    public static Collection<Point> pointsSurLineString(LineString lineString, double distanceSeparation) throws ParseException {
//        double a = angleDirecteur(lineString);
//        double add_x = distanceSeparation * Math.cos(a);
//        double add_y = distanceSeparation * Math.sin(a);
//        ArrayList<Point> res = new ArrayList<>();
//        Point prec = lineString.getStartPoint();
//        for (int i = 0; i < distance(lineString.getStartPoint(), lineString.getEndPoint()) - distanceSeparation; i += distanceSeparation) {
//            double x = prec.getX() + add_x;
//            double y = prec.getY() + add_y;
//            Point p = (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
//            res.add(p);
//            prec = (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
//        }
//        return res;
//    }
//
//    public static double angleDirecteur(LineString lineString)
//    {
//        return Math.acos((lineString.getEndPoint().getX() - lineString.getStartPoint().getX() )/distance(lineString.getStartPoint(), lineString.getEndPoint()));
//    }
//
//    public static double distance(Point A, Point B) {
//        return Math.sqrt((A.getX() - B.getX()) * (A.getX() - B.getX()) + (A.getY() - B.getY()) * (A.getY() - B.getY()));
//    }
//
//    private static PriorityQueue<Noeud2> pointToNoeud(Collection<Point> points, Noeud2 noeud, Noeud2 objectif) {
//        PriorityQueue<Noeud2> noeuds = new PriorityQueue<>();
//        for (Point point : points) {
//            noeuds.add(new Noeud2(point, noeud.getEtage(), 0, Math.sqrt(
//                    (noeud.getPoint().getX() - objectif.getPoint().getX()) * (noeud.getPoint().getX() - objectif.getPoint().getX()) +
//                            (noeud.getPoint().getY() - objectif.getPoint().getY()) * (noeud.getPoint().getY() - objectif.getPoint().getY()))));
//        }
//        return noeuds;
//    }
//
//    private static LineString lineStringFrom2Points(Point p1, Point p2) throws ParseException {
//        return (LineString) SuperService.wktToGeometry("LINESTRING (" + p1.getX() + " " + p2.getY() + ", " +
//                p2.getX() + " " + p2.getY() + ")");
//    }
//
//
////    private Collection<Point> pathTrajet(Noeud2 depart, Noeud2 objectif) throws ParseException {
////        LinkedList<Noeud2> closedList = new LinkedList<>();
////        PriorityQueue<Noeud2> openList = new PriorityQueue<>();
////        openList.add(depart);
////        boolean murRencontre = false;
////        while (!openList.isEmpty()) {
////            Noeud2 u = openList.poll();
//////            if (u.getPoint().getX() == objectif.getPoint().getX() && u.getPoint().getY() == objectif.getPoint().getY()) {
////            if (auVoisinage(1, u.getPoint(), objectif.getPoint())) {
////                return closedList.stream().map(Noeud2::getPoint).collect(Collectors.toList());
////            }
////            if (!murRencontre) {
////                LineString lineString = lineStringFrom2Points(depart.getPoint(), objectif.getPoint());
////                for (Noeud2 v : pointToNoeud(pointsSurLineString(lineString, 1), u, objectif)) {
////                    if (pointInPolygon(v.getPoint(), polygonCouloirByEtage(v.getEtage()))) {
////                        if (!(closedList.contains(v) || containsAvecCoupInf(openList, v))) {
////                            v.setCout(u.getCout() + 1);
////                            v.setHeuristique((v.getCout() +
////                                    Math.sqrt(
////                                            (v.getPoint().getX() - objectif.getPoint().getX()) * (v.getPoint().getX() - objectif.getPoint().getX()) +
////                                                    (v.getPoint().getY() - objectif.getPoint().getY()) * (v.getPoint().getY() - objectif.getPoint().getY()))));
////                            openList.add(v);
////                        }
////                    } else {
////                        murRencontre = true;
////                        break;
////                    }
////                }
////
////            } else {
////                for (Noeud2 v : voisinsNoeud(u, objectif)) {
////                    if (!(closedList.contains(v) || containsAvecCoupInf(openList, v))) {
////                        v.setCout(u.getCout() + 1);
////                        v.setHeuristique((v.getCout() +
////                                Math.sqrt(
////                                        (v.getPoint().getX() - objectif.getPoint().getX()) * (v.getPoint().getX() - objectif.getPoint().getX()) +
////                                                (v.getPoint().getY() - objectif.getPoint().getY()) * (v.getPoint().getY() - objectif.getPoint().getY()))));
////                        openList.add(v);
////                    }
////                }
////            }
////            closedList.add(u);
////        }
////        return null;
////    }
//
//    private static Collection<Point> pathTrajet(Noeud2 depart, Noeud2 objectif) throws ParseException {
//        LinkedList<Noeud2> closedList = new LinkedList<>();
//        PriorityQueue<Noeud2> openList = new PriorityQueue<>();
//        openList.add(depart);
//        boolean murRencontre = false;
//        while (!openList.isEmpty()) {
//            Noeud2 u = openList.poll();
////            if (u.getPoint().getX() == objectif.getPoint().getX() && u.getPoint().getY() == objectif.getPoint().getY()) {
//            if (auVoisinage(1, u.getPoint(), objectif.getPoint())) {
//                return closedList.stream().map(Noeud2::getPoint).collect(Collectors.toList());
//            }
//            if (!murRencontre) {
//                LineString lineString = lineStringFrom2Points(depart.getPoint(), objectif.getPoint());
//                for (Noeud2 v : pointToNoeud(pointsSurLineString(lineString, 1), u, objectif)) {
//                    if (pointInPolygon(v.getPoint(), polygonCouloirByEtage(v.getEtage()))) {
//                        if (!(closedList.contains(v) || containsAvecCoupInf(openList, v))) {
//                            v.setCout(u.getCout() + 1);
//                            v.setHeuristique((v.getCout() +
//                                    Math.sqrt(
//                                            (v.getPoint().getX() - objectif.getPoint().getX()) * (v.getPoint().getX() - objectif.getPoint().getX()) +
//                                                    (v.getPoint().getY() - objectif.getPoint().getY()) * (v.getPoint().getY() - objectif.getPoint().getY()))));
//                            openList.add(v);
//                        }
//                    } else {
//                        murRencontre = true;
//                        break;
//                    }
//                }
//            } else {
//                for (Noeud2 v : voisinsNoeud(u, objectif)) {
//                    if (!(closedList.contains(v) || containsAvecCoupInf(openList, v))) {
//                        v.setCout(u.getCout() + 1);
//                        v.setHeuristique((v.getCout() +
//                                Math.sqrt(
//                                        (v.getPoint().getX() - objectif.getPoint().getX()) * (v.getPoint().getX() - objectif.getPoint().getX()) +
//                                                (v.getPoint().getY() - objectif.getPoint().getY()) * (v.getPoint().getY() - objectif.getPoint().getY()))));
//                        openList.add(v);
//                    }
//                }
//            }
//            closedList.add(u);
//        }
//        return null;
//    }
//
////    public Collection<LineString> pathFinding(Porte porteDepart, Salle salleArrivee) throws ParseException {
////        HashMap<LineString, Etage> trajets = (HashMap<LineString, Etage>) findTrajet(porteDepart, salleArrivee);
////        ArrayList<LineString> paths = new ArrayList<>();
////        Set<LineString> setLineStrings = trajets.keySet();
////        LineString[] lineStrings = setLineStrings.toArray(new LineString[0]);
////        Etage[] etages = trajets.values().toArray(new Etage[0]);
////        for (int i = 0; i < trajets.keySet().size() - 1; i += 2) {
////            Noeud2 depart = new Noeud2(milieuLineString(lineStrings[i]), etages[i], 0, 0);
////            Noeud2 objectif = new Noeud2(milieuLineString(lineStrings[i + 1]), etages[i + 1], 0, 0);
////            ArrayList<Point> trajetPoint = (ArrayList<Point>) pathTrajet(depart, objectif);
////            if (!trajetPoint.contains(objectif.getPoint())) {
////                trajetPoint.add(objectif.getPoint());
////            }
////            StringBuilder trajetString = new StringBuilder("LINESTRING (");
////            for (int j = 0; j < trajetPoint.size(); j++) {
////                trajetString.append(trajetPoint.get(j).getX()).append(" ").append(trajetPoint.get(j).getY());
////                if (j + 1 < trajetPoint.size()) {
////                    trajetString.append(", ");
////                }
////            }
////            trajetString.append(")");
////            paths.add((LineString) SuperService.wktToGeometry(trajetString.toString()));
////        }
////        return paths;
////    }
//
//    public static void main(String[] args) {
//        try {
//            Point point = (Point) SuperService.wktToGeometry("POINT (3 3)");
//            Point point2 = (Point) SuperService.wktToGeometry("POINT (6 6)");
//            Polygon polygon = (Polygon) SuperService.wktToGeometry("POLYGON ((0 0, 0 10, 10 10, 10 0, 0 0))");
//            Noeud2 depart = new Noeud2();
//            Noeud2 objectif = new Noeud2();
//            for (Point point1 : pathTrajet(depart, objectif)) {
//
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
