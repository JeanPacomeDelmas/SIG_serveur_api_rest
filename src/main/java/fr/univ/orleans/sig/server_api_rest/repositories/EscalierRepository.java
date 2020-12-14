package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EscalierRepository extends JpaRepository<Escalier, Integer> {

    @Query("select e from Escalier e where e.etageB.gid = :idEtageH or e.etageH.gid = :idEtageB")
    List<Escalier> findAllByEtage(@Param("idEtageB") int idEtageB, @Param("idEtageH") int idEtageH);

    @Query("select e from Escalier e where e.etageB.gid = :idEtageH")
    Escalier findByEtageHaut(@Param("idEtageH") int idEtageH);

    @Query("select e from Escalier e where e.etageH.gid = :idEtageB")
    Escalier findByEtageBas(@Param("idEtageB") int idEtageB);

}
