package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
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

    public static Geometry wktToGeometry(String wellKnownText) throws ParseException {
        return new WKTReader().read(wellKnownText);
    }

    protected Polygon polygonDTOToPolygon(PolygonDTO polygonDTO) throws ParseException {
        StringBuilder polygon = new StringBuilder(polygonDTO.getType().toUpperCase(Locale.ROOT) + " ((");
        for (ArrayList<ArrayList<Double>> arrayList2 : (ArrayList<ArrayList<ArrayList<Double>>>) polygonDTO.getCoordinates()) {
            for (ArrayList<Double> arrayList : arrayList2) {
                polygon.append(arrayList.get(0)).append(" ").append(arrayList.get(1)).append(", ");
            }
        }
        polygon.append("))");
        return (Polygon) wktToGeometry(polygon.toString());
    }

    protected LineString lineStringDTOToLineString(LineStringDTO lineStringDTO) throws ParseException {
        StringBuilder lineString = new StringBuilder(lineStringDTO.getType().toUpperCase(Locale.ROOT) + " (");
        for (ArrayList<Double> arrayList : (ArrayList<ArrayList<Double>>) lineStringDTO.getCoordinates()) {
            lineString.append(arrayList.get(0)).append(" ").append(arrayList.get(1)).append(", ");
        }
        lineString.append(")");
        return (LineString) wktToGeometry(lineString.toString());
    }

    protected Salle salleDTOToSalle(SalleDTO salleDTO) throws ParseException {
        Polygon polygon = polygonDTOToPolygon(salleDTO.getGeometry());
        return salleRepository.findByGeomAndEtageAndNomAndFonction(polygon, etageDTOToEtage(salleDTO.getProperties().getEtage()),
                salleDTO.getProperties().getNom(), fonctionSalleDTOToFonctionSalle(salleDTO.getProperties().getFonction()));
    }

    protected Porte porteDTOToPorte(PorteDTO porteDTO) throws ParseException {
        LineString lineString = lineStringDTOToLineString(porteDTO.getGeometry());
        return porteRepository.findBySalle1AndSalle2AndGeom(salleDTOToSalle(porteDTO.getSalle1()),
                salleDTOToSalle(porteDTO.getSalle2()), lineString);
    }

    protected Porte createPorteFromPorteDTO(PorteDTO porteDTO) throws ParseException {
        LineString lineString = lineStringDTOToLineString(porteDTO.getGeometry());
        Salle salle1 = salleDTOToSalle(porteDTO.getSalle1());
        Salle salle2 = salleDTOToSalle(porteDTO.getSalle2());
        if (salle1 != null && salle2 != null) {
            return new Porte(salle1, salle2, lineString);
        }
        return null;
    }

    protected Etage etageDTOToEtage(EtageDTO etageDTO) {
        return etageRepository.findByNom(etageDTO.getNom());
    }

    protected FonctionSalle fonctionSalleDTOToFonctionSalle(FonctionSalleDTO fonctionSalleDTO) {
        return fonctionSalleRepository.findByNom(fonctionSalleDTO.getNom());
    }

}
