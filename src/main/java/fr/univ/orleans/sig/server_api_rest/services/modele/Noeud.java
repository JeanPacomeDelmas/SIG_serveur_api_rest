package fr.univ.orleans.sig.server_api_rest.services.modele;

import org.locationtech.jts.geom.Point;

public class Noeud implements Comparable<Noeud> {

    private final Point point;
    private int cout;
    private int heuristique;

    public Noeud(Point point, int cout, int heuristique) {
        this.point = point;
        this.cout = cout;
        this.heuristique = heuristique;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            Noeud noeud = (Noeud) obj;
            return point.getX() == noeud.getPoint().getX() && point.getY() == noeud.getPoint().getY();
        }
        return false;
    }

    @Override
    public int compareTo(Noeud o) {
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

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public int getHeuristique() {
        return heuristique;
    }

    public void setHeuristique(int heuristique) {
        this.heuristique = heuristique;
    }

}
