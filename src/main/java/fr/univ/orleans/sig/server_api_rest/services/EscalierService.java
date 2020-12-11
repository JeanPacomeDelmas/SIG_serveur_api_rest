package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.repositories.EscalierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EscalierService implements GenericService<Escalier> {

    @Autowired
    private EscalierRepository escalierRepository;

    @Override
    public Collection<Escalier> findAll() {
        return escalierRepository.findAll();
    }

    @Override
    public Escalier findById(int id) {
        return escalierRepository.findById(id).orElse(null);
    }

    @Override
    public Escalier save(Escalier entity) {
        if (entity != null)
            return escalierRepository.save(entity);
        return null;
    }

    @Override
    public Escalier update(Escalier entity) {
        if (entity != null)
            return escalierRepository.save(entity);
        return null;
    }

    @Override
    public boolean delete(Escalier entity) {
        if (entity != null) {
            escalierRepository.delete(entity);
            return true;
        }
        return false;
    }

}
