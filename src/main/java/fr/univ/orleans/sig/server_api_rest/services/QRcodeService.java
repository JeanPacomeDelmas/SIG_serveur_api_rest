package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.QRcode;
import fr.univ.orleans.sig.server_api_rest.repositories.QRcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QRcodeService implements GenericService<QRcode, Integer> {

	@Autowired
	private QRcodeRepository qrcodeRepository;

	@Override
	public Collection<QRcode> findAll() {
		return qrcodeRepository.findAll();
	}

	@Override
	public QRcode findById(Integer id) {
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
