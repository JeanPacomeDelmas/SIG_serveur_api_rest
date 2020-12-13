package fr.univ.orleans.sig.server_api_rest.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SubSalleDTO {

    private final int id;
    private EtageDTO etage;
    @NotNull
    private String nom;
    @Valid
    private FonctionSalleDTO fonction;

    public SubSalleDTO(int id, EtageDTO etage, @NotNull String nom, @Valid FonctionSalleDTO fonction) {
        this.id = id;
        this.etage = etage;
        this.nom = nom;
        this.fonction = fonction;
    }

    public int getId() {
        return id;
    }

    public EtageDTO getEtage() {
        return etage;
    }

    public void setEtage(EtageDTO etage) {
        this.etage = etage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public FonctionSalleDTO getFonction() {
        return fonction;
    }

    public void setFonction(FonctionSalleDTO fonction) {
        this.fonction = fonction;
    }

}
