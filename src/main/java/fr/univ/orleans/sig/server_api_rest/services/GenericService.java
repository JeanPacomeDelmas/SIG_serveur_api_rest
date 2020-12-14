package fr.univ.orleans.sig.server_api_rest.services;

import java.util.*;

public interface GenericService<E, ID> {

    Collection<E> findAll();
    E findById(ID id);
    E save(E entity);
    E update(E entity);
    boolean delete(E entity);

}

//    public void insertPolygon(String polygon) {
//        PointEntity entity = new PointEntity();
//        entity.setPoint((Point) wktToGeometry(point));
//        session.persist(entity);
//    }
//
//    public Geometry createCircle(double x, double y, double radius) {
//        GeometricShapeFactory shapeFactory = new GeometricShapeFactory();
//        shapeFactory.setNumPoints(32);
//        shapeFactory.setCentre(new Coordinate(x, y));
//        shapeFactory.setSize(radius * 2);
//        return shapeFactory.createCircle();
//    }