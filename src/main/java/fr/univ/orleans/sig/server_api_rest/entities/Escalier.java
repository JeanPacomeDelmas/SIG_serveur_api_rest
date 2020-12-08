package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

@Entity
@Table(name = "escalier")
public class Escalier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "id_etage_bas", nullable = false)
    private Etage etage_bas;
    @OneToOne
    @JoinColumn(name = "id_etage_haut", nullable = false)
    private Etage etage_haut;
    @OneToOne
    @JoinColumn(name = "id_salle_bas", nullable = false)
    private Salle salle_bas;
    @OneToOne
    @JoinColumn(name = "id_salle_haut", nullable = false)
    private Salle salle_haut;
    @Column(name = "geo", nullable = false)
    private Polygon geo;
    @Column(name = "bas", nullable = false)
    private LineString bas;
    @Column(name = "Haut", nullable = false)
    private LineString haut;

    public Escalier() { }

    public Escalier(Etage etage_bas, Etage etage_haut, Salle salle_bas, Salle salle_haut, Polygon geo, LineString bas, LineString haut) {
        this.etage_bas = etage_bas;
        this.etage_haut = etage_haut;
        this.salle_bas = salle_bas;
        this.salle_haut = salle_haut;
        this.geo = geo;
        this.bas = bas;
        this.bas = haut;
    }

    public int getId() {
        return id;
    }

    public Etage getEtage_bas() {
        return etage_bas;
    }

    public void setEtage_bas(Etage etage_bas) {
        this.etage_bas = etage_bas;
    }

    public Etage getEtage_haut() {
        return etage_haut;
    }

    public void setEtage_haut(Etage etage_haut) {
        this.etage_haut = etage_haut;
    }

    public Salle getSalle_bas() {
        return salle_bas;
    }

    public void setSalle_bas(Salle salle_bas) {
        this.salle_bas = salle_bas;
    }

    public Salle getSalle_haut() {
        return salle_haut;
    }

    public void setSalle_haut(Salle haut) {
        this.salle_haut = haut;
    }

    public Polygon getGeo() {
        return geo;
    }

    public void setGeo(Polygon geo) {
        this.geo = geo;
    }

    public LineString getBas() {
        return bas;
    }

    public void setBas(LineString bas) {
        this.bas = bas;
    }

    public LineString getHaut() {
        return haut;
    }

    public void setHaut(LineString haut) {
        this.haut = haut;
    }

}
