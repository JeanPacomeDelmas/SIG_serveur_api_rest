package fr.univ.orleans.sig.server_api_rest.services.A.modele;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.services.A.NoeudGraphe;
import org.locationtech.jts.geom.Point;

import java.util.Objects;

public class Noeud implements NoeudGraphe {

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
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Noeud noeud = (Noeud) obj;
        return Objects.equals(id, noeud.id)
                && Objects.equals(noeud.id, id);
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
