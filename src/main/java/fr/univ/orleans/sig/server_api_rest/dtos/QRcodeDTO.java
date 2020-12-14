package fr.univ.orleans.sig.server_api_rest.dtos;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class QRcodeDTO {

	private final int id;
	@NotBlank
	private final String text;
	@NotNull
	private final PointDTO position;
	@NotNull
	private final EtageDTO etage;

	public static QRcodeDTO create(QRcode qrcode) {
		return new QRcodeDTO(qrcode.getGid(), qrcode.getText(), PointDTO.create(qrcode.getPosition(), qrcode.getEtage()),
				EtageDTO.create(qrcode.getEtage()));
	}

	private QRcodeDTO(int id, String text, PointDTO position, EtageDTO etage) {
		this.id = id;
		this.text = text;
		this.position = position;
		this.etage = etage;
	}

	public int getId() { return id; }

	public String getText() { return text; }

	public PointDTO getPosition() { return position; }

	public EtageDTO getEtage() {
		return etage;
	}

}
