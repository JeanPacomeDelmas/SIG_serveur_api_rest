package fr.univ.orleans.sig.server_api_rest.entities;

import javax.persistence.*;

@Entity
@Table(name = "etage")
public class Etage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int gid;
    @Column(name = "nom", unique = true, nullable = false)
    private String nom;

    public Etage() { }

    public Etage(String nom) {
        this.nom = nom;
    }

    public int getGid() {
        return gid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
