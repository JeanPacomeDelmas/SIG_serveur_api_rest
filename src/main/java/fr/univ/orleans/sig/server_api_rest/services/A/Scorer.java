package fr.univ.orleans.sig.server_api_rest.services.A;

public interface Scorer<T extends GraphNode> {

    double computeCost(T from, T to);

}
