package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SalleDTO {

    private final int id;
    private final String type;
    private PolygonDTO geometry;
    private EtageDTO etage;
    @NotNull
    private String nom;
    @Valid
    private FonctionSalleDTO fonction;

    public static SalleDTO create(Salle salle) {
        return new SalleDTO(salle.getGid(), PolygonDTO.create(salle.getGeom()),
                EtageDTO.create(salle.getEtage()), salle.getNom(), FonctionSalleDTO.create(salle.getFonction()));
    }

    private SalleDTO(int id, PolygonDTO geometry, EtageDTO etage, String nom, FonctionSalleDTO fonction) {
        this.id = id;
        this.type = "Feature";
        this.geometry = geometry;
        this.etage = etage;
        this.nom = nom;
        this.fonction = fonction;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public PolygonDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(PolygonDTO geometry) {
        this.geometry = geometry;
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
