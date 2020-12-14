package fr.univ.orleans.sig.server_api_rest.repositories;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRcodeRepository extends JpaRepository<QRcode, Integer> {

	// ??? findAllByIdEtage

}
