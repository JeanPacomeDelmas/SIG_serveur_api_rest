package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.EtageDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.PointDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.QRcodeDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import fr.univ.orleans.sig.server_api_rest.repositories.QRcodeRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QRcodeService extends SuperService implements GenericService<QRcode, String> {

	@Autowired
	private QRcodeRepository qrcodeRepository;

	@Override
	public Collection<QRcode> findAll() {
		return qrcodeRepository.findAll();
	}

	@Override
	public QRcode findById(String id) {
		return qrcodeRepository.findById(id).orElse(null);
	}

	@Override
	public QRcode save(QRcode entity) {
		if (entity != null)
			return qrcodeRepository.save(entity);
		return null;
	}

	@Override
	public QRcode update(QRcode entity) {
		if (entity != null)
			return qrcodeRepository.save(entity);
		return null;
	}

	@Override
	public boolean delete(QRcode entity) {
		if (entity != null) {
			qrcodeRepository.delete(entity);
			return true;
		}
		return false;
	}

	public boolean conflict(PointDTO position, EtageDTO etage) throws ParseException {
		Collection<QRcode> qRcodes = qrcodeRepository.findAllByPosition(pointDTOToPoint(position));
		if (!qRcodes.isEmpty()) {
			for (QRcode qRcode : qRcodes) {
				if (qRcode.getEtage().getGid() == etageDTOToEtage(etage).getGid()) {
					return true;
				}
			}
		}
		return false;
	}

	public QRcode createQRCodeFromQRCodeDTO(QRcodeDTO qRcodeDTO) throws ParseException {
		return super.createQRCodeFromQRCodeDTO(qRcodeDTO);
	}

	public Collection<QRcode> findAllQRCOdeByEtage(Etage etage) {
		return qrcodeRepository.findAllByEtage(etage.getGid());
	}
}
