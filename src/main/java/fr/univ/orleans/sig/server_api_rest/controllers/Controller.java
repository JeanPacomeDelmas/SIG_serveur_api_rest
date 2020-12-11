package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //////////////////////////////////////////////////////////////
    //////////////////// FONCTION_SALLE //////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        return ResponseEntity.ok(fonctionSalleRepository.findAll().stream().map(FonctionSalleDTO::createFonctionSalleDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/fonction_salle/{id}")
    public ResponseEntity<FonctionSalleDTO> findFonctionSalleById(@PathVariable int id) {
        return fonctionSalleRepository.findById(id).map(value -> ResponseEntity.ok(FonctionSalleDTO.createFonctionSalleDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/fonction_salle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> saveFonctionSalle(@RequestBody FonctionSalleDTO fonctionSalleDTO) {
        if (fonctionSalleRepository.existsByNom(fonctionSalleDTO.getNom())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        FonctionSalle fonctionSalleSave = fonctionSalleRepository.save(new FonctionSalle(fonctionSalleDTO.getNom()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(fonctionSalleSave.getId()).toUri();
        return ResponseEntity.created(location).body(FonctionSalleDTO.createFonctionSalleDTO(fonctionSalleSave));
    }

    @PatchMapping(value = "fonction_salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> updateFonctionSalle(@PathVariable int id, @RequestBody FonctionSalleDTO fonctionSalleDTO) {
        Optional<FonctionSalle> fonctionSalle = fonctionSalleRepository.findById(id);
        if (fonctionSalle.isPresent()) {
            fonctionSalle.get().setNom(fonctionSalleDTO.getNom());
            return ResponseEntity.ok(FonctionSalleDTO.createFonctionSalleDTO(fonctionSalleRepository.save(fonctionSalle.get())));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "fonction_salle/{id}")
    public ResponseEntity<?> deleteFonctionSalle(@PathVariable int id) {
        Optional<FonctionSalle> fonctionSalle = fonctionSalleRepository.findById(id);
        if (fonctionSalle.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// ETAGE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/etages")
    public ResponseEntity<Collection<EtageDTO>> findAllEtages() {
        return ResponseEntity.ok(etageRepository.findAll().stream().map(EtageDTO::createEtageDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/etage/{id}")
    public ResponseEntity<EtageDTO> findEtageById(@PathVariable int id) {
        return etageRepository.findById(id).map(value -> ResponseEntity.ok(EtageDTO.createEtageDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// PORTE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<PorteDTO>> findAllPortes() {
        return ResponseEntity.ok(porteRepository.findAll().stream().map(PorteDTO::createPorteDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/porte/{id}")
    public ResponseEntity<PorteDTO> findPorteById(@PathVariable int id) {
        return porteRepository.findById(id).map(value -> ResponseEntity.ok(PorteDTO.createPorteDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// SALLE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<SalleDTO>> findAllSalles() {
        return ResponseEntity.ok(salleRepository.findAll().stream().map(SalleDTO::createSalleDTO).collect(Collectors.toList()));
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

    //////////////////////////////////////////////////////////////
    /////////////////////// ESCALIER /////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<EscalierDTO>> findAllEscaliers() {
        return ResponseEntity.ok(escalierRepository.findAll().stream().map(EscalierDTO::createEscalierDTO).collect(Collectors.toList()));
    }

    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<EscalierDTO> findEscalierById(@PathVariable int id) {
        return escalierRepository.findById(id).map(value -> ResponseEntity.ok(EscalierDTO.createEscalierDTO(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
