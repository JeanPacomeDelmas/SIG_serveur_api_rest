package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.services.SuperService;

import java.util.*;

public class EscalierDTO implements TrajetDTO {

    private final int id;
    private final String type;
    private EtageDTO etageB;
    private EtageDTO etageH;
    private SalleDTO salleB;
    private SalleDTO salleH;
    private PolygonDTO geometry;
    private LineStringDTO sortieB;
    private LineStringDTO sortieH;

    public static EscalierDTO create(Escalier escalier) {
        return new EscalierDTO(escalier.getGid(), EtageDTO.create(escalier.getEtageB()),
                EtageDTO.create(escalier.getEtageH()), SalleDTO.create(escalier.getSalleB()),
                SalleDTO.create(escalier.getSalleH()), PolygonDTO.create(escalier.getGeom()),
                LineStringDTO.create(escalier.getSortieB()), LineStringDTO.create(escalier.getSortieH()));
    }

    private EscalierDTO(int id, EtageDTO etageB, EtageDTO etageH, SalleDTO salleB, SalleDTO salleH,
                        PolygonDTO geom, LineStringDTO sortieB, LineStringDTO sortieH) {
        this.id = id;
        this.type = "Feature";
        this.etageB = etageB;
        this.etageH = etageH;
        this.salleB = salleB;
        this.salleH = salleH;
        this.geometry = geom;
        this.sortieB = sortieB;
        this.sortieH = sortieH;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public EtageDTO getEtageB() {
        return etageB;
    }

    public void setEtageB(EtageDTO etageB) {
        this.etageB = etageB;
    }

    public EtageDTO getEtageH() {
        return etageH;
    }

    public void setEtageH(EtageDTO etageH) {
        this.etageH = etageH;
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

}
