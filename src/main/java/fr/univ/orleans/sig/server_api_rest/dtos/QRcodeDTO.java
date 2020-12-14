//package fr.univ.orleans.sig.server_api_rest.dtos;
//
//import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
//
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//
//public class QRcodeDTO {
//
//	@NotBlank
//	private final String text;
//	@NotNull
//	private final PointDTO position;
//	@NotNull
//	private final EtageDTO etage;
//
//	public static QRcodeDTO create(QRcode qrcode) {
//		return new QRcodeDTO(qrcode.getText(), PointDTO.create(qrcode.getPosition(), qrcode.getEtage()),
//				EtageDTO.create(qrcode.getEtage()));
//	}
//
//	private QRcodeDTO(String text, PointDTO position, EtageDTO etage) {
//		this.text = text;
//		this.position = position;
//		this.etage = etage;
//	}
//
//	public String getText() { return text; }
//
//	public PointDTO getPosition() { return position; }
//
//	public EtageDTO getEtage() {
//		return etage;
//	}
//
//}
