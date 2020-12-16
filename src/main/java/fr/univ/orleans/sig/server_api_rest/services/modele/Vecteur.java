package fr.univ.orleans.sig.server_api_rest.services.modele;

public class Vecteur {

    private final double x;
    private final double y;

    public Vecteur(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
