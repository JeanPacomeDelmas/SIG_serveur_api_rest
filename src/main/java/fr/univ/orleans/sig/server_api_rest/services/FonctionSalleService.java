package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.repositories.FonctionSalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FonctionSalleService implements GenericService<FonctionSalle> {

    @Autowired
    private FonctionSalleRepository fonctionSalleRepository;

    @Override
    public Collection<FonctionSalle> findAll() {
        return fonctionSalleRepository.findAll();
    }

    @Override
    public FonctionSalle findById(int id) {
        return fonctionSalleRepository.findById(id).orElse(null);
    }

    public boolean conflict(String nom) {
        return fonctionSalleRepository.existsByNom(nom);
    }

    @Override
    public FonctionSalle save(FonctionSalle entity) {
        if (entity != null)
            return fonctionSalleRepository.save(entity);
        return null;
    }

    @Override
    public FonctionSalle update(FonctionSalle entity) {
        if (entity != null)
            return fonctionSalleRepository.save(entity);
        return null;
    }

    @Override
    public boolean delete(FonctionSalle entity) {
        if (entity != null) {
            fonctionSalleRepository.delete(entity);
            return true;
        }
        return false;
    }

    public FonctionSalle findByNom(String nom) {
        return fonctionSalleRepository.findByNom(nom);
    }

}
