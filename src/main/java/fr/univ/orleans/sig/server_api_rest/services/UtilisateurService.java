package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.PorteDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;
import fr.univ.orleans.sig.server_api_rest.repositories.PorteRepository;
import fr.univ.orleans.sig.server_api_rest.repositories.UtilisateurRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UtilisateurService implements GenericService<Utilisateur> {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Override
	public Collection<Utilisateur> findAll() {
		return utilisateurRepository.findAll();
	}

	@Override
	public Utilisateur findById(int id) {
		return null;
	}

	@Override
	public Utilisateur save(Utilisateur entity) {
		return null;
	}

	@Override
	public Utilisateur update(Utilisateur entity) {
		return null;
	}

	@Override
	public boolean delete(Utilisateur entity) {
		return false;
	}
}
