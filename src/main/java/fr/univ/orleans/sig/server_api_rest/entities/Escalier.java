package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

@Entity
@Table(name = "escalier")
public class Escalier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private int id;
    @OneToOne
    @JoinColumn(name = "id_etage_b", nullable = false)
    private Etage etage_b;
    @OneToOne
    @JoinColumn(name = "id_etage_h", nullable = false)
    private Etage etage_h;
    @OneToOne
    @JoinColumn(name = "id_salle_b", nullable = false)
    private Salle salle_b;
    @OneToOne
    @JoinColumn(name = "id_salle_h", nullable = false)
    private Salle salle_h;
    @Column(name = "geom", nullable = false)
    private Polygon geom;
    @Column(name = "sortie_b", nullable = false)
    private LineString sortie_b;
    @Column(name = "sortie_h", nullable = false)
    private LineString sortie_h;

    public Escalier() { }

    public Escalier(Etage etage_b, Etage etage_h, Salle salle_b, Salle salle_h, Polygon geom, LineString sortie_b, LineString sortie_h) {
        this.etage_b = etage_b;
        this.etage_h = etage_h;
        this.salle_b = salle_b;
        this.salle_h = salle_h;
        this.geom = geom;
        this.sortie_b = sortie_b;
        this.sortie_b = sortie_h;
    }

    public int getId() {
        return id;
    }

    public Etage getEtage_b() {
        return etage_b;
    }

    public void setEtage_b(Etage etage_bas) {
        this.etage_b = etage_bas;
    }

    public Etage getEtage_h() {
        return etage_h;
    }

    public void setEtage_h(Etage etage_haut) {
        this.etage_h = etage_haut;
    }

    public Salle getSalle_b() {
        return salle_b;
    }

    public void setSalle_b(Salle salle_bas) {
        this.salle_b = salle_bas;
    }

    public Salle getSalle_h() {
        return salle_h;
    }

    public void setSalle_h(Salle haut) {
        this.salle_h = haut;
    }

    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geo) {
        this.geom = geo;
    }

    public LineString getSortie_b() {
        return sortie_b;
    }

    public void setSortie_b(LineString bas) {
        this.sortie_b = bas;
    }

    public LineString getSortie_h() {
        return sortie_h;
    }

    public void setSortie_h(LineString haut) {
        this.sortie_h = haut;
    }

}
