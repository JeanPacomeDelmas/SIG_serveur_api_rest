package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.services.A.Graphe;
import fr.univ.orleans.sig.server_api_rest.services.A.RechercheChemin;
import fr.univ.orleans.sig.server_api_rest.services.A.modele.Noeud;
import fr.univ.orleans.sig.server_api_rest.services.A.modele.HeuristiqueNoeud;
import fr.univ.orleans.sig.server_api_rest.services.modele.Segment;
import fr.univ.orleans.sig.server_api_rest.services.modele.Vecteur;
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

    private Point createPoint(double x, double y) throws ParseException {
        return (Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")");
    }

    private LineString createLineString(ArrayList<Point> points) throws ParseException {
        StringBuilder lineString = new StringBuilder("LINESTRING (");
        for (int j = 0; j < points.size(); j++) {
            lineString.append(points.get(j).getX()).append(" ").append(points.get(j).getY());
            if (j + 1 < points.size()) {
                lineString.append(", ");
            }
        }
        lineString.append(")");
        return (LineString) SuperService.wktToGeometry(lineString.toString());
    }


    private ArrayList<Pair<LineString, Etage>> findEtageTrajet(Porte porteDepart, Salle salleArrivee) {
        ArrayList<Pair<LineString, Etage>> trajets = new ArrayList<>();
        trajets.add(Pair.of(porteDepart.getGeom(), porteDepart.getSalle1().getEtage()));
        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
            for (LineString lineString : escalierService.lineStringEscalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage())) {
                trajets.add(Pair.of(lineString, porteDepart.getSalle1().getEtage()));
            }
        }
        trajets.addAll(findEtape(salleArrivee));
        return trajets;
    }

    private ArrayList<Pair<LineString, Etage>> findEtape(Salle salleArrivee) {
        ArrayList<Pair<LineString, Etage>> trajets = new ArrayList<>();
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

    private ArrayList<Pair<LineString, Etage>> findEtageTrajet(Point point, Etage etage, Salle salleArrivee) throws ParseException {
        ArrayList<Pair<LineString, Etage>> trajets = new ArrayList<>();
        ArrayList<Point> points = new ArrayList<>();
        points.add(point);
        points.add(point);
        LineString lineStringDepart = createLineString(points);
        trajets.add(Pair.of(lineStringDepart, etage));
        if (etage.getGid() != salleArrivee.getEtage().getGid()) {
            for (LineString lineString : escalierService.lineStringEscalierToEscalier(etage, salleArrivee.getEtage())) {
                trajets.add(Pair.of(lineString, etage));
            }
        }
        trajets.addAll(findEtape(salleArrivee));
        return trajets;
    }


    private Point milieuLineString(LineString lineString) throws ParseException {
        Point A = lineString.getStartPoint();
        Point B = lineString.getEndPoint();
        double x = (A.getX() + B.getX()) / 2;
        double y = (A.getY() + B.getY()) / 2;
        return createPoint(x, y);
    }

    private boolean pointInLineString(Point point, LineString lineString) {
        return lineString.contains(point);
    }

    private boolean pointInPolygon(Point point, Polygon polygon) {
        return polygon.contains(point);
    }

    private boolean pointInBordurePolygon(Point point, Polygon polygon) throws ParseException {
        ArrayList<LineString> bordures = (ArrayList<LineString>) borduresPolygon(polygon);
        for (LineString bordure : bordures) {
            if (pointInLineString(point, bordure)) {
                return true;
            }
        }
        return false;
    }

//    private boolean pointInPolygonSansEscalier(Point point, Polygon polygon, Polygon escalier) {
//        return pointInPolygon(point, polygon) && !escalier.contains(point);
//    }


    private Salle couloirByEtage(Etage etage) {
        return salleService.findSalleByEtageAndFonctionCouloir(etage, fonctionSalleService.findByNom("couloir"));
    }

    private Polygon polygonCouloirByEtage(Etage etage) {
        return couloirByEtage(etage).getGeom();
    }


    private Collection<LineString> borduresPolygon(Polygon polygon) throws ParseException {
        ArrayList<LineString> lineStrings = new ArrayList<>();
        for (int i = 0; i < polygon.getCoordinates().length - 1; i++) {
            ArrayList<Point> points = new ArrayList<>();
            points.add(createPoint(polygon.getCoordinates()[i].getX(), polygon.getCoordinates()[i].getY()));
            points.add(createPoint(polygon.getCoordinates()[i + 1].getX(), polygon.getCoordinates()[i + 1].getY()));
            lineStrings.add(createLineString(points));
        }
        return lineStrings;
    }

    private Point segmentsSecants(Segment segment1, Segment segment2) throws ParseException {
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
                return createPoint(x, y);
            }
        }
        return null;
    }

    private boolean borduresPolygonNonSecantes(LineString lineString, Polygon polygon) throws ParseException {
        ArrayList<LineString> bordures = (ArrayList<LineString>) borduresPolygon(polygon);
        for (LineString bordure : bordures) {
            if (segmentsSecants(new Segment(lineString), new Segment(bordure)) != null) {
                return false;
            }
        }
        return true;
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

    private boolean auVoisinagePoint(double range, Point point, Point objectif) {
        double distance = Math.sqrt((point.getX() - objectif.getX()) * (point.getX() - objectif.getX()) +
                (point.getY() - objectif.getY()) * (point.getY() - objectif.getY()));
        return distance <= 2 * range;
    }

    private LinkedList<Noeud> voisinsNoeud(Noeud noeud, Polygon polygon, Polygon escalier, double range) throws ParseException {
        LinkedList<Noeud> voisins = new LinkedList<>();
        for (int i = - 1; i < 2; i++) {
            for (int j = - 1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    double x = noeud.getPoint().getX() + i * range;
                    double y = noeud.getPoint().getY() + j * range;
                    Point point = createPoint(x, y);
                    ArrayList<Point> points = new ArrayList<>();
                    points.add(noeud.getPoint());
                    points.add(point);
                    if (pointInPolygon(point, polygon) &&
                            !pointInPolygon(point, escalier) &&
                            !pointInBordurePolygon(point, escalier) &&
                            borduresPolygonNonSecantes(createLineString(points), polygon) &&
                            borduresPolygonNonSecantes(createLineString(points), escalier)) {
                        voisins.add(new Noeud(point, noeud.getEtage()));
                    }
                }
            }
        }
        return voisins;
    }

    private Collection<Noeud> connectionsNoeud(Noeud noeud, Set<Noeud> noeuds, Map<String, Set<String>> connections, double range) {
        ArrayList<Noeud> voisins = new ArrayList<>();
        for (Noeud voisinPotentiel : noeuds) {
            if (auVoisinagePoint(range, noeud.getPoint(), voisinPotentiel.getPoint())) {
                voisins.add(voisinPotentiel);
                for (String idNoeud : connections.keySet()) {
                    String idVoisinPotentiel = voisinPotentiel.getId();
                    if (idNoeud.equals(idVoisinPotentiel)) {
                        connections.get(idVoisinPotentiel).add(noeud.getId());
                    }
                }
            }
        }
        return voisins;
    }


    private Graphe<Noeud> initializeGraph(Noeud from, Noeud to, Escalier escalier, double range) throws ParseException {
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
                if (pointInPolygon(point, couloir) &&
                        !pointInPolygon(point, escalier.getGeom()) &&
                        !pointInBordurePolygon(point, escalier.getGeom())) {/////////////////////////////////////////////////////
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
                if (auVoisinagePoint(range, point, voisinPotentiel.getPoint())) {
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
            connections.put(from.getId(), new HashSet<>(
                    connectionsNoeud(from, noeuds, connections,range).stream().map(Noeud::getId).collect(Collectors.toList())));
        }

        noeuds.add(to);
        connections.put(to.getId(), new HashSet<>(
                connectionsNoeud(to, noeuds, connections,range).stream().map(Noeud::getId).collect(Collectors.toList())));

        return new Graphe<>(noeuds, connections);
    }

    private Collection<LineString> pathFinding(ArrayList<Pair<LineString, Etage>> trajets, ArrayList<Escalier> escaliers) throws ParseException {
        ArrayList<LineString> paths = new ArrayList<>();
        LineString[] lineStrings = trajets.stream().map(Pair::getFirst).toArray(LineString[]::new);
        Etage[] etages = trajets.stream().map(Pair::getSecond).toArray(Etage[]::new);

        for (int i = 0; i < trajets.size() - 1; i += 2) {
            Noeud depart = new Noeud(milieuLineString(lineStrings[i]), etages[i]);
            Noeud objectif = new Noeud(milieuLineString(lineStrings[i + 1]), etages[i + 1]);

            Graphe<Noeud> graphe = initializeGraph(depart, objectif, escaliers.get(i), 1d);
            RechercheChemin<Noeud> rechercheChemin = new RechercheChemin<>(graphe, new HeuristiqueNoeud(), new HeuristiqueNoeud());
            ArrayList<Noeud> noeuds = (ArrayList<Noeud>) rechercheChemin.findRoute(depart, objectif);
            ArrayList<Point> points = (ArrayList<Point>) noeuds.stream().map(Noeud::getPoint).collect(Collectors.toList());

            paths.add(createLineString(points));
        }
        return paths;
    }

    public Collection<LineString> pathFindingPorte(Porte porteDepart, Salle salleArrivee) throws ParseException {
        ArrayList<Pair<LineString, Etage>> trajets = findEtageTrajet(porteDepart, salleArrivee);
        ArrayList<Escalier> escaliers = (ArrayList<Escalier>) escalierService.escalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage());
        return pathFinding(trajets, escaliers);
    }

    public Collection<LineString> pathFindingPosition(Point point, Etage etage, Salle salleArrivee) throws ParseException {
        ArrayList<Pair<LineString, Etage>> trajets = findEtageTrajet(point, etage, salleArrivee);
        ArrayList<Escalier> escaliers = (ArrayList<Escalier>) escalierService.escalierToEscalier(etage, salleArrivee.getEtage());
        return pathFinding(trajets, escaliers);
    }

}
