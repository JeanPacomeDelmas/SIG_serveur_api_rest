package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorteRepository extends JpaRepository<Porte, Integer> {

    boolean existsBySalle1AndSalle2AndGeom(Salle salle1, Salle salle2, LineString geom);

}
