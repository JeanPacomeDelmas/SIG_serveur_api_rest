package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.Point;

import javax.persistence.Entity;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

	@Id
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "position")
	private Point position;
	@Column(name = "date_dernier_scan")
	private Timestamp dateDernierScan; ////////////////////////////////////////

	public Utilisateur() {}

	public Utilisateur(String username) {
		this.username = username;
		this.position = null;
		this.dateDernierScan = null;
	}

	public Timestamp getDateDernierScan() { return dateDernierScan; }

	public void setDateDernierScan(Timestamp datedernierscan) { this.dateDernierScan = datedernierscan; }

	public String getUsername() { return username; }

	public void setUsername(String sessiontoken) { this.username = sessiontoken; }

	public Point getPosition() { return position; }

	public void setPosition(Point position) { this.position = position; }

}
