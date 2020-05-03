package nyilvantarto;

import java.sql.Timestamp;

/**
 *
 * @author Lireoy
 */
public class Kolcsonzes {

    private final int id;
    private final int konyv;
    private final int tag;
    private final Timestamp idopont;

    public Kolcsonzes(int id, int konyv, int tag, Timestamp idopont) {
        this.id = id;
        this.konyv = konyv;
        this.tag = tag;
        this.idopont = idopont;
    }

    public int getId() {
        return id;
    }

    public int getKonyv() {
        return konyv;
    }

    public int getTag() {
        return tag;
    }

    public Timestamp getIdopont() {
        return idopont;
    }
}
