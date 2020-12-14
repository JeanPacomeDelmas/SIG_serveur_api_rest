package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

	@Id
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "position")
	private Point position;
	@OneToOne
	@Column(name = "etage")
	private Etage etage;
	@Column(name = "date_dernier_scan")
	private Timestamp dateDernierScan; ////////////////////////////////////////

	public Utilisateur() {}

//	public Utilisateur(String username) {
//		this.username = username;
//		this.position = null;
//		this.etage = null;
//		this.dateDernierScan = null;
//	}

	public Utilisateur(String username, Point position, Etage etage, Timestamp dateDernierScan) {
		this.username = username;
		this.position = position;
		this.etage = etage;
		this.dateDernierScan = dateDernierScan;
	}

	public String getUsername() { return username; }

	public void setUsername(String sessiontoken) { this.username = sessiontoken; }

	public Point getPosition() { return position; }

	public void setPosition(Point position) { this.position = position; }

	public Etage getEtage() {
		return etage;
	}

	public void setEtage(Etage etage) {
		this.etage = etage;
	}

	public Timestamp getDateDernierScan() { return dateDernierScan; }

	public void setDateDernierScan(Timestamp datedernierscan) { this.dateDernierScan = datedernierscan; }

}
