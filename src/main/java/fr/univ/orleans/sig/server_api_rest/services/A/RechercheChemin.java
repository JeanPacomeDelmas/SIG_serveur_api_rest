package fr.univ.orleans.sig.server_api_rest.services.A;

import java.util.*;

public class RechercheChemin<T extends NoeudGraphe> {

    private final Graphe<T> graphe;
    private final Heuristique<T> noeudHeuristiqueSuivant;
    private final Heuristique<T> noeudHeuristiqueCible;

    public RechercheChemin(Graphe<T> graphe, Heuristique<T> noeudHeuristiqueSuivant, Heuristique<T> noeudHeuristiqueCible) {
        this.graphe = graphe;
        this.noeudHeuristiqueSuivant = noeudHeuristiqueSuivant;
        this.noeudHeuristiqueCible = noeudHeuristiqueCible;
    }

    public List<T> plusCourtChemin(T from, T to) {
        Queue<NoeudChemin<T>> noeudAVisiter = new PriorityQueue<>();
        Map<T, NoeudChemin<T>> noeudDejaVisites = new HashMap<>();

        NoeudChemin<T> noeudDepart = new NoeudChemin<>(from, null, 0d, noeudHeuristiqueCible.calculerCout(from, to));
        noeudAVisiter.add(noeudDepart);
        noeudDejaVisites.put(from, noeudDepart);

        NoeudChemin<T> suivant = null;
        while (!noeudAVisiter.isEmpty()) {
            suivant = noeudAVisiter.poll();
            if (suivant.getCourant().getId().equals(to.getId())) {
                List<T> chemin = new ArrayList<>();
                NoeudChemin<T> courant = suivant;
                do {
                    chemin.add(0, courant.getCourant());
                    courant = noeudDejaVisites.get(courant.getPrecedent());
                } while (courant != null);
                return chemin;
            }

            NoeudChemin<T> vraiSuivant = suivant;
            graphe.getConnections(suivant.getCourant()).forEach(connection -> {
                NoeudChemin<T> noeudSuivant = noeudDejaVisites.getOrDefault(connection, new NoeudChemin<>(connection));
                noeudDejaVisites.put(connection, noeudSuivant);

                double nouveauCout = vraiSuivant.getScoreRoute() + noeudHeuristiqueSuivant.calculerCout(vraiSuivant.getCourant(), connection);
                if (nouveauCout < noeudSuivant.getScoreRoute()) {
                    noeudSuivant.setPrecedent(vraiSuivant.getCourant());
                    noeudSuivant.setScoreRoute(nouveauCout);
                    noeudSuivant.setScoreEstime(nouveauCout + noeudHeuristiqueCible.calculerCout(connection, to));
                    noeudAVisiter.add(noeudSuivant);
                }
            });
        }
        throw new IllegalStateException("Chemin introuvable");
    }

}
