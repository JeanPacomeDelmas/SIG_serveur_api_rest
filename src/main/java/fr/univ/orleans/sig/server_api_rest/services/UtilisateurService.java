package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.UtilisateurDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;
import fr.univ.orleans.sig.server_api_rest.repositories.UtilisateurRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UtilisateurService extends SuperService implements GenericService<Utilisateur, String> {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public Collection<Utilisateur> findAll() {
		return utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur findById(String id) {
		return utilisateurRepository.findById(id).orElse(null);
	}

	@Override
	public Utilisateur save(Utilisateur entity) {
		if (entity != null)
			return utilisateurRepository.save(entity);
		return null;
	}

	@Override
	public Utilisateur update(Utilisateur entity) {
		if (entity != null)
			return utilisateurRepository.save(entity);
		return null;
	}

	@Override
	public boolean delete(Utilisateur entity) {
		if (entity != null) {
			utilisateurRepository.delete(entity);
			return true;
		}
		return false;
	}

	@Override
	public Utilisateur createUtilisateurFromUtilisateurDTO(UtilisateurDTO utilisateurDTO) throws ParseException {
		return super.createUtilisateurFromUtilisateurDTO(utilisateurDTO);
	}

	public boolean conflict(String username) {
		return utilisateurRepository.existsByUsername(username);
	}


	public Collection<Utilisateur> findAllUtilisateurByEtage(Etage etage) {
		return utilisateurRepository.findAllByEtage(etage.getGid());
	}
}
