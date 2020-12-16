package fr.univ.orleans.sig.server_api_rest.services.modele;

import org.locationtech.jts.geom.Point;

public class Segment {

    private Point debut;
    private Point fin;

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
