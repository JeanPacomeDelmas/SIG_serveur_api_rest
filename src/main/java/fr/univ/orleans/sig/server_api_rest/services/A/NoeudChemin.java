package fr.univ.orleans.sig.server_api_rest.services.A;

class NoeudChemin<T extends NoeudGraphe> implements Comparable<NoeudChemin<T>> {

    private final T courant;
    private T precedent;
    private double scoreRoute;
    private double scoreEstime;

    public NoeudChemin(T courant) {
        this(courant, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public NoeudChemin(T courant, T precedent, double scoreRoute, double scoreEstime) {
        this.courant = courant;
        this.precedent = precedent;
        this.scoreRoute = scoreRoute;
        this.scoreEstime = scoreEstime;
    }

    @Override
    public int compareTo(NoeudChemin other) {
        if (this.scoreEstime > other.scoreEstime) {
            return 1;
        } else if (this.scoreEstime < other.scoreEstime) {
            return -1;
        } else {
            return 0;
        }
    }

    public T getCourant() {
        return courant;
    }

    public T getPrecedent() {
        return precedent;
    }

    public void setPrecedent(T precedent) {
        this.precedent = precedent;
    }

    public double getScoreRoute() {
        return scoreRoute;
    }

    public void setScoreRoute(double scoreRoute) {
        this.scoreRoute = scoreRoute;
    }

    public double getScoreEstime() {
        return scoreEstime;
    }

    public void setScoreEstime(double scoreEstime) {
        this.scoreEstime = scoreEstime;
    }
}
