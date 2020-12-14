package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.PorteDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.QRcodeDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.PorteRepository;
import fr.univ.orleans.sig.server_api_rest.repositories.QRcodeRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class QRcodeService implements  GenericService<QRcode> {
	@Autowired
	private QRcodeRepository qrcodeRepository;

	@Override
	public Collection<QRcode> findAll() {
		return qrcodeRepository.findAll();
	}

	@Override
	public QRcode findById(int id) {
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
}
