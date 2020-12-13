package fr.univ.orleans.sig.server_api_rest.services.modele;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import org.locationtech.jts.geom.Point;

public class Noeud2 implements Comparable<Noeud2> {

    private final Point point;
    private final Etage etage;
    private int cout;
    private double heuristique;

    public Noeud2(Point point, Etage etage, int cout, double heuristique) {
        this.point = point;
        this.etage = etage;
        this.cout = cout;
        this.heuristique = heuristique;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            Noeud2 noeud2 = (Noeud2) obj;
            return point.getX() == noeud2.getPoint().getX() &&
                    point.getY() == noeud2.getPoint().getY() &&
                    etage.getGid() == noeud2.getEtage().getGid();
        }
        return false;
    }

    @Override
    public int compareTo(Noeud2 o) {
        if (heuristique < o.heuristique) {
            return -1;
        } else if (heuristique == o.heuristique) {
            return 0;
        } else {
            return 1;
        }
    }

    public Point getPoint() {
        return point;
    }

    public Etage getEtage() {
        return etage;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public double getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(double heuristique) {
        this.heuristique = heuristique;
    }

}
