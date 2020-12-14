package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import fr.univ.orleans.sig.server_api_rest.entities.Utilisateur;

import javax.validation.constraints.Size;

public class QRcodeDTO {

	private final int id;

	private final String text;

	@Size(min = 2, max = 2)
	private final int position;

	public static QRcodeDTO create(QRcode qrcode) {
		return new QRcodeDTO(qrcode.getGid(),qrcode.getText(),qrcode.getPosition());
	}

	private QRcodeDTO(int id, String text, int position) {
		this.id = id;
		this.text = text;
		this.position = position;
	}

	public int getId() { return id; }

	public String getText() { return text; }

	public int getPosition() { return position; }
}
