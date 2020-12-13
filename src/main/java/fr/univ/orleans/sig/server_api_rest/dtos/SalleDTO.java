package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;

public class SalleDTO {

    private final int id;
    private final String type;
    private PolygonDTO geometry;
    private SubSalleDTO properties;

    public static SalleDTO create(Salle salle) {
        return new SalleDTO(salle.getGid(), PolygonDTO.create(salle.getGeom()),
                new SubSalleDTO(salle.getGid(), EtageDTO.create(salle.getEtage()), salle.getNom(), FonctionSalleDTO.create(salle.getFonction())));
    }

    private SalleDTO(int id, PolygonDTO geometry, SubSalleDTO properties) {
        this.id = id;
        this.type = "Feature";
        this.geometry = geometry;
        this.properties = properties;
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

    public SubSalleDTO getProperties() {
        return properties;
    }

    public void setProperties(SubSalleDTO properties) {
        this.properties = properties;
    }

}
