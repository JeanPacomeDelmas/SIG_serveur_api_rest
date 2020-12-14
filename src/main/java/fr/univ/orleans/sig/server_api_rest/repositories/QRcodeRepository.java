package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QRcodeRepository extends JpaRepository<QRcode, Integer> {

    @Query("select q from QRcode q where q.etage.gid = :idEtage")
    List<QRcode> findAllByEtage(@Param("idEtage") int idEtage);

}
