package fr.univ.orleans.sig.server_api_rest.services.A;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graphe<T extends NoeudGraphe> {

    private final Set<T> noeuds;
    private final Map<String, Set<String>> connections;

    public Graphe(Set<T> noeuds, Map<String, Set<String>> connections) {
        this.noeuds = noeuds;
        this.connections = connections;
    }

    public T getNoeud(String id) {
        return noeuds.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Noeud introuvable avec cet ID"));
    }

    public Set<T> getConnections(T noeud) {
        return connections.get(noeud.getId()).stream()
                .map(this::getNoeud)
                .collect(Collectors.toSet());
    }

}
