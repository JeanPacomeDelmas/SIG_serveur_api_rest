package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.*;
import fr.univ.orleans.sig.server_api_rest.services.*;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:1234/")
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private FonctionSalleService fonctionSalleService;
    @Autowired
    private EtageService etageService;
    @Autowired
    private EscalierService escalierService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private PorteService porteService;

    //////////////////////////////////////////////////////////////
    //////////////////// FONCTION_SALLE //////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        FonctionSalle fonctionSalle = new FonctionSalle(null);
        return ResponseEntity.ok(
                fonctionSalleService.findAll().stream().map(FonctionSalleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/fonction_salle/{id}")
    public ResponseEntity<FonctionSalleDTO> findFonctionSalleById(@PathVariable int id) {
        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
        if (fonctionSalle != null) {
            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalle));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/fonction_salle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> saveFonctionSalle(@Valid @RequestBody FonctionSalleDTO fonctionSalleDTO) {
        if (fonctionSalleService.conflict(fonctionSalleDTO.getNom())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        FonctionSalle fonctionSalle = fonctionSalleService.save(new FonctionSalle(fonctionSalleDTO.getNom()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(fonctionSalle.getGid()).toUri();
        return ResponseEntity.created(location).body(FonctionSalleDTO.create(fonctionSalle));
    }

    @PatchMapping(value = "fonction_salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> updateFonctionSalle(@PathVariable int id, @Valid @RequestBody FonctionSalleDTO fonctionSalleDTO) {
        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
        if (fonctionSalle != null) {
            fonctionSalle.setNom(fonctionSalleDTO.getNom());
            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalleService.update(fonctionSalle)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "fonction_salle/{id}")
    public ResponseEntity<?> deleteFonctionSalle(@PathVariable int id) {
        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
        if (fonctionSalle != null && fonctionSalleService.delete(fonctionSalle)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// ETAGE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/etages")
    public ResponseEntity<Collection<EtageDTO>> findAllEtages() {
        return ResponseEntity.ok(etageService.findAll().stream().map(EtageDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/etage/{id}")
    public ResponseEntity<EtageDTO> findEtageById(@PathVariable int id) {
        Etage etage = etageService.findById(id);
        if (etage != null) {
            return ResponseEntity.ok(EtageDTO.create(etage));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/etage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EtageDTO> saveEtage(@Valid @RequestBody EtageDTO etageDTO) {
        Etage etage = etageService.save(new Etage(etageDTO.getNom()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(etage.getGid()).toUri();
        return ResponseEntity.created(location).body(EtageDTO.create(etage));
    }

    @PatchMapping(value = "etage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EtageDTO> updateEtage(@PathVariable int id, @Valid @RequestBody EtageDTO etageDTO) {
        Etage etage = etageService.findById(id);
        if (etage != null) {
            etage.setNom(etageDTO.getNom());
            return ResponseEntity.ok(EtageDTO.create(etageService.update(etage)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "etage/{id}")
    public ResponseEntity<?> deleteEtage(@PathVariable int id) {
        Etage etage = etageService.findById(id);
        if (etage != null && etageService.delete(etage)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// PORTE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<PorteDTO>> findAllPortes() {
        return ResponseEntity.ok(porteService.findAll().stream().map(PorteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/porte/{id}")
    public ResponseEntity<PorteDTO> findPorteById(@PathVariable int id) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            return ResponseEntity.ok(PorteDTO.create(porte));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/porte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PorteDTO> savePorte(@Valid @RequestBody PorteDTO porteDTO) {
        Porte porte = null;
        try {
            porte = porteService.save(porteService.createPorteFromPorteDTO(porteDTO));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        if (porte != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(porte.getGid()).toUri();
            return ResponseEntity.created(location).body(PorteDTO.create(porte));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping(value = "porte/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PorteDTO> updatePorte(@PathVariable int id, @Valid @RequestBody PorteDTO porteDTO) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            if (porteDTO.getSalle1() != null) {
                try {
                    porte.setSalle1(salleService.salleDTOToSalle(porteDTO.getSalle1()));
                    return ResponseEntity.ok(PorteDTO.create(porteService.save(porte)));
                } catch (ParseException e) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "porte/{id}")
    public ResponseEntity<?> deletePorte(@PathVariable int id) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// SALLE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<SalleDTO>> findAllSalles() {
        return ResponseEntity.ok(salleService.findAll().stream().map(SalleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/salle/{id}")
    public ResponseEntity<SalleDTO> findSalleById(@PathVariable int id) {
        Salle salle = salleService.findById(id);
        if (salle != null) {
            return ResponseEntity.ok(SalleDTO.create(salle));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/salle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalleDTO> saveSalle(@Valid @RequestBody SalleDTO salleDTO) {
        Porte porte = null;
        try {
            porte = porteService.save(porteService.createPorteFromPorteDTO(salleDTO));
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        if (porte != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(porte.getGid()).toUri();
            return ResponseEntity.created(location).body(SalleDTO.create(porte));
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping(value = "salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalleDTO> updateSalle(@PathVariable int id, @Valid @RequestBody SalleDTO salleDTO) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            if (salleDTO.getSalle1() != null) {
                try {
                    porte.setSalle1(salleService.salleDTOToSalle(salleDTO.getSalle1()));
                    return ResponseEntity.ok(SalleDTO.create(porteService.save(porte)));
                } catch (ParseException e) {
                    return ResponseEntity.badRequest().build();
                }
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "salle/{id}")
    public ResponseEntity<?> deleteSalle(@PathVariable int id) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    /////////////////////// ESCALIER /////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<EscalierDTO>> findAllEscaliers() {
        return ResponseEntity.ok(escalierService.findAll().stream().map(EscalierDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<EscalierDTO> findEscalierById(@PathVariable int id) {
        Escalier escalier = escalierService.findById(id);
        if (escalier != null) {
            return ResponseEntity.ok(EscalierDTO.create(escalier));
        }
        return ResponseEntity.notFound().build();
    }

}
