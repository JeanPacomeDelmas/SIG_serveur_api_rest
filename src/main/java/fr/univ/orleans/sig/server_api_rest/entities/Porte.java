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
    private Salle salle_1;
    @OneToOne
    @JoinColumn(name = "id_salle_2", nullable = false)
    private Salle salle_2;
    @Column(name = "geom", nullable = false)
    private LineString geom;

    public Porte() { }

    public Porte(Salle salle_1, Salle salle_2, LineString geom) {
        this.salle_1 = salle_1;
        this.salle_2 = salle_2;
        this.geom = geom;
    }

    public int getGid() {
        return gid;
    }

    public Salle getSalle_1() {
        return salle_1;
    }

    public void setSalle_1(Salle salle1) {
        this.salle_1 = salle1;
    }

    public Salle getSalle_2() {
        return salle_2;
    }

    public void setSalle_2(Salle salle2) {
        this.salle_2 = salle2;
    }

    public LineString getGeom() {
        return geom;
    }

    public void setGeom(LineString geo) {
        this.geom = geo;
    }

}
