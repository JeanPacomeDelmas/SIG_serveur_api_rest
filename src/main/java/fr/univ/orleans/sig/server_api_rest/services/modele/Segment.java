package fr.univ.orleans.sig.server_api_rest.services.modele;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

public class Segment {

    private final Point debut;
    private final Point fin;

    public Segment(LineString lineString) {
        debut = lineString.getStartPoint();
        fin = lineString.getEndPoint();
    }

    public Segment(Point debut, Point fin) {
        this.debut = debut;
        this.fin = fin;
    }

    public Vecteur toVecteur() {
        return new Vecteur(fin.getX() - debut.getX(), fin.getY() - debut.getY());
    }

    public Point getDebut() {
        return debut;
    }

    public Point getFin() {
        return fin;
    }

}
