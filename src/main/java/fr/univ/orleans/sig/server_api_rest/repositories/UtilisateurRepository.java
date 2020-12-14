//package fr.univ.orleans.sig.server_api_rest.repositories;
//
//import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
//
//    @Query("select u from Utilisateur u where u.etage.gid = :idEtage")
//    List<Utilisateur> findAllByEtage(@Param("idEtage") int idEtage);
//
//    boolean existsByUsername(String username);
//
//}
