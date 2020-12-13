package fr.univ.orleans.sig.server_api_rest.services.A.modele;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.services.A.GraphNode;
import org.locationtech.jts.geom.Point;

import java.util.Objects;

public class Noeud implements GraphNode {

    private final String id;
    private final Point point;
    private final Etage etage;

    public Noeud(Point point, Etage etage) {
        this.id = point.toString();
        this.point = point;
        this.etage = etage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        // null check
        if (obj == null)
            return false;
        // type check and cast
        if (getClass() != obj.getClass())
            return false;
        Noeud noeud = (Noeud) obj;
        // field comparison
        return Objects.equals(id, noeud.id)
                && Objects.equals(noeud.id, id);
//        if (getClass().equals(obj.getClass())) {
//            Noeud noeud = (Noeud) obj;
//            return id.equals(noeud.id) && noeud.id.equals(id);
//        }
//        return false;
    }

    public String getId() {
        return id;
    }

    public Point getPoint() {
        return point;
    }

    public Etage getEtage() {
        return etage;
    }

}
