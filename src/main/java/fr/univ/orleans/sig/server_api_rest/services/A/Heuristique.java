package fr.univ.orleans.sig.server_api_rest.services.A;

public interface Heuristique<T extends NoeudGraphe> {

    double calculerCoup(T from, T to);

}
