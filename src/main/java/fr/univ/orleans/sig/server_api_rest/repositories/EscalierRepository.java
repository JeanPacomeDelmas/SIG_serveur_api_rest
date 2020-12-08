package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscalierRepository extends JpaRepository<Escalier, Integer> {
}
