package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.EtageDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.repositories.EtageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Positive;
import java.util.Collection;

@Service
public class EtageService extends SuperService implements GenericService<Etage, Integer> {

    @Autowired
    private EtageRepository etageRepository;

    @Override
    public Collection<Etage> findAll() {
        return etageRepository.findAll();
    }

    @Override
    public Etage findById(@Positive Integer id) {
        return etageRepository.findById(id).orElse(null);
    }

    @Override
    public Etage save(Etage entity) {
        if (entity != null)
            return etageRepository.save(entity);
        return null;
    }

    @Override
    public Etage update(Etage entity) {
        if (entity != null)
            return etageRepository.save(entity);
        return null;
    }

    @Override
    public boolean delete(Etage entity) {
        if (entity != null) {
            etageRepository.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Etage etageDTOToEtage(EtageDTO etageDTO) {
        return super.etageDTOToEtage(etageDTO);
    }
}
