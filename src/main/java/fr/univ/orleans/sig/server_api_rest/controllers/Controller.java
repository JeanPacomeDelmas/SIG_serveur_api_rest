package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.entities.*;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private FonctionSalleRepository fonctionSalleRepository;
    @Autowired
    private EtageRepository etageRepository;
    @Autowired
    private EscalierRepository escalierRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PorteRepository porteRepository;

    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalle>> findAllFonctionSalles() {
        return ResponseEntity.ok(fonctionSalleRepository.findAll());
    }

    @GetMapping(value = "/fonction_salles/{id}")
    public ResponseEntity<FonctionSalle> findFonctionSalleById(@PathVariable int id) {
        return fonctionSalleRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/etages")
    public ResponseEntity<Collection<Etage>> findAllEtages() {
        return ResponseEntity.ok(etageRepository.findAll());
    }

    @GetMapping(value = "/etage/{id}")
    public ResponseEntity<Etage> findEtageById(@PathVariable int id) {
        return etageRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<Porte>> findAllPortes() {
        return ResponseEntity.ok(porteRepository.findAll());
    }

    @GetMapping(value = "/porte/{id}")
    public ResponseEntity<Porte> findPorteById(@PathVariable int id) {
        return porteRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<Salle>> findAllSalles() {
        return ResponseEntity.ok(salleRepository.findAll());
    }

    @GetMapping(value = "/salle/{id}")
    public ResponseEntity<Salle> findSalleById(@PathVariable int id) {
        return salleRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<Escalier>> findAllEscaliers() {
        return ResponseEntity.ok(escalierRepository.findAll());
    }

    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<Escalier> findEscalierById(@PathVariable int id) {
        return escalierRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
