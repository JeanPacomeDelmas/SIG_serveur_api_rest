package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Porte;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class PorteDTO implements TrajetDTO {

    private final int id;
    private final String type;
    @Valid
    private SalleDTO salle1;
    @Valid
    private SalleDTO salle2;
    @Size(min = 2, max = 2)
    private LineStringDTO geometry;
    @Valid
    private EtageDTO properties;

    public static PorteDTO create(Porte porte) {
        return new PorteDTO(porte.getGid(), SalleDTO.create(porte.getSalle1()), SalleDTO.create(porte.getSalle2()),
                LineStringDTO.create(porte.getGeom()), EtageDTO.create(porte.getSalle1().getEtage()));
    }

    private PorteDTO(int id, SalleDTO salle1, SalleDTO salle2, LineStringDTO geometry, EtageDTO properties) {
        this.id = id;
        this.type = "Feature";
        this.salle1 = salle1;
        this.salle2 = salle2;
        this.geometry = geometry;
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public SalleDTO getSalle1() {
        return salle1;
    }

    public void setSalle1(SalleDTO salle1) {
        this.salle1 = salle1;
    }

    public SalleDTO getSalle2() {
        return salle2;
    }

    public void setSalle2(SalleDTO salle2) {
        this.salle2 = salle2;
    }

    public LineStringDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(LineStringDTO geometry) {
        this.geometry = geometry;
    }

    public EtageDTO getProperties() {
        return properties;
    }

    public void setProperties(EtageDTO properties) {
        this.properties = properties;
    }

}
