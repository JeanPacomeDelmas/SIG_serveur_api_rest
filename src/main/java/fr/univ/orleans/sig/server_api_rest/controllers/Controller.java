package fr.univ.orleans.sig.server_api_rest.controllers;

import fr.univ.orleans.sig.server_api_rest.dtos.*;
import fr.univ.orleans.sig.server_api_rest.entities.Etage;
import fr.univ.orleans.sig.server_api_rest.entities.FonctionSalle;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.repositories.*;
import fr.univ.orleans.sig.server_api_rest.services.GenericService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
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

    //////////////////////////////////////////////////////////////
    //////////////////// FONCTION_SALLE //////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/fonction_salles")
    public ResponseEntity<Collection<FonctionSalleDTO>> findAllFonctionSalles() {
        return ResponseEntity.ok(fonctionSalleRepository.findAll().stream().map(FonctionSalleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/fonction_salle/{id}")
    public ResponseEntity<FonctionSalleDTO> findFonctionSalleById(@PathVariable int id) {
        return fonctionSalleRepository.findById(id).map(value -> ResponseEntity.ok(FonctionSalleDTO.create(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/fonction_salle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> saveFonctionSalle(@RequestBody FonctionSalleDTO fonctionSalleDTO) {
        if (fonctionSalleRepository.existsByNom(fonctionSalleDTO.getNom())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        FonctionSalle fonctionSalle = fonctionSalleRepository.save(new FonctionSalle(fonctionSalleDTO.getNom()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(fonctionSalle.getGid()).toUri();
        return ResponseEntity.created(location).body(FonctionSalleDTO.create(fonctionSalle));
    }

    @PatchMapping(value = "fonction_salle/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FonctionSalleDTO> updateFonctionSalle(@PathVariable int id, @RequestBody FonctionSalleDTO fonctionSalleDTO) {
        Optional<FonctionSalle> fonctionSalle = fonctionSalleRepository.findById(id);
        if (fonctionSalle.isPresent()) {
            fonctionSalle.get().setNom(fonctionSalleDTO.getNom());
            return ResponseEntity.ok(FonctionSalleDTO.create(fonctionSalleRepository.save(fonctionSalle.get())));
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
        return ResponseEntity.ok(etageRepository.findAll().stream().map(EtageDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/etage/{id}")
    public ResponseEntity<EtageDTO> findEtageById(@PathVariable int id) {
        return etageRepository.findById(id).map(value -> ResponseEntity.ok(EtageDTO.create(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/etage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EtageDTO> saveEtage(@RequestBody EtageDTO etageDTO) {
        Etage etage = etageRepository.save(new Etage(etageDTO.getNom()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(etage.getGid()).toUri();
        return ResponseEntity.created(location).body(EtageDTO.create(etage));
    }

    @PatchMapping(value = "etage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EtageDTO> updateEtage(@PathVariable int id, @RequestBody EtageDTO etageDTO) {
        Optional<Etage> etage = etageRepository.findById(id);
        if (etage.isPresent()) {
            etage.get().setNom(etageDTO.getNom());
            return ResponseEntity.ok(EtageDTO.create(etageRepository.save(etage.get())));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "etage/{id}")
    public ResponseEntity<?> deleteEtage(@PathVariable int id) {
        Optional<Etage> etage = etageRepository.findById(id);
        if (etage.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// PORTE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/portes")
    public ResponseEntity<Collection<PorteDTO>> findAllPortes() {
        return ResponseEntity.ok(porteRepository.findAll().stream().map(PorteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/porte/{id}")
    public ResponseEntity<PorteDTO> findPorteById(@PathVariable int id) {
        return porteRepository.findById(id).map(value -> ResponseEntity.ok(PorteDTO.create(value))).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/porte", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PorteDTO> savePorte(@RequestBody PorteDTO porteDTO) {
//        try {
//            Salle salle1 = salleRepository.findByGeomAndEtageAndNumeroAndFonction(
//                    (Polygon) GenericService.wktToGeometry(porteDTO.getSalle1().getGeometry()),
//                    etageRepository.findByNom(porteDTO.getSalle1().getEtage().getNom()),
//                    porteDTO.getSalle1().getNumero(),
//                    fonctionSalleRepository.findByNom(porteDTO.getSalle1().getFonction().getNom()));
//            Salle salle2 = salleRepository.findByGeomAndEtageAndNumeroAndFonction(
//                    (Polygon) GenericService.wktToGeometry(porteDTO.getSalle2().getGeometry()),
//                    etageRepository.findByNom(porteDTO.getSalle2().getEtage().getNom()),
//                    porteDTO.getSalle2().getNumero(),
//                    fonctionSalleRepository.findByNom(porteDTO.getSalle2().getFonction().getNom()));
//            LineString geom = (LineString) GenericService.wktToGeometry(porteDTO.getGeom());
//            if (salle1 != null && salle2 != null && geom != null) {
//                if (!porteRepository.existsBySalle1AndSalle2AndGeom(salle1, salle2, geom)) {
//                    Porte porte = porteRepository.save(new Porte(salle1, salle2, geom));
//                    URI location = ServletUriComponentsBuilder
//                            .fromCurrentRequest().path("/{id}")
//                            .buildAndExpand(porte.getGid()).toUri();
//                    return ResponseEntity.created(location).body(PorteDTO.create(porte));
//                }
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PatchMapping(value = "porte/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PorteDTO> updatePorte(@PathVariable int id, @RequestBody PorteDTO porteDTO) {
        Optional<Porte> porte = porteRepository.findById(id);
        if (porte.isPresent()) {
//            if (porteDTO.getSalle1() != null) porte.get().setSalle1(porteDTO.getSalle1());
//            return ResponseEntity.ok(PorteDTO.create(etageRepository.save(porte.get())));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "porte/{id}")
    public ResponseEntity<?> deletePorte(@PathVariable int id) {
        Optional<Etage> etage = etageRepository.findById(id);
        if (etage.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //////////////////////////////////////////////////////////////
    ///////////////////////// SALLE //////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/salles")
    public ResponseEntity<Collection<SalleDTO>> findAllSalles() {
        return ResponseEntity.ok(salleRepository.findAll().stream().map(SalleDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/salle/{id}")
    public ResponseEntity<SalleDTO> findSalleById(@PathVariable int id) {
//        Optional<Salle> salle = salleRepository.findById(id);
//        if (salle.isPresent()) {
//            ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
//            for (Coordinate coordinate : salle.get().getGeom().getCoordinates()) {
//                ArrayList<Double> coords = new ArrayList<>(Arrays.asList(coordinate.x, coordinate.y));
//                coordinates.add(coords);
//            }
//            Map<String, Object> json = new HashMap<>();
//            json.put("fonction", salle.get().getFonction());
//            json.put("numero", salle.get().getNumero());
//            json.put("etage", salle.get().getEtage());
//            json.put("geometry", new HashMap<String, Object>() {{
//                put("coordinates", new ArrayList<ArrayList<ArrayList<Double>>>(Collections.singletonList(coordinates)));
//                put("type", "Polygon");
//            }});
//            json.put("type", "Feature");
//            json.put("gid", id);
//            return ResponseEntity.ok(json);
//        }
//        return ResponseEntity.notFound().build();
        return salleRepository.findById(id).map(value -> ResponseEntity.ok(SalleDTO.create(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //////////////////////////////////////////////////////////////
    /////////////////////// ESCALIER /////////////////////////////
    //////////////////////////////////////////////////////////////

    @GetMapping(value = "/escaliers")
    public ResponseEntity<Collection<EscalierDTO>> findAllEscaliers() {
        return ResponseEntity.ok(escalierRepository.findAll().stream().map(EscalierDTO::create).collect(Collectors.toList()));
    }

    @GetMapping(value = "/escalier/{id}")
    public ResponseEntity<EscalierDTO> findEscalierById(@PathVariable int id) {
        return escalierRepository.findById(id).map(value -> ResponseEntity.ok(EscalierDTO.create(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
