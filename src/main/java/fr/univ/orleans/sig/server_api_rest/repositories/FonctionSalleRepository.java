package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FonctionSalleRepository extends JpaRepository<FonctionSalle, Integer> {

    boolean existsByNom(String nom);

    FonctionSalle findByNom(String nom);

}
