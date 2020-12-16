package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.PorteDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.PorteRepository;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PorteService extends SuperService implements GenericService<Porte, Integer> {

    @Autowired
    private PorteRepository porteRepository;

    @Override
    public Collection<Porte> findAll() {
        return porteRepository.findAll();
    }

    @Override
    public Porte findById(Integer id) {
        return porteRepository.findById(id).orElse(null);
    }

    @Override
    public Porte save(Porte entity) {
        if (entity != null)
            return porteRepository.save(entity);
        return null;
    }

    @Override
    public Porte update(Porte entity) {
        if (entity != null)
            return porteRepository.save(entity);
        return null;
    }

    @Override
    public boolean delete(Porte entity) {
        if (entity != null) {
            porteRepository.delete(entity);
            return true;
        }
        return false;
    }

    @Override
    public Porte porteDTOToPorte(PorteDTO porteDTO) throws ParseException {
        return super.porteDTOToPorte(porteDTO);
    }

    @Override
    public Porte createPorteFromPorteDTO(PorteDTO porteDTO) throws ParseException {
        return super.createPorteFromPorteDTO(porteDTO);
    }

    public Collection<Porte> findAllPorteByEtage(Etage etage) {
        return porteRepository.findAllByIdEtage(etage.getGid());
    }

    public Porte findPorteBySalle(Salle salle,Salle couloir) {
        /////////////////////////
        /////////////////////////
        /////////////////////////
        return porteRepository.findAllBySalle(salle.getGid(), couloir.getGid()).get(0);
    }

}
