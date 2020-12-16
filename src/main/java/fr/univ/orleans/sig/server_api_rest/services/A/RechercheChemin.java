package fr.univ.orleans.sig.server_api_rest.services.A;

import java.util.*;

public class RechercheChemin<T extends NoeudGraphe> {

    private final Graphe<T> graphe;
    private final Heuristique<T> nextNodeHeuristique;
    private final Heuristique<T> targetHeuristique;

    public RechercheChemin(Graphe<T> graphe, Heuristique<T> nextNodeHeuristique, Heuristique<T> targetHeuristique) {
        this.graphe = graphe;
        this.nextNodeHeuristique = nextNodeHeuristique;
        this.targetHeuristique = targetHeuristique;
    }

    public List<T> findRoute(T from, T to) {
        Queue<NoeudChemin<T>> openSet = new PriorityQueue<>();
        Map<T, NoeudChemin<T>> allNodes = new HashMap<>();

        NoeudChemin<T> start = new NoeudChemin<>(from, null, 0d, targetHeuristique.calculerCoup(from, to));
        openSet.add(start);
        allNodes.put(from, start);

        NoeudChemin<T> next = null;
        while (!openSet.isEmpty()) {
            next = openSet.poll();
            if (next.getCourant().getId().equals(to.getId())) {
                List<T> route = new ArrayList<>();
                NoeudChemin<T> current = next;
                do {
                    route.add(0, current.getCourant());
                    current = allNodes.get(current.getPrecedent());
                } while (current != null);
                return route;
            }

            NoeudChemin<T> finalNext = next;
            graphe.getConnections(next.getCourant()).forEach(connection -> {
                NoeudChemin<T> nextNode = allNodes.getOrDefault(connection, new NoeudChemin<>(connection));
                allNodes.put(connection, nextNode);

                double newScore = finalNext.getScoreRoute() + nextNodeHeuristique.calculerCoup(finalNext.getCourant(), connection);
                if (newScore < nextNode.getScoreRoute()) {
                    nextNode.setPrecedent(finalNext.getCourant());
                    nextNode.setScoreRoute(newScore);
                    nextNode.setScoreEstime(newScore + targetHeuristique.calculerCoup(connection, to));
                    openSet.add(nextNode);
                }
            });
        }
        throw new IllegalStateException("No route found");
    }

}
