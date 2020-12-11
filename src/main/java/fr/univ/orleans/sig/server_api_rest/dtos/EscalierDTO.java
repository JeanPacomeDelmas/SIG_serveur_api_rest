package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.services.GenericService;

import java.util.*;

public class EscalierDTO {

    private final int id;
    private EtageDTO etageB;
    private EtageDTO etageH;
    private SalleDTO salleB;
    private SalleDTO salleH;
    private Map<String, Object> geometry;
    private Map<String, Object> sortieB;
    private Map<String, Object> sortieH;

    public static EscalierDTO create(Escalier escalier) {
        return new EscalierDTO(escalier.getGid(), EtageDTO.create(escalier.getEtageB()),
                EtageDTO.create(escalier.getEtageH()), SalleDTO.create(escalier.getSalleB()),
                SalleDTO.create(escalier.getSalleH()), GenericService.polygonDTO(escalier.getGeom()),
                GenericService.lineStringDTO(escalier.getSortieB()), GenericService.lineStringDTO(escalier.getSortieH()));
    }

    private EscalierDTO(int id, EtageDTO etageB, EtageDTO etageH, SalleDTO salleB, SalleDTO salleH,
                        Map<String, Object> geom, Map<String, Object> sortieB, Map<String, Object> sortieH) {
        this.id = id;
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

    public Map<String, Object> getGeometry() {
        return geometry;
    }

    public void setGeometry(Map<String, Object> geom) {
        this.geometry = geom;
    }

    public Map<String, Object> getSortieB() {
        return sortieB;
    }

    public void setSortieB(Map<String, Object> sortieB) {
        this.sortieB = sortieB;
    }

    public Map<String, Object> getSortieH() {
        return sortieH;
    }

    public void setSortieH(Map<String, Object> sortieH) {
        this.sortieH = sortieH;
    }

}
