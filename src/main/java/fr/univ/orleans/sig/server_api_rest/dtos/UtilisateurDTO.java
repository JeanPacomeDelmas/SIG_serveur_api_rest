package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;

import java.sql.Timestamp;
import java.util.Date;

public class UtilisateurDTO {

	private final String username;
	private PointDTO position;
	private Timestamp dateDernierScan;

	public static UtilisateurDTO create(Utilisateur utilisateur) {
		return new UtilisateurDTO(utilisateur.getUsername());
	}
	private UtilisateurDTO(String username) {
		this.username = username;
		this.position = null;
		this.dateDernierScan = null;
	}

	public String getUsername() { return username; }

	public PointDTO getPosition() { return position; }

	public void setPosition(PointDTO position) { this.position = position; }

	public Timestamp getDateDernierScan() { return dateDernierScan; }

	public void setDateDernierScan(Timestamp dateDernierScan) { this.dateDernierScan = dateDernierScan; }

}
