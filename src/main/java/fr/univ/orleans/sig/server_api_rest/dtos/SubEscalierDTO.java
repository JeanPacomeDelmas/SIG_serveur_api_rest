package fr.univ.orleans.sig.server_api_rest.dtos;

public class SubEscalierDTO {

    private EtageDTO etageB;
    private EtageDTO etageH;

    public SubEscalierDTO(EtageDTO etageB, EtageDTO etageH) {
        this.etageB = etageB;
        this.etageH = etageH;
    }

    public EtageDTO getEtageB() {
        return etageB;
    }

    public void setEtageB(EtageDTO etageB) {
        this.etageB = etageB;
    }

    public EtageDTO getEtageH() {
        return etageH;
    }

    public void setEtageH(EtageDTO etageH) {
        this.etageH = etageH;
    }

}
