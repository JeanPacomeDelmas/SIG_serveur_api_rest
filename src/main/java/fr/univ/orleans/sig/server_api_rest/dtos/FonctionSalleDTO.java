package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;

public class FonctionSalleDTO {

//    private int id;
    private String nom;

    public static FonctionSalleDTO createFonctionSalleDTO(FonctionSalle fonctionSalle) {
        return new FonctionSalleDTO(/*fonctionSalle.getId(), */fonctionSalle.getNom());
    }

    private FonctionSalleDTO(/*int id, */String nom) {
//        this.id = id;
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
