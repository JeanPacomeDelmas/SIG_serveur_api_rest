package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;

public class EtageDTO {

//    private int gid;
    private String nom;

    public static EtageDTO createEtageDTO(Etage etage) {
        return new EtageDTO(/*etage.getId(), */etage.getNom());
    }

    private EtageDTO(/*int id, */String nom) {
//        this.gid = id;
        this.nom = nom;
    }

//    public int getGid() {
//        return id;
//    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
