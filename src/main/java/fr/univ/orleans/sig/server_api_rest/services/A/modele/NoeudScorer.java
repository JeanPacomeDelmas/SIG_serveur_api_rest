package fr.univ.orleans.sig.server_api_rest.services.A.modele;

import fr.univ.orleans.sig.server_api_rest.services.A.Scorer;

public class NoeudScorer implements Scorer<Noeud> {

    @Override
    public double computeCost(Noeud from, Noeud to) {
        return Math.sqrt(
                (from.getPoint().getX() - to.getPoint().getX()) * (from.getPoint().getX() - to.getPoint().getX()) +
                        (from.getPoint().getY() - to.getPoint().getY()) * (from.getPoint().getY() - to.getPoint().getY()));
    }

}
