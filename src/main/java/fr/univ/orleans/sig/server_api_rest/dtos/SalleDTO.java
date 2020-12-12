package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.services.SuperService;

public class SalleDTO {

    private final int id;
    private final String type;
    private PolygonDTO geometry;
    private EtageDTO etage;
    private int numero;
    private FonctionSalleDTO fonction;

    public static SalleDTO create(Salle salle) {
        return new SalleDTO(salle.getGid(), PolygonDTO.create(salle.getGeom()),
                EtageDTO.create(salle.getEtage()), salle.getNumero(), FonctionSalleDTO.create(salle.getFonction()));
    }

    private SalleDTO(int id, PolygonDTO geometry, EtageDTO etage, int numero, FonctionSalleDTO fonction) {
        this.id = id;
        this.type = "Feature";
        this.geometry = geometry;
        this.etage = etage;
        this.numero = numero;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public FonctionSalleDTO getFonction() {
        return fonction;
    }

    public void setFonction(FonctionSalleDTO fonction) {
        this.fonction = fonction;
    }

}
