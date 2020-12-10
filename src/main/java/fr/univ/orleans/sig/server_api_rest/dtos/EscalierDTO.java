package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class EscalierDTO {

//    private int id;
    private Etage etage_b;
    private Etage etage_h;
    private Salle salle_b;
    private Salle salle_h;
    private Polygon geom;
    private LineString sortie_b;
    private LineString sortie_h;

    public static EscalierDTO createEscalierDTO(Escalier escalier) {
        return new EscalierDTO(/*escalier.getId(), */escalier.getEtage_b(), escalier.getEtage_h(), escalier.getSalle_b(), escalier.getSalle_h(), escalier.getGeom(), escalier.getSortie_b(), escalier.getSortie_h());
    }

    private EscalierDTO(/*int id, */Etage etage_b, Etage etage_h, Salle salle_b, Salle salle_h, Polygon geom, LineString sortie_b, LineString sortie_H) {
//        this.id = id;
        this.etage_b = etage_b;
        this.etage_h = etage_h;
        this.salle_b = salle_b;
        this.salle_h = salle_h;
        this.geom = geom;
        this.sortie_b = sortie_b;
        this.sortie_h = sortie_H;
    }

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public Etage getEtage_b() {
        return etage_b;
    }

    public void setEtage_b(Etage etage_b) {
        this.etage_b = etage_b;
    }

    public Etage getEtage_h() {
        return etage_h;
    }

    public void setEtage_h(Etage etage_h) {
        this.etage_h = etage_h;
    }

    public Salle getSalle_b() {
        return salle_b;
    }

    public void setSalle_b(Salle salle_b) {
        this.salle_b = salle_b;
    }

    public Salle getSalle_h() {
        return salle_h;
    }

    public void setSalle_h(Salle salle_h) {
        this.salle_h = salle_h;
    }

    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geom) {
        this.geom = geom;
    }

    public LineString getSortie_b() {
        return sortie_b;
    }

    public void setSortie_b(LineString sortie_b) {
        this.sortie_b = sortie_b;
    }

    public LineString getSortie_h() {
        return sortie_h;
    }

    public void setSortie_h(LineString sortie_h) {
        this.sortie_h = sortie_h;
    }

}
