package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.*;
import fr.univ.orleans.sig.server_api_rest.services.*;
import org.locationtech.jts.geom.LineString;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    @Autowired
    private TrajetService trajetService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private QRcodeService qRcodeService;


    //////////////////////////////////////////////////////////////
    ////////////////////  UTILISATEUR ////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/utilisateurs")
    public ResponseEntity<Collection<UtilisateurDTO>> findAllUtilisateur() {
        return ResponseEntity.ok(utilisateurService.findAll().stream().map(UtilisateurDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/utilisateurs/etage/{idEtage}")
    public ResponseEntity<Collection<UtilisateurDTO>> findAllUtilisateurByEtage(@PathVariable int idEtage) {
        Etage etage = etageService.findById(idEtage);
        if (etage != null) {
            return ResponseEntity.ok(utilisateurService.findAllUtilisateurByEtage(etage).stream().map(UtilisateurDTO::create).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/utilisateur/{username}")
    public ResponseEntity<UtilisateurDTO> findUtilisateurById(@PathVariable String username) {
        Utilisateur utilisateur = utilisateurService.findById(username);
        if (utilisateur != null) {
            return ResponseEntity.ok(UtilisateurDTO.create(utilisateur));
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/utilisateur", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDTO> saveUtilisateur(@Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        if (utilisateurService.conflict(utilisateurDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Utilisateur utilisateur = null;
        try {
            utilisateur = utilisateurService.save(utilisateurService.createUtilisateurFromUtilisateurDTO(utilisateurDTO));
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if (utilisateur != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{username}")
                    .buildAndExpand(utilisateur.getUsername()).toUri();
            return ResponseEntity.created(location).body(UtilisateurDTO.create(utilisateur));
        }
        return ResponseEntity.badRequest().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping(value = "/utilisateur/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(@PathVariable String username,
                                                            @Valid @RequestBody UtilisateurDTO utilisateurDTO) {
        Utilisateur utilisateur = utilisateurService.findById(username);
        if (utilisateur != null) {
            if (utilisateurDTO.getEtage() != null && utilisateurDTO.getPosition() != null && utilisateurDTO.getDateDernierScan() != null) {
                try {
                    utilisateur.setPosition(SuperService.pointDTOToPoint(utilisateurDTO.getPosition()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return ResponseEntity.badRequest().build();
                }
                utilisateur.setEtage(etageService.etageDTOToEtage(utilisateurDTO.getEtage()));
                utilisateur.setDateDernierScan(utilisateurDTO.getDateDernierScan());
                return ResponseEntity.ok(UtilisateurDTO.create(utilisateurService.save(utilisateur)));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    //////////////////////  QR CODE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/qrcodes")
    public ResponseEntity<Collection<QRcodeDTO>> findAllQrCodes() {
        return ResponseEntity.ok(qRcodeService.findAll().stream().map(QRcodeDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/qrcode/{text}")
    public ResponseEntity<QRcodeDTO> findQrCodesById(@PathVariable String text) {
        QRcode qRcode = qRcodeService.findById(text);
        if (qRcode != null) {
            return ResponseEntity.ok(QRcodeDTO.create(qRcode));
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/qrcodes/etage/{idEtage}")
    public ResponseEntity<Collection<QRcodeDTO>> findAllQRCodesByEtage(@PathVariable int idEtage) {
        Etage etage = etageService.findById(idEtage);
        if (etage != null) {
            return ResponseEntity.ok(qRcodeService.findAllQRCOdeByEtage(etage).stream().map(QRcodeDTO::create).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(value = "/qrcode")
    public ResponseEntity<QRcodeDTO> saveQRCode(@Valid @RequestBody QRcodeDTO qRcodeDTO) {
        try {
            if (qRcodeService.conflict(qRcodeDTO.getPosition(), qRcodeDTO.getEtage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            ResponseEntity.badRequest().build();
        }
        QRcode qRcode = null;
        try {
            qRcode = qRcodeService.save(qRcodeService.createQRCodeFromQRCodeDTO(qRcodeDTO));
        } catch (ParseException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        if (qRcode != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(qRcode.getText()).toUri();
            return ResponseEntity.created(location).body(QRcodeDTO.create(qRcode));
        }
        return ResponseEntity.badRequest().build();
    }

    //////////////////////////////////////////////////////////////
    //////////////////// FONCTION_SALLE //////////////////////////
    //////////////////////////////////////////////////////////////

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        return ResponseEntity.ok(
                fonctionSalleService.findAll().stream().map(FonctionSalleDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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
//    @PatchMapping(value = "/fonction_salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<FonctionSalleDTO> updateFonctionSalle(@PathVariable int id, @Valid @RequestBody FonctionSalleDTO fonctionSalleDTO) {
//        FonctionSalle fonctionSalle = fonctionSalleService.findById(id);
//        if (fonctionSalle != null) {
//            fonctionSalle.setNom(fonctionSalleDTO.getNom());
//            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalleService.update(fonctionSalle)));
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @CrossOrigin(origins = "http://localhost:1234")
//    @DeleteMapping(value = /"fonction_salle/{id}")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/etages")
    public ResponseEntity<Collection<EtageDTO>> findAllEtages() {
        return ResponseEntity.ok(etageService.findAll().stream().map(EtageDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<PorteDTO>> findAllPortes() {
        return ResponseEntity.ok(porteService.findAll().stream().map(PorteDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/portes/etage/{idEtage}")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<SalleDTO>> findAllSalles() {
        return ResponseEntity.ok(salleService.findAll().stream().map(SalleDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PatchMapping(value = "/salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SalleDTO> updateSalle(@PathVariable int id, @Valid @RequestBody SalleDTO salleDTO) {
        Salle salle = salleService.findById(id);
        if (salle != null) {
            boolean set = false;
            if (salleService.findSalleByNom(salleDTO.getProperties().getNom()) == null) {
                salle.setNom(salleDTO.getProperties().getNom());
                set = true;
            }
            if (fonctionSalleService.conflict(salleDTO.getProperties().getFonction().getNom())) {
                salle.setFonction(fonctionSalleService.findByNom(salleDTO.getProperties().getFonction().getNom()));
                set = true;
            }
            if (set) {
                return ResponseEntity.ok(SalleDTO.create(salleService.save(salle)));
            } else {
                return ResponseEntity.badRequest().build();
            }
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/salles/etage/{idEtage}")
    public ResponseEntity<Collection<SalleDTO>> findAllSalleByEtage(@PathVariable int idEtage) {
        Etage etage = etageService.findById(idEtage);
        if (etage != null) {
            return ResponseEntity.ok(salleService.findAllSalleByEtage(etage).stream().map(SalleDTO::create).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/salle/nom/{nom}")
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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<EscalierDTO>> findAllEscaliers() {
        return ResponseEntity.ok(escalierService.findAll().stream().map(EscalierDTO::create).collect(Collectors.toList()));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

//    @CrossOrigin(origins = "*", allowedHeaders = "*")
//    @GetMapping(value = "/trajet/porteDepart/{idPorte}/salle/{idSalle}")
//    public ResponseEntity<Collection<LineStringDTO>> findTrajet(@PathVariable int idPorte, @PathVariable int idSalle) {
//        ArrayList<LineStringDTO> trajets = new ArrayList<>();
//        Porte porteDepart = porteService.findById(idPorte);
//        Salle salleArrivee = salleService.findById(idSalle);
//        if (porteDepart != null && salleArrivee != null) {
//            Map<LineString, Etage> etapes = trajetService.findEtageTrajet(porteDepart, salleArrivee);
//            for (LineString lineString : etapes.keySet()) {
//                trajets.add(LineStringDTO.create(lineString, etapes.get(lineString)));
//            }
//            return ResponseEntity.ok(trajets);
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/trajet/porteDepart/{idPorte}/salle/{idSalle}")
    public ResponseEntity<Collection<LineStringDTO>> findTrajet(@PathVariable int idPorte, @PathVariable int idSalle) {
        Porte porteDepart = porteService.findById(idPorte);
        Salle salleArrivee = salleService.findById(idSalle);
        try {
            ArrayList<LineString> lineStrings = (ArrayList<LineString>) trajetService.pathFinding(porteDepart, salleArrivee);
            ArrayList<LineStringDTO> lineStringsDTO = new ArrayList<>();
            lineStringsDTO.add(LineStringDTO.create(lineStrings.get(0), porteDepart.getSalle1().getEtage()));
            if (lineStrings.size() > 1) {
                lineStringsDTO.add(LineStringDTO.create(lineStrings.get(1), salleArrivee.getEtage()));
            }
            return ResponseEntity.ok(lineStringsDTO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

}
