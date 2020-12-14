package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Table(name = "qrcode")
public class QRcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gid")
	private int gid;
	@Column(name = "text", nullable = false)
	private String text;
	@Column(name = "position", nullable = false)
	private Point position;
	@OneToOne
	@Column(name = "etage")
	private Etage etage;

	public QRcode() {}

	public QRcode(String text, Point position, Etage etage) {
		this.text = text;
		this.position = position;
		this.etage = etage;
	}

	public int getGid() { return gid; }

	public void setGid(int gid) { this.gid = gid; }

	public String getText() { return text; }

	public void setText(String text) { this.text = text; }

	public Point getPosition() { return position; }

	public void setPosition(Point position) { this.position = position; }

	public Etage getEtage() {
		return etage;
	}

	public void setEtage(Etage etage) {
		this.etage = etage;
	}

}
