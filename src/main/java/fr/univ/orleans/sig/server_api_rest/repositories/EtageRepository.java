package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtageRepository extends JpaRepository<Etage, Integer> {

    Etage findByNom(String nom);

}
