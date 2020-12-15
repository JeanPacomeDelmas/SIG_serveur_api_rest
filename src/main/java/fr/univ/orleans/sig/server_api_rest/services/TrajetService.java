package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.services.A.Graph;
import fr.univ.orleans.sig.server_api_rest.services.A.RouteFinder;
import fr.univ.orleans.sig.server_api_rest.services.A.modele.Noeud;
import fr.univ.orleans.sig.server_api_rest.services.A.modele.NoeudScorer;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrajetService {

    @Autowired
    private FonctionSalleService fonctionSalleService;
    @Autowired
    private EscalierService escalierService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private PorteService porteService;

    private ArrayList<Pair<LineString, Etage>> findEtageTrajet(Porte porteDepart, Salle salleArrivee) {
        ArrayList<Pair<LineString, Etage>> trajets = new ArrayList<>();
        trajets.add(Pair.of(porteDepart.getGeom(), porteDepart.getSalle1().getEtage()));
        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
            for (LineString lineString : escalierService.lineStringEscalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage())) {
                trajets.add(Pair.of(lineString, porteDepart.getSalle1().getEtage()));
            }
        }
        Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
        for (Salle salle : salles) {
            if (salle.getGid() == salleArrivee.getGid()) {
                ///////////////////////////////////
                ///////////////////////////////////
                ///////////////////////////////////
                trajets.add(Pair.of(porteService.findPorteBySalle(salle,
                        salleService.findSalleByEtageAndFonctionCouloir(salle.getEtage(),
                                fonctionSalleService.findByNom("couloir"))).getGeom(), salle.getEtage()));
                break;
            }
        }
        return trajets;
    }

    private Point milieuLineString(LineString lineString) throws ParseException {
        Point A = lineString.getStartPoint();
        Point B = lineString.getEndPoint();
        double x = (A.getX() + B.getX()) / 2;
        double y = (A.getY() + B.getY()) / 2;
        return (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
    }

    private Polygon polygonCouloirByEtage(Etage etage) {
        return couloirByEtage(etage).getGeom();
    }

//    private boolean lineStringContainsPoint(Point point, LineString lineString) {
//        return lineString.contains(point);
//    }

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

    private boolean auVoisinagePoint(double range, Point point, Point objectif) {
        double distance = Math.sqrt((point.getX() - objectif.getX()) * (point.getX() - objectif.getX()) +
                (point.getY() - objectif.getY()) * (point.getY() - objectif.getY()));
        return distance <= range;
    }

//    public static Collection<Point> pointsLineString(LineString lineString, double distanceSeparation) throws ParseException {
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
//    public static double angleDirecteur(LineString lineString) {
//        return Math.acos((lineString.getEndPoint().getX() - lineString.getStartPoint().getX() )/distance(lineString.getStartPoint(), lineString.getEndPoint()));
//    }
//
//    public static double distance(Point A, Point B) {
//        return Math.sqrt((A.getX() - B.getX()) * (A.getX() - B.getX()) + (A.getY() - B.getY()) * (A.getY() - B.getY()));
//    }

//    private PriorityQueue<Noeud> pointToNoeud(Collection<Point> points, Noeud noeud) {
//        PriorityQueue<Noeud> noeuds = new PriorityQueue<>();
//        for (Point point : points) {
//            noeuds.add(new Noeud(point, noeud.getEtage()));
//        }
//        return noeuds;
//    }

//    private LineString lineStringFrom2Points(Point p1, Point p2) throws ParseException {
//        return (LineString) SuperService.wktToGeometry("LINESTRING (" + p1.getX() + " " + p2.getY() + ", " +
//                p2.getX() + " " + p2.getY() + ")");
//    }

    private boolean pointInPolygon(Point point, Polygon polygon) {
        return polygon.contains(point);
    }

    private boolean pointInPolygonSansEscalier(Point point, Polygon polygon, Polygon escalier) {
        return polygon.contains(point) && !escalier.contains(point);
    }

//    private boolean pointInPolygonWithBorders(Point point, Polygon polygon) throws ParseException {
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

    private Salle couloirByEtage(Etage etage) {
        return salleService.findSalleByEtageAndFonctionCouloir(etage, fonctionSalleService.findByNom("couloir"));
    }

    private LinkedList<Noeud> voisinsNoeud(Noeud noeud, Polygon polygon, Polygon escalier, double range) throws ParseException {
        LinkedList<Noeud> voisins = new LinkedList<>();
        for (int i = - 1; i < 2; i++) {
            for (int j = - 1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    double x = noeud.getPoint().getX() + i * range;
                    double y = noeud.getPoint().getY() + j * range;
                    Point point = (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
                    if (pointInPolygonSansEscalier(point, polygon, escalier)) {
                        voisins.add(new Noeud(point, noeud.getEtage()));
                    }
                }
            }
        }
        return voisins;
    }

    private Point createPoint(double x, double y) throws ParseException {
        return (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
    }

    private Collection<LineString> borduresPolygon(Polygon polygon) throws ParseException {
        ArrayList<LineString> lineStrings = new ArrayList<>();
        for (int i = 0; i < polygon.getCoordinates().length - 1; i++) {
            lineStrings.add((LineString) SuperService.wktToGeometry(
                    "LINESTRING ("+
                            polygon.getCoordinates()[i].getX() + " " + polygon.getCoordinates()[i].getY() + ", " +
                            polygon.getCoordinates()[i + 1].getX() + " " + polygon.getCoordinates()[i + 1].getY() + ")"));
        }
        return lineStrings;
    }

    public double coeffDirecteur(LineString lineString) {
        return (lineString.getEndPoint().getY() - lineString.getStartPoint().getY()) / (lineString.getEndPoint().getX() - lineString.getStartPoint().getX());
    }

//    private boolean segmentsSecant(LineString lineString1, LineString lineString2) {
//        Point temoin =
//    }

    private Graph<Noeud> initializeGraph(Noeud from, Noeud to, Escalier escalier, double range) throws ParseException {
        Set<Noeud> noeuds = new HashSet<>();
        Map<String, Set<String>> connections = new HashMap<>();

        Etage etageCourant = from.getEtage();

        ArrayList<Point> pointsPortes =
                (ArrayList<Point>) porteService.findAllPorteByEtage(
                        etageCourant).stream().map(value -> {
                    try {
                        return milieuLineString(value.getGeom());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).collect(Collectors.toList());

        Polygon couloir = polygonCouloirByEtage(etageCourant);
        Coordinate[] coordinatesCouloir = couloir.getCoordinates();
        Point H = createPoint(coordinatesCouloir[0].getX(), coordinatesCouloir[0].getY());
        Point B = createPoint(coordinatesCouloir[0].getX(), coordinatesCouloir[0].getY());
        Point G = createPoint(coordinatesCouloir[0].getX(), coordinatesCouloir[0].getY());
        Point D = createPoint(coordinatesCouloir[0].getX(), coordinatesCouloir[0].getY());
        for (Coordinate coordinate : coordinatesCouloir) {
            if (coordinate.getX() < G.getX()) {
                G = createPoint(coordinate.getX(), G.getY());
            }
            if (coordinate.getX() > D.getX()) {
                D = createPoint(coordinate.getX(), D.getY());
            }
            if (coordinate.getY() < B.getY()) {
                B = createPoint(B.getX(), coordinate.getY());
            }
            if (coordinate.getY() > H.getY()) {
                H = createPoint(H.getX(), coordinate.getY());
            }
        }

        for (double x = G.getX(); x <= D.getX(); x += range) {
            for (double y = B.getY(); y <= H.getY(); y += range) {
                Point point = createPoint(x, y);
                if (pointInPolygonSansEscalier(point, couloir, escalier.getGeom())) {/////////////////////////////////////////////////////
                    Noeud noeud = new Noeud(point, etageCourant);
                    noeuds.add(noeud);

                    connections.put(noeud.getId(), new HashSet<>(
                            voisinsNoeud(noeud, couloir, escalier.getGeom(), range).stream().map(Noeud::getId).collect(Collectors.toList())));
                }
            }
        }

        for (Point point : pointsPortes) {
            Noeud porte = new Noeud(point, etageCourant);
            noeuds.add(porte);
            ArrayList<Noeud> voisins = new ArrayList<>();
            for (Noeud voisinPotentiel : noeuds) {
                if (auVoisinagePoint(2 * range, point, voisinPotentiel.getPoint())) {
                    voisins.add(voisinPotentiel);
                    for (String idNoeud : connections.keySet()) {
                        String idVoisinPotentiel = voisinPotentiel.getId();
                        if (idNoeud.equals(idVoisinPotentiel)) {
                            connections.get(idVoisinPotentiel).add(porte.getId());
                        }
                    }
                }
            }
            connections.put(porte.getId(), new HashSet<>(voisins.stream().map(Noeud::getId).collect(Collectors.toList())));
        }

        boolean fromEstUnePorte = false;
        for (Noeud noeud : noeuds) {
            if (noeud.getId().equals(from.getId())) {
                fromEstUnePorte = true;
                break;
            }
        }
        if (!fromEstUnePorte) {
            noeuds.add(from);
            ArrayList<Noeud> voisins = new ArrayList<>();
            for (Noeud voisinPotentiel : noeuds) {
                if (auVoisinagePoint(2 * range, from.getPoint(), voisinPotentiel.getPoint())) {
                    voisins.add(voisinPotentiel);
                    for (String idNoeud : connections.keySet()) {
                        String idVoisinPotentiel = voisinPotentiel.getId();
                        if (idNoeud.equals(idVoisinPotentiel)) {
                            connections.get(idVoisinPotentiel).add(from.getId());
                        }
                    }
                }
            }
            connections.put(from.getId(), new HashSet<>(voisins.stream().map(Noeud::getId).collect(Collectors.toList())));
        }

        noeuds.add(to);
        ArrayList<Noeud> voisins = new ArrayList<>();
        for (Noeud voisinPotentiel : noeuds) {
            if (auVoisinagePoint(2 * range, to.getPoint(), voisinPotentiel.getPoint())) {
                voisins.add(voisinPotentiel);
                for (String idNoeud : connections.keySet()) {
                    String idVoisinPotentiel = voisinPotentiel.getId();
                    if (idNoeud.equals(idVoisinPotentiel)) {
                        connections.get(idVoisinPotentiel).add(to.getId());
                    }
                }
            }
        }
        connections.put(to.getId(), new HashSet<>(voisins.stream().map(Noeud::getId).collect(Collectors.toList())));

        return new Graph<>(noeuds, connections);
    }

    public Collection<LineString> pathFinding(Porte porteDepart, Salle salleArrivee) throws ParseException {
        ArrayList<Pair<LineString, Etage>> trajets = findEtageTrajet(porteDepart, salleArrivee);
        ArrayList<Escalier> escaliers = (ArrayList<Escalier>) escalierService.escalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage());
        ArrayList<LineString> paths = new ArrayList<>();
        LineString[] lineStrings = trajets.stream().map(Pair::getFirst).toArray(LineString[]::new);
        Etage[] etages = trajets.stream().map(Pair::getSecond).toArray(Etage[]::new);

        for (int i = 0; i < trajets.size() - 1; i += 2) {
            Noeud depart = new Noeud(milieuLineString(lineStrings[i]), etages[i]);
            Noeud objectif = new Noeud(milieuLineString(lineStrings[i + 1]), etages[i + 1]);

            Graph<Noeud> graph = initializeGraph(depart, objectif, escaliers.get(i), 1d);
            RouteFinder<Noeud> routeFinder = new RouteFinder<>(graph, new NoeudScorer(), new NoeudScorer());
            ArrayList<Noeud> noeuds = (ArrayList<Noeud>) routeFinder.findRoute(depart, objectif);
            ArrayList<Point> points = (ArrayList<Point>) noeuds.stream().map(Noeud::getPoint).collect(Collectors.toList());

            StringBuilder trajetString = new StringBuilder("LINESTRING (");
            for (int j = 0; j < points.size(); j++) {
                trajetString.append(points.get(j).getX()).append(" ").append(points.get(j).getY());
                if (j + 1 < points.size()) {
                    trajetString.append(", ");
                }
            }
            trajetString.append(")");
            paths.add((LineString) SuperService.wktToGeometry(trajetString.toString()));
        }
        return paths;
    }

}
