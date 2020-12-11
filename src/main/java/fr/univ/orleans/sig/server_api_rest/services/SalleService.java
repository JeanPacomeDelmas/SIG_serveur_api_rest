package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SalleService implements GenericService<Salle> {

    @Autowired
    private SalleRepository salleRepository;

    @Override
    public Collection<Salle> findAll() {
        return salleRepository.findAll();
    }

    @Override
    public Salle findById(int id) {
        return salleRepository.findById(id).orElse(null);
    }

    @Override
    public Salle save(Salle entity) {
        if (entity != null)
            return salleRepository.save(entity);
        return null;
    }

    @Override
    public Salle update(Salle entity) {
        if (entity != null)
            return salleRepository.save(entity);
        return null;
    }

    @Override
    public boolean delete(Salle entity) {
        if (entity != null) {
            salleRepository.delete(entity);
            return true;
        }
        return false;
    }

}
