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
    private int gid;
    @OneToOne
    @JoinColumn(name = "id_etage_b", nullable = false)
    private Etage etageB;
    @OneToOne
    @JoinColumn(name = "id_etage_h", nullable = false)
    private Etage etageH;
    @OneToOne
    @JoinColumn(name = "id_salle_b", nullable = false)
    private Salle salleB;
    @OneToOne
    @JoinColumn(name = "id_salle_h", nullable = false)
    private Salle salleH;
    @Column(name = "geom", nullable = false)
    private Polygon geom;
    @Column(name = "sortie_b", nullable = false)
    private LineString sortieB;
    @Column(name = "sortie_h", nullable = false)
    private LineString sortieH;

    public Escalier() {}

    public Escalier(Etage etageB, Etage etageH, Salle salleB, Salle salleH, Polygon geom, LineString sortieB, LineString sortieH) {
        this.etageB = etageB;
        this.etageH = etageH;
        this.salleB = salleB;
        this.salleH = salleH;
        this.geom = geom;
        this.sortieB = sortieB;
        this.sortieB = sortieH;
    }

    public int getGid() {
        return gid;
    }

    public Etage getEtageB() {
        return etageB;
    }

    public void setEtageB(Etage etage_bas) {
        this.etageB = etage_bas;
    }

    public Etage getEtageH() {
        return etageH;
    }

    public void setEtageH(Etage etage_haut) {
        this.etageH = etage_haut;
    }

    public Salle getSalleB() {
        return salleB;
    }

    public void setSalleB(Salle salle_bas) {
        this.salleB = salle_bas;
    }

    public Salle getSalleH() {
        return salleH;
    }

    public void setSalleH(Salle haut) {
        this.salleH = haut;
    }

    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geo) {
        this.geom = geo;
    }

    public LineString getSortieB() {
        return sortieB;
    }

    public void setSortieB(LineString bas) {
        this.sortieB = bas;
    }

    public LineString getSortieH() {
        return sortieH;
    }

    public void setSortieH(LineString haut) {
        this.sortieH = haut;
    }

}
