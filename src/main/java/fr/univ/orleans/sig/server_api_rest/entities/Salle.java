package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

@Entity
@Table(name = "salle")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private int gid;
    @Column(name = "geom", nullable = false)
    private Polygon geom;
    @ManyToOne
    @JoinColumn(name = "id_etage", nullable = false)
    private Etage etage;
    @Column(name = "numero"/*, nullable = false*/)
    private int numero;
    @ManyToOne
    @JoinColumn(name = "id_fonctio", nullable = false)
    private FonctionSalle fonction;

    public Salle() { }

    public Salle(Polygon geom, Etage etage, int numero, FonctionSalle fonction) {
        this.geom = geom;
        this.etage = etage;
        this.numero = numero;
        this.fonction = fonction;
    }

    public int getGid() {
        return gid;
    }

    public Polygon getGeom() {
        return geom;
    }

    public void setGeom(Polygon geo) {
        this.geom = geo;
    }

    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public FonctionSalle getFonction() {
        return fonction;
    }

    public void setFonction(FonctionSalle fonction) {
        this.fonction = fonction;
    }

}
