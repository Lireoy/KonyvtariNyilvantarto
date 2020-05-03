package nyilvantarto;

/**
 *
 * @author Lireoy
 */
public class Konyv {

    private final int sorozatSzam;
    private final String kiado;
    private final String szerzo;
    private final String cim;
    private final int ertek;

    public Konyv(int sorozatSzam, String kiado, String szerzo, String cim, int ertek) {
        this.sorozatSzam = sorozatSzam;
        this.kiado = kiado;
        this.szerzo = szerzo;
        this.cim = cim;
        this.ertek = ertek;
    }

    public int getSorozatSzam() {
        return sorozatSzam;
    }

    public String getKiado() {
        return kiado;
    }

    public String getSzerzo() {
        return szerzo;
    }

    public String getCim() {
        return cim;
    }

    public int getErtek() {
        return ertek;
    }
}
