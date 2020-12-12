package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.*;
import fr.univ.orleans.sig.server_api_rest.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:1234")
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

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        FonctionSalle fonctionSalle = new FonctionSalle(null);
        return ResponseEntity.ok(
                fonctionSalleService.findAll().stream().map(FonctionSalleDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/fonction_salle/{id}")
    public ResponseEntity<FonctionSalleDTO> findFonctionSalleById(@PathVariable int id) {
        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
        if (fonctionSalle != null) {
            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalle));
        }
        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PostMapping(value = "/fonction_salle", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<FonctionSalleDTO> saveFonctionSalle(@Valid @RequestBody FonctionSalleDTO fonctionSalleDTO) {
//        if (fonctionSalleService.conflict(fonctionSalleDTO.getNom())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//        FonctionSalle fonctionSalle = fonctionSalleService.save(new FonctionSalle(fonctionSalleDTO.getNom()));
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(fonctionSalle.getGid()).toUri();
//        return ResponseEntity.created(location).body(FonctionSalleDTO.create(fonctionSalle));
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PatchMapping(value = "fonction_salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<FonctionSalleDTO> updateFonctionSalle(@PathVariable int id, @Valid @RequestBody FonctionSalleDTO fonctionSalleDTO) {
//        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
//        if (fonctionSalle != null) {
//            fonctionSalle.setNom(fonctionSalleDTO.getNom());
//            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalleService.update(fonctionSalle)));
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @DeleteMapping(value = "fonction_salle/{id}")
//    public ResponseEntity<?> deleteFonctionSalle(@PathVariable int id) {
//        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
//        if (fonctionSalle != null && fonctionSalleService.delete(fonctionSalle)) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// ETAGE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/etages")
    public ResponseEntity<Collection<EtageDTO>> findAllEtages() {
        return ResponseEntity.ok(etageService.findAll().stream().map(EtageDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/etage/{id}")
    public ResponseEntity<EtageDTO> findEtageById(@PathVariable int id) {
        Etage etage = etageService.findById(id);
        if (etage != null) {
            return ResponseEntity.ok(EtageDTO.create(etage));
        }
        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PostMapping(value = "/etage", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EtageDTO> saveEtage(@Valid @RequestBody EtageDTO etageDTO) {
//        Etage etage = etageService.save(new Etage(etageDTO.getNom()));
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest().path("/{id}")
//                .buildAndExpand(etage.getGid()).toUri();
//        return ResponseEntity.created(location).body(EtageDTO.create(etage));
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PatchMapping(value = "etage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<EtageDTO> updateEtage(@PathVariable int id, @Valid @RequestBody EtageDTO etageDTO) {
//        Etage etage = etageService.findById(id);
//        if (etage != null) {
//            etage.setNom(etageDTO.getNom());
//            return ResponseEntity.ok(EtageDTO.create(etageService.update(etage)));
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @DeleteMapping(value = "etage/{id}")
//    public ResponseEntity<?> deleteEtage(@PathVariable int id) {
//        Etage etage = etageService.findById(id);
//        if (etage != null && etageService.delete(etage)) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// PORTE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<PorteDTO>> findAllPortes() {
        return ResponseEntity.ok(porteService.findAll().stream().map(PorteDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/porte/{id}")
    public ResponseEntity<PorteDTO> findPorteById(@PathVariable int id) {
        Porte porte = porteService.findById(id);
        if (porte != null) {
            return ResponseEntity.ok(PorteDTO.create(porte));
        }
        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PostMapping(value = "/porte", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PorteDTO> savePorte(@Valid @RequestBody PorteDTO porteDTO) {
//        Porte porte = null;
//        try {
//            porte = porteService.save(porteService.createPorteFromPorteDTO(porteDTO));
//        } catch (ParseException e) {
//            return ResponseEntity.badRequest().build();
//        }
//        if (porte != null) {
//            URI location = ServletUriComponentsBuilder
//                    .fromCurrentRequest().path("/{id}")
//                    .buildAndExpand(porte.getGid()).toUri();
//            return ResponseEntity.created(location).body(PorteDTO.create(porte));
//        }
//        return ResponseEntity.badRequest().build();
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PatchMapping(value = "porte/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<PorteDTO> updatePorte(@PathVariable int id, @Valid @RequestBody PorteDTO porteDTO) {
//        Porte porte = porteService.findById(id);
//        if (porte != null) {
//            if (porteDTO.getSalle1() != null) {
//                try {
//                    porte.setSalle1(salleService.salleDTOToSalle(porteDTO.getSalle1()));
//                } catch (ParseException e) {
//                    return ResponseEntity.badRequest().build();
//                }
//            }
//            if (porteDTO.getSalle2() != null) {
//                try {
//                    porte.setSalle2(salleService.salleDTOToSalle(porteDTO.getSalle2()));
//                } catch (ParseException e) {
//                    return ResponseEntity.badRequest().build();
//                }
//            }
////            if (porteDTO.getGeometry() != null) {
////                try {
////                    porte.setGeom(porteDTO.getGeometry());
////                } catch (ParseException e) {
////                    return ResponseEntity.badRequest().build();
////                }
////            }
//            return ResponseEntity.ok(PorteDTO.create(porteService.save(porte)));
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @DeleteMapping(value = "porte/{id}")
//    public ResponseEntity<?> deletePorte(@PathVariable int id) {
//        Porte porte = porteService.findById(id);
//        if (porte != null) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "portes/etage/{idEtage}")
    public ResponseEntity<Collection<PorteDTO>> findAllPorteByEtage(@PathVariable int idEtage) {
        Etage etage = etageService.findById(idEtage);
        if (etage != null) {
            return ResponseEntity.ok(porteService.findAllPorteByEtage(etage).stream().map(PorteDTO::create).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// SALLE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<SalleDTO>> findAllSalles() {
        return ResponseEntity.ok(salleService.findAll().stream().map(SalleDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/salle/{id}")
    public ResponseEntity<SalleDTO> findSalleById(@PathVariable int id) {
        Salle salle = salleService.findById(id);
        if (salle != null) {
            return ResponseEntity.ok(SalleDTO.create(salle));
        }
        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @PostMapping(value = "/salle", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<SalleDTO> saveSalle(@Valid @RequestBody SalleDTO salleDTO) {
////        Salle salle = null;
////        try {
////            salle = salleService.save(salleService.createSalleFromSalleDTO(salleDTO));
////        } catch (ParseException e) {
////            return ResponseEntity.badRequest().build();
////        }
////        if (salle != null) {
////            URI location = ServletUriComponentsBuilder
////                    .fromCurrentRequest().path("/{id}")
////                    .buildAndExpand(salle.getGid()).toUri();
////            return ResponseEntity.created(location).body(SalleDTO.create(salle));
////        }
//        return ResponseEntity.badRequest().build();
//    }

    @CrossOrigin(origins = "http://localhost:1234")
    @PatchMapping(value = "salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalleDTO> updateSalle(@PathVariable int id, @Valid @RequestBody SalleDTO salleDTO) {
        Salle salle = salleService.findById(id);
        if (salle != null) {
            if (salleService.findSalleByNom(salleDTO.getNom()) == null) {
                salle.setNom(salleDTO.getNom());
            }
            if (!fonctionSalleService.conflict(salleDTO.getFonction().getNom())) {
                salle.setFonction(fonctionSalleService.findByNom(salleDTO.getFonction().getNom()));
            }
            return ResponseEntity.ok(SalleDTO.create(salleService.save(salle)));
        }
        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @DeleteMapping(value = "salle/{id}")
//    public ResponseEntity<?> deleteSalle(@PathVariable int id) {
//        Porte porte = porteService.findById(id);
//        if (porte != null) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "salles/etage/{idEtage}")
    public ResponseEntity<Collection<SalleDTO>> findAllSalleByEtage(@PathVariable int idEtage) {
        Etage etage = etageService.findById(idEtage);
        if (etage != null) {
            return ResponseEntity.ok(salleService.findAllSalleByEtage(etage).stream().map(SalleDTO::create).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "salle/nom/{nom}")
    public ResponseEntity<SalleDTO> findSalleByNumero(@PathVariable String nom) {
        Salle salle = salleService.findSalleByNom(nom);
        if (salle != null) {
            return ResponseEntity.ok(SalleDTO.create(salle));
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    /////////////////////// ESCALIER /////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "/")
    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<EscalierDTO>> findAllEscaliers() {
        return ResponseEntity.ok(escalierService.findAll().stream().map(EscalierDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<EscalierDTO> findEscalierById(@PathVariable int id) {
        Escalier escalier = escalierService.findById(id);
        if (escalier != null) {
            return ResponseEntity.ok(EscalierDTO.create(escalier));
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// AUTRES /////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "http://localhost:1234")
    @GetMapping(value = "trajet/porteDepart/{idPorte}/salle/{idSalle}")
    public ResponseEntity<Collection<TrajetDTO>> findTrajet(@PathVariable int idPorte, @PathVariable int idSalle) {
        ArrayList<TrajetDTO> trajets = new ArrayList<>();
        Porte porteDepart = porteService.findById(idPorte);
        Salle salleArrivee = salleService.findById(idSalle);
        if (porteDepart != null && salleArrivee != null) {
            trajets.add(PorteDTO.create(porteDepart));
            if (porteDepart.getSalle1().getEtage().getGid() == salleArrivee.getEtage().getGid()) {
                Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
                for (Salle salle : salles) {

                    if (salle.getGid() == salleArrivee.getGid()) {
//                        trajets.add(PorteDTO.create(porteService.findPorteBySalle(salle, salleService.)));
                    }
                }
            } else {

            }
            return ResponseEntity.ok(trajets);
        }
        return ResponseEntity.badRequest().build();
    }

}
