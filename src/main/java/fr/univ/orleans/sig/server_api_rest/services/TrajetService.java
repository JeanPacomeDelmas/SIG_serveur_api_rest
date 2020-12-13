package fr.univ.orleans.sig.server_api_rest.services;

import fr.univ.orleans.sig.server_api_rest.dtos.EscalierDTO;
import fr.univ.orleans.sig.server_api_rest.dtos.PorteDTO;
import fr.univ.orleans.sig.server_api_rest.entities.Escalier;
import fr.univ.orleans.sig.server_api_rest.entities.Porte;
import fr.univ.orleans.sig.server_api_rest.entities.Salle;
import fr.univ.orleans.sig.server_api_rest.entities.Trajet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TrajetService {

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

    public Collection<Trajet> findTrajet(Porte porteDepart, Salle salleArrivee) {
        ArrayList<Trajet> trajets = new ArrayList<>();
//        trajets.add(porteDepart);
//        if (porteDepart.getSalle1().getEtage().getGid() != salleArrivee.getEtage().getGid()) {
//            for (Escalier escalier :
//                    escalierService.escalierToEscalier(porteDepart.getSalle1().getEtage(), salleArrivee.getEtage())) {
//                trajets.add(EscalierDTO.create(escalier));
//            }
//        }
//        Collection<Salle> salles = salleService.findAllSalleByEtage(salleArrivee.getEtage());
//        for (Salle salle : salles) {
//            if (salle.getGid() == salleArrivee.getGid()) {
//                trajets.add(PorteDTO.create(porteService.findPorteBySalle(salle,
//                        salleService.findSalleByEtageAndFonctionCouloir(salle.getEtage(),
//                                fonctionSalleService.findByNom("couloir")))));
//            }
//        }
        return trajets;
    }

}
