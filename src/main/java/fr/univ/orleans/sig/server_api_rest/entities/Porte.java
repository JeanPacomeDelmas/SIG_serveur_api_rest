package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "porte")
public class Porte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "id_salle1", nullable = false)
    private Salle salle1;
    @OneToOne
    @JoinColumn(name = "id_salle2", nullable = false)
    private Salle salle2;
    @Column(name = "geo", nullable = false)
    private LineString geo;

    public Porte() { }

    public Porte(Salle salle1, Salle salle2, LineString geo) {
        this.salle1 = salle1;
        this.salle2 = salle2;
        this.geo = geo;
    }

    public int getId() {
        return id;
    }

    public Salle getSalle1() {
        return salle1;
    }

    public void setSalle1(Salle salle1) {
        this.salle1 = salle1;
    }

    public Salle getSalle2() {
        return salle2;
    }

    public void setSalle2(Salle salle2) {
        this.salle2 = salle2;
    }

    public LineString getGeo() {
        return geo;
    }

    public void setGeo(LineString geo) {
        this.geo = geo;
    }

}
