package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;

import javax.validation.constraints.NotBlank;

public class EtageDTO {

    private final int id;
    @NotBlank
    private String nom;

    public static EtageDTO create(Etage etage) {
        return new EtageDTO(etage.getGid(), etage.getNom());
    }

    private EtageDTO(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
