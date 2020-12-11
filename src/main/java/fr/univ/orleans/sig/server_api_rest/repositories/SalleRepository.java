package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Integer> {

    Salle findByGeomAndEtageAndNumeroAndFonction(Polygon geom, Etage etage, int numero, FonctionSalle fonction);

}
