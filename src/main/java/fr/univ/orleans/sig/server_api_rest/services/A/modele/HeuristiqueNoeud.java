package fr.univ.orleans.sig.server_api_rest.services.A.modele;

import fr.univ.orleans.sig.server_api_rest.services.A.Heuristique;

public class HeuristiqueNoeud implements Heuristique<Noeud> {

    @Override
    public double calculerCout(Noeud from, Noeud to) {
        return Math.sqrt(
                (from.getPoint().getX() - to.getPoint().getX()) * (from.getPoint().getX() - to.getPoint().getX()) +
                        (from.getPoint().getY() - to.getPoint().getY()) * (from.getPoint().getY() - to.getPoint().getY()));
    }

}
