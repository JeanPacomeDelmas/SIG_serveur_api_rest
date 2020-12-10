package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.LineString;

public class PorteDTO {

//    private int gid;
    private Salle salle_1;
    private Salle salle_2;
    private LineString geom;

    public static PorteDTO createPorteDTO(Porte porte) {
        return new PorteDTO(/*porte.getGid(), */porte.getSalle_1(), porte.getSalle_2(), porte.getGeom());
    }

    private PorteDTO(/*int gid, */Salle salle_1, Salle salle_2, LineString geom) {
//        this.gid = gid;
        this.salle_1 = salle_1;
        this.salle_2 = salle_2;
        this.geom = geom;
    }

//    public int getGid() {
//        return gid;
//    }

//    public void setGid(int gid) {
//        this.gid = gid;
//    }

    public Salle getSalle_1() {
        return salle_1;
    }

    public void setSalle_1(Salle salle_1) {
        this.salle_1 = salle_1;
    }

    public Salle getSalle_2() {
        return salle_2;
    }

    public void setSalle_2(Salle salle_2) {
        this.salle_2 = salle_2;
    }

    public LineString getGeom() {
        return geom;
    }

    public void setGeom(LineString geom) {
        this.geom = geom;
    }

}
