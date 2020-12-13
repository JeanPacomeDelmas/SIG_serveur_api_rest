package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;

import javax.validation.Valid;

public class EscalierDTO implements TrajetDTO {

    private final int id;
    private final String type;
    @Valid
    private SalleDTO salleB;
    @Valid
    private SalleDTO salleH;
    @Valid
    private PolygonDTO geometry;
    @Valid
    private LineStringDTO sortieB;
    @Valid
    private LineStringDTO sortieH;
    @Valid
    private SubEscalierDTO properties;

    public static EscalierDTO create(Escalier escalier) {
        return new EscalierDTO(escalier.getGid(), SalleDTO.create(escalier.getSalleB()),
                SalleDTO.create(escalier.getSalleH()), PolygonDTO.create(escalier.getGeom()),
                LineStringDTO.create(escalier.getSortieB()), LineStringDTO.create(escalier.getSortieH()),
                new SubEscalierDTO(EtageDTO.create(escalier.getEtageB()), EtageDTO.create(escalier.getEtageH())));
    }

    private EscalierDTO(int id, SalleDTO salleB, SalleDTO salleH,
                        PolygonDTO geom, LineStringDTO sortieB, LineStringDTO sortieH, SubEscalierDTO properties) {
        this.id = id;
        this.type = "Feature";
        this.salleB = salleB;
        this.salleH = salleH;
        this.geometry = geom;
        this.sortieB = sortieB;
        this.sortieH = sortieH;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public SalleDTO getSalleB() {
        return salleB;
    }

    public void setSalleB(SalleDTO salleB) {
        this.salleB = salleB;
    }

    public SalleDTO getSalleH() {
        return salleH;
    }

    public void setSalleH(SalleDTO salleH) {
        this.salleH = salleH;
    }

    public PolygonDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(PolygonDTO geom) {
        this.geometry = geom;
    }

    public LineStringDTO getSortieB() {
        return sortieB;
    }

    public void setSortieB(LineStringDTO sortieB) {
        this.sortieB = sortieB;
    }

    public LineStringDTO getSortieH() {
        return sortieH;
    }

    public void setSortieH(LineStringDTO sortieH) {
        this.sortieH = sortieH;
    }

    public SubEscalierDTO getProperties() {
        return properties;
    }

    public void setProperties(SubEscalierDTO properties) {
        this.properties = properties;
    }

}
