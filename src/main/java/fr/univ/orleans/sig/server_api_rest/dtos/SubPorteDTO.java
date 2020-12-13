package fr.univ.orleans.sig.server_api_rest.dtos;

import javax.validation.Valid;

public class SubPorteDTO {

    @Valid
    private EtageDTO etage;

    public SubPorteDTO(EtageDTO etage) {
        this.etage = etage;
    }

    public EtageDTO getEtage() {
        return etage;
    }

    public void setEtage(EtageDTO etage) {
        this.etage = etage;
    }

}
