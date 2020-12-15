package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QRcodeRepository extends JpaRepository<QRcode, String> {

    @Query("select q from QRcode q where q.etage.gid = :idEtage")
    List<QRcode> findAllByEtage(@Param("idEtage") int idEtage);

    List<QRcode> findAllByPosition(Point position);

}
