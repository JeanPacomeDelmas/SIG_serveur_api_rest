package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import org.locationtech.jts.geom.Point;

import javax.validation.Valid;

public class PointDTO {

    private final String type;
    private double x;
    private double y;
    @Valid
    private EtageDTO properties;

    public static PointDTO create(Point point, Etage etage) {
        return new PointDTO(point.getX(), point.getY(), EtageDTO.create(etage));
    }

    public PointDTO(double x, double y, EtageDTO properties) {
        this.type = "Point";
        this.x = x;
        this.y = y;
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public EtageDTO getProperties() {
        return properties;
    }

    public void setProperties(EtageDTO properties) {
        this.properties = properties;
    }

}
