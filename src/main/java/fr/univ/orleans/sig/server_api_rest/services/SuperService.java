package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.EtageDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.FonctionSalleDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.PorteDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.SalleDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SuperService {

    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private EscalierRepository escalierRepository;
    @Autowired
    private EtageRepository etageRepository;
    @Autowired
    private FonctionSalleRepository fonctionSalleRepository;
    @Autowired
    private PorteRepository porteRepository;

    protected Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

    protected Polygon polygonDTOToPolygon(Map<String, Object> polygonDTO) throws ParseException {
        StringBuilder polygon = new StringBuilder(polygonDTO.get("type").toString().toUpperCase(Locale.ROOT) + " ((");
        for (ArrayList<ArrayList<Double>> arrayList2 : (ArrayList<ArrayList<ArrayList<Double>>>) polygonDTO.get("coordinates")) {
            for (ArrayList<Double> arrayList : arrayList2) {
                polygon.append(arrayList.get(0)).append(" ").append(arrayList.get(1)).append(", ");
            }
        }
        polygon.append("))");
        return (Polygon) wktToGeometry(polygon.toString());
    }

    protected LineString lineStringDTOToLineString(Map<String, Object> lineStringDTO) throws ParseException {
        StringBuilder lineString = new StringBuilder(lineStringDTO.get("type").toString().toUpperCase(Locale.ROOT) + " (");
        for (ArrayList<Double> arrayList : (ArrayList<ArrayList<Double>>) lineStringDTO.get("coordinates")) {
            lineString.append(arrayList.get(0)).append(" ").append(arrayList.get(1)).append(", ");
        }
        lineString.append(")");
        return (LineString) wktToGeometry(lineString.toString());
    }

    public static Map<String, Object> polygonToPolygonDTO(Polygon polygon) {
        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        for (Coordinate coordinate : polygon.getCoordinates()) {
            ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
            coordinates.add(coords);
        }
        return new HashMap<>() {{
            put("type", "Polygon");
            put("coordinates", new ArrayList<>(Collections.singletonList(coordinates)));
        }};
    }

    public static Map<String, Object> lineStringToLineStringDTO(LineString lineString) {
        ArrayList<Double> coordinates = new ArrayList<>();
        for (Coordinate coordinate : lineString.getCoordinates()) {
            coordinates.add(coordinate.x);
            coordinates.add(coordinate.y);
        }
        return new HashMap<>() {{
            put("type", "LineString");
            put("coordinates", new ArrayList<>(Collections.singletonList(coordinates)));
        }};
    }

    protected Salle salleDTOToSalle(SalleDTO salleDTO) throws ParseException {
        Polygon polygon = polygonDTOToPolygon(salleDTO.getGeometry());
        return salleRepository.findByGeomAndEtageAndNumeroAndFonction(polygon, etageDTOToEtage(salleDTO.getEtage()),
                salleDTO.getNumero(), fonctionSalleDTOToFonctionSalle(salleDTO.getFonction()));
    }

    protected Porte porteDTOToPorte(PorteDTO porteDTO) throws ParseException {
        LineString lineString = lineStringDTOToLineString(porteDTO.getGeometry());
        return porteRepository.findBySalle1AndSalle2AndGeom(salleDTOToSalle(porteDTO.getSalle1()),
                salleDTOToSalle(porteDTO.getSalle2()), lineString);
    }

    protected Etage etageDTOToEtage(EtageDTO etageDTO) {
        return etageRepository.findByNom(etageDTO.getNom());
    }

    protected FonctionSalle fonctionSalleDTOToFonctionSalle(FonctionSalleDTO fonctionSalleDTO) {
        return fonctionSalleRepository.findByNom(fonctionSalleDTO.getNom());
    }

}
