package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.FonctionSalleDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.SalleDTO;
import fr.univ.orleans.sig.server_api_rest.entities.*;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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

//    private ArrayList<Salle> salles = new ArrayList<>();

    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        Collection<FonctionSalleDTO> fonctionSalleDTOS = new ArrayList<>();
        for (FonctionSalle salle : fonctionSalleRepository.findAll()) {
            fonctionSalleDTOS.add(FonctionSalleDTO.createFonctionSalleDTO(salle));
        }
        return ResponseEntity.ok(fonctionSalleDTOS);
    }

    @GetMapping(value = "/fonction_salle/{id}")
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
    public ResponseEntity<SalleDTO> findSalleById(@PathVariable int id) {
        return salleRepository.findById(id).map(value -> ResponseEntity.ok(SalleDTO.createSalleDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @GetMapping(value = "/salle/{id}")
//    public void findSalleById(@PathVariable int id) {
//        Optional<Salle> salle = salleRepository.findById(id);
//        salles.add(salle.get());
//        Polygon geom = salles.get(0).getGeo();
//    }

    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<Escalier>> findAllEscaliers() {
        return ResponseEntity.ok(escalierRepository.findAll());
    }

    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<Escalier> findEscalierById(@PathVariable int id) {
        return escalierRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
