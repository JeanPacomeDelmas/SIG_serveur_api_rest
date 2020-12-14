package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "porte")
public class Porte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private int gid;
    @OneToOne
    @JoinColumn(name = "id_salle_1", nullable = false)
    private Salle salle1;
    @OneToOne
    @JoinColumn(name = "id_salle_2", nullable = false)
    private Salle salle2;
    @Column(name = "geom", nullable = false)
    private LineString geom;

    public Porte() { }

    public Porte(Salle salle1, Salle salle2, LineString geom) {
        this.salle1 = salle1;
        this.salle2 = salle2;
        this.geom = geom;
    }

    public int getGid() {
        return gid;
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

    public LineString getGeom() {
        return geom;
    }

    public void setGeom(LineString geo) {
        this.geom = geo;
    }

}
