package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import org.locationtech.jts.geom.LineString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PorteRepository extends JpaRepository<Porte, Integer> {

    boolean existsBySalle1AndSalle2AndGeom(Salle salle1, Salle salle2, LineString geom);

    Porte findBySalle1AndSalle2AndGeom(Salle salle1, Salle salle2, LineString geom);

    @Query("select p from Porte p where p.salle1.etage.gid = :idEtage")
    List<Porte> findAllByIdEtage(@Param("idEtage") int idEtage);

}
