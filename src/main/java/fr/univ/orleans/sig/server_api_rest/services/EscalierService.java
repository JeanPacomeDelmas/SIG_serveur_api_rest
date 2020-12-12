package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.repositories.EscalierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public Collection<Escalier> escalierToEscalier(Etage etageDepart, Etage etageArrivee) {
        ArrayList<Escalier> escaliers = new ArrayList<>();
        Etage etage1 = etageDepart;
        Etage etage2 = etageArrivee;
        while (true) {
            Escalier escalier;
            if (etage1.getNom().equals("rdc")) {
                escalier = escalierRepository.findByEtageHaut(etage1.getGid());
            } else {
                escalier = escalierRepository.findByEtageBas(etage1.getGid());
            }
            if (escalier != null) {
                escaliers.add(escalier);
                etage1 = etage2;
                if (etage1.getNom().equals("1er")) {
                    etage2 = escalier.getEtageH();
                } else {
                    etage2 = escalier.getEtageB();
                }
                if (etage2.getGid() == etageArrivee.getGid()) {
                    return escaliers;
                }
            } else {
                return null;
            }
        }
    }

}
