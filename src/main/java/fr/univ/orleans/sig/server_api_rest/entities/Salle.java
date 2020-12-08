package fr.univ.orleans.sig.server_api_rest.entities;


import org.locationtech.jts.geom.Polygon;

import javax.persistence.*;

@Entity
@Table(name = "salle")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "geo", nullable = false)
    private Polygon geo;
    @ManyToOne
    @JoinColumn(name = "id_etage", nullable = false)
    private Etage etage;
    @Column(name = "numero", nullable = false)
    private int numero;
    @ManyToOne
    @JoinColumn(name = "id_fonction", nullable = false)
    private FonctionSalle fonction;

    public Salle() { }

    public Salle(Polygon geo, Etage etage, int numero, FonctionSalle fonction) {
        this.geo = geo;
        this.etage = etage;
        this.numero = numero;
        this.fonction = fonction;
    }

    public int getId() {
        return id;
    }

    public Polygon getGeo() {
        return geo;
    }

    public void setGeo(Polygon geo) {
        this.geo = geo;
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
