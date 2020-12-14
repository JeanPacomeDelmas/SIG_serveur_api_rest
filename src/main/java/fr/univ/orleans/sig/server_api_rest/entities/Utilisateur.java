package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int gid;
	@Column(name = "sessiontoken", nullable = false)
	private String sessiontoken;
	@Column(name = "position")
	private int position;
	@Column(name = "datedernierscan")
	private Date datedernierscan;


	public Utilisateur() {}

	public Utilisateur(String sessiontoken) {
		this.sessiontoken = sessiontoken;
	}



	public Date getDatedernierscan() { return datedernierscan; }

	public void setDatedernierscan(Date datedernierscan) { this.datedernierscan = datedernierscan; }

	public int getGid() { return gid; }

	public void setGid(int gid) { this.gid = gid; }

	public String getSessiontoken() { return sessiontoken; }

	public void setSessiontoken(String sessiontoken) { this.sessiontoken = sessiontoken; }

	public int getPosition() { return position; }

	public void setPosition(int position) { this.position = position; }
}
