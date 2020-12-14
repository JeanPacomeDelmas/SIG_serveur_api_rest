package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import org.locationtech.jts.geom.Point;

public class PointDTO {

    private final String type;
    private double x;
    private double y;
    private EtageDTO attributes;

    public static PointDTO create(Point point, Etage etage) {
        return new PointDTO(point.getX(), point.getY(), EtageDTO.create(etage));
    }

    public PointDTO(double x, double y, EtageDTO attributes) {
        this.type = "Point";
        this.x = x;
        this.y = y;
        this.attributes = attributes;
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

    public EtageDTO getAttributes() {
        return attributes;
    }

    public void setAttributes(EtageDTO attributes) {
        this.attributes = attributes;
    }

}
