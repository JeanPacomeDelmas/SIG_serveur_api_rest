package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

public class UtilisateurDTO {

	private final int id;
	private final String sessiontoken;

	@Size(min = 2, max = 2)
	private int position;

	private Date datedernierscan;

	public static UtilisateurDTO create(Utilisateur utilisateur) {
		return new UtilisateurDTO(utilisateur.getGid(),utilisateur.getSessiontoken(),
				utilisateur.getPosition(), utilisateur.getDatedernierscan());

	}
	private UtilisateurDTO(int id, String sessiontoken, int position, Date datedernierscan ) {
		this.id = id;
		this.sessiontoken = sessiontoken;
		this.position = position;
		this.datedernierscan = datedernierscan;
	}

	public Date getDatedernierscan() { return datedernierscan; }

	public void setDatedernierscan(Date datedernierscan) { this.datedernierscan = datedernierscan; }

	public int getId() { return id; }

	public String getSessiontoken() { return sessiontoken; }

	public int getPosition() { return position; }

	public void setPosition(int position) { this.position = position; }
}
