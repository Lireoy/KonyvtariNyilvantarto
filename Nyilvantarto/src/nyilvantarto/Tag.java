package nyilvantarto;

/**
 *
 * @author Lireoy
 */
public class Tag {

    private final int id;
    private final String nev;
    private final String telefon;
    private final String lakcim;

    public Tag(int id, String nev, String telefon, String lakcim) {
        this.id = id;
        this.nev = nev;
        this.telefon = telefon;
        this.lakcim = lakcim;
    }

    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getLakcim() {
        return lakcim;
    }
}
