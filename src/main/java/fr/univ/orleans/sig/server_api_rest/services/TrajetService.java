package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.services.modele.Noeud;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.PriorityQueue;
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

//    private Collection<Trajet> findTrajet(Porte porteDepart, Salle salleArrivee) {
//        ArrayList<Trajet> trajets = new ArrayList<>();
//        trajets.add(porteDepart);
//        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
//            trajets.addAll(escalierService.escalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage()));
//        }
//        Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
//        for (Salle salle : salles) {
//            if (salle.getGid() == salleArrivee.getGid()) {
//                trajets.add(porteService.findPorteBySalle(salle,
//                        salleService.findSalleByEtageAndFonctionCouloir(salle.getEtage(),
//                                fonctionSalleService.findByNom("couloir"))));
//            }
//        }
//        return trajets;
//    }

    private Collection<LineString> findTrajet(Porte porteDepart, Salle salleArrivee) {
        ArrayList<LineString> trajets = new ArrayList<>();
        trajets.add(porteDepart.getGeom());
        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
            trajets.addAll(escalierService.escalierToEscalierByLineString(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage()));
        }
        Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
        for (Salle salle : salles) {
            if (salle.getGid() == salleArrivee.getGid()) {
                trajets.add(porteService.findPorteBySalle(salle,
                        salleService.findSalleByEtageAndFonctionCouloir(salle.getEtage(),
                                fonctionSalleService.findByNom("couloir"))).getGeom());
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

    private Collection<Noeud> voisinsNoeud(Noeud noeud) throws ParseException {
        ArrayList<Noeud> voisins = new ArrayList<>();
        for (int i = - 1; i < 2; i++) {
            for (int j = - 1; j < 2; j++) {
                if (i != 0 || j != 0) {
                    double x = noeud.getPoint().getX() + i * 0.5;
                    double y = noeud.getPoint().getY() + j * 0.5;
                    voisins.add(new Noeud((Point) SuperService.wktToGeometry("POINT (" + x + " " + y + ")"),
                            0, 0));
                }
            }
        }
        return voisins;
    }

    private boolean containsAvecCoupInf(PriorityQueue<Noeud> openList, Noeud v) {
        for (Noeud noeud : openList) {
            if (noeud.equals(v)) {
                if (noeud.getCout() < v.getCout()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Collection<Point> pathTrajet(Noeud depart, Noeud objectif) throws ParseException {
        LinkedList<Noeud> closedList = new LinkedList<>();
        PriorityQueue<Noeud> openList = new PriorityQueue<>();
        openList.add(depart);
        while (!openList.isEmpty()) {
            Noeud u = openList.poll();
            if (u.getPoint().getX() == objectif.getPoint().getX() && u.getPoint().getY() == objectif.getPoint().getY()) {
                return closedList.stream().map(Noeud::getPoint).collect(Collectors.toList());
            }
            for (Noeud v : voisinsNoeud(u)) {
                if (!(closedList.contains(v) || containsAvecCoupInf(openList, v))) {
                    v.setCout(u.getCout() + 1);
                    v.setHeuristique((int) (v.getCout() +
                            Math.sqrt(
                                    (v.getPoint().getX() - objectif.getPoint().getX()) * (v.getPoint().getX() - objectif.getPoint().getX()) +
                                            (v.getPoint().getY() - objectif.getPoint().getY()) * (v.getPoint().getY() - objectif.getPoint().getY()))));
                    openList.add(v);
                }
            }
            closedList.add(u);
        }
        return null;
    }

    public Collection<LineString> pathFinding(Porte porteDepart, Salle salleArrivee) throws ParseException {
        ArrayList<LineString> trajets = (ArrayList<LineString>) findTrajet(porteDepart, salleArrivee);
        ArrayList<LineString> paths = new ArrayList<>();
        for (int i = 0; i < trajets.size() - 1; i += 2) {
            Noeud depart = new Noeud(milieuLineString(trajets.get(i)), 0, 0);
            Noeud objectif = new Noeud(milieuLineString(trajets.get(i + 1)), 0, 0);
            ArrayList<Point> trajetPoint = (ArrayList<Point>) pathTrajet(depart, objectif);
            StringBuilder trajetString = new StringBuilder("LINESTRING (");
            for (int j = 0; j < trajetPoint.size(); j++) {
                trajetString.append(trajetPoint.get(j).getX()).append(" ").append(trajetPoint.get(j).getY());
                if (j + i < trajetPoint.size()) {
                    trajetString.append(", ");
                }
            }
            trajetString.append(")");
            paths.add((LineString) SuperService.wktToGeometry(trajetString.toString()));
        }
        return paths;
    }

}
