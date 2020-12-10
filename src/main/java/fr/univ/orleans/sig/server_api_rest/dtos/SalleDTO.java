package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;

public class SalleDTO {

//    private int gid;
    private String geom;
    private EtageDTO etage;
    private int numero;
    private FonctionSalleDTO fonction;

    public static SalleDTO createSalleDTO(Salle salle) {
        return new SalleDTO(/*salle.getGid(), */salle.getGeom().toString(), EtageDTO.createEtageDTO(salle.getEtage()), salle.getNumero(), FonctionSalleDTO.createFonctionSalleDTO(salle.getFonction()));
    }

    private SalleDTO(/*int id, */String geom, EtageDTO etage, int numero, FonctionSalleDTO fonction) {
//        this.gid = id;
        this.geom = geom;
        this.etage = etage;
        this.numero = numero;
        this.fonction = fonction;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public EtageDTO getEtage() {
        return etage;
    }

    public void setEtage(EtageDTO etage) {
        this.etage = etage;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public FonctionSalleDTO getFonction() {
        return fonction;
    }

    public void setFonction(FonctionSalleDTO fonction) {
        this.fonction = fonction;
    }

}
