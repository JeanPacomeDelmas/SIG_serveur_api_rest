package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

public class UtilisateurDTO {

	@NotBlank
	private final String username;
	private PointDTO position;
	private EtageDTO etage;
	private Timestamp dateDernierScan;

	public static UtilisateurDTO create(Utilisateur utilisateur) {
		return new UtilisateurDTO(utilisateur.getUsername(),
				PointDTO.create(utilisateur.getPosition(), utilisateur.getEtage()),
				EtageDTO.create(utilisateur.getEtage()),
				utilisateur.getDateDernierScan());
	}

	private UtilisateurDTO(String username, PointDTO position, EtageDTO etage, Timestamp dateDernierScan) {
		this.username = username;
		this.position = position;
		this.etage = etage;
		this.dateDernierScan = dateDernierScan;
	}

	public String getUsername() { return username; }

	public PointDTO getPosition() { return position; }

	public EtageDTO getEtage() {
		return etage;
	}

	public void setEtage(EtageDTO etage) {
		this.etage = etage;
	}

	public void setPosition(PointDTO position) { this.position = position; }

	public Timestamp getDateDernierScan() { return dateDernierScan; }

	public void setDateDernierScan(Timestamp dateDernierScan) { this.dateDernierScan = dateDernierScan; }

}
