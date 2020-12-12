package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalleRepository extends JpaRepository<Salle, Integer> {

    Salle findByGeomAndEtageAndNomAndFonction(Polygon geom, Etage etage, String nom, FonctionSalle fonction);

    List<Salle> findAllByEtage(Etage etage);

    Salle findByNom(String nom);

}
