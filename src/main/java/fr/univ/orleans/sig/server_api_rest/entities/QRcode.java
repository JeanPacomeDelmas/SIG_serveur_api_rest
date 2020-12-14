package fr.univ.orleans.sig.server_api_rest.entities;

import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "qrcode")
public class QRcode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int gid;
	@Column(name = "text", nullable = false)
	private String text;
	@Column(name = "position", nullable = false)
	private int position;

	public QRcode() {}

	public QRcode(String text, int position) {
		this.text = text;
		this.position = position;
	}

	public int getGid() { return gid; }

	public void setGid(int gid) { this.gid = gid; }

	public String getText() { return text; }

	public void setText(String text) { this.text = text; }

	public int getPosition() { return position; }

	public void setPosition(int position) { this.position = position; }
}
