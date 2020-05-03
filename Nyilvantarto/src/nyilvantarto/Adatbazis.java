/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyilvantarto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lireoy
 */
public class Adatbazis {

    private final String nev;
    private final String felhasznalo;
    private final String jelszo;

    public Adatbazis(String cim, String felhasznalo, String jelszo) {
        this.nev = "jdbc:mysql://localhost/" + cim;
        this.felhasznalo = felhasznalo;
        this.jelszo = jelszo;
    }

    private Connection kapcsolodas() throws SQLException {
        return DriverManager.getConnection(nev, felhasznalo, jelszo);
    }

    private void utasitasVegrehajtas(String sql) throws SQLException {
        Statement utasitas = kapcsolodas().createStatement();
        utasitas.execute(sql);
    }

    private ResultSet lekerdezesVegrehajtas(String sql) throws SQLException {
        Statement utasitas = kapcsolodas().createStatement();
        return utasitas.executeQuery(sql);
    }

    public ArrayList osszesTag() {
        try {
            String sql = "SELECT * FROM `tagok` ";
            ResultSet valasz = lekerdezesVegrehajtas(sql);

            ArrayList<Tag> tagok = new ArrayList<>();

            while (valasz.next()) {
                tagok.add(
                        new Tag(
                                valasz.getInt("id"),
                                valasz.getString("nev"),
                                valasz.getString("telefon"),
                                valasz.getString("lakcim")));
            }

            return tagok;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList osszesKonyv() {
        try {
            String sql = "SELECT * FROM `konyvek` ";
            ResultSet valasz = lekerdezesVegrehajtas(sql);

            ArrayList<Konyv> konyvek = new ArrayList<>();

            while (valasz.next()) {
                konyvek.add(
                        new Konyv(
                                valasz.getInt("sorozatszam"),
                                valasz.getString("kiado"),
                                valasz.getString("szerzo"),
                                valasz.getString("cim"),
                                valasz.getInt("ertek")));
            }

            return konyvek;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public ArrayList osszesKolcsonzes() {
        try {
            String sql = "SELECT * FROM `kolcsonzesek` ";
            ResultSet valasz = lekerdezesVegrehajtas(sql);

            ArrayList<Kolcsonzes> kolcsonzesek = new ArrayList<>();

            while (valasz.next()) {
                kolcsonzesek.add(
                        new Kolcsonzes(
                                valasz.getInt("id"),
                                valasz.getInt("konyv"),
                                valasz.getInt("tag"),
                                valasz.getTimestamp("idopont")));
            }

            return kolcsonzesek;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean tagTorles(int azonosito) {
        try {
            String ellenorzes = "SELECT COUNT(id) AS darab FROM `kolcsonzesek` WHERE kolcsonzesek.tag = " + azonosito;
            ResultSet valasz = lekerdezesVegrehajtas(ellenorzes);
            valasz.next();

            int darab = valasz.getInt("darab");

            if (darab != 0) {
                return false;
            }

            String sql = "DELETE FROM `tagok` WHERE tagok.id = " + azonosito;
            utasitasVegrehajtas(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean konyvTorles(int azonosito) {
        try {
            String ellenorzes = "SELECT COUNT(id) AS darab FROM `kolcsonzesek` WHERE kolcsonzesek.konyv = " + azonosito;
            ResultSet valasz = lekerdezesVegrehajtas(ellenorzes);
            valasz.next();
            int darab = valasz.getInt("darab");

            if (darab != 0) {
                return false;
            }

            String sql = "DELETE FROM `konyvek` WHERE konyvek.sorozatszam = " + azonosito;
            utasitasVegrehajtas(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean kolcsonzesTorles(int azonosito) {
        try {
            String sql = "DELETE FROM `kolcsonzesek` WHERE kolcsonzesek.id = " + azonosito;
            utasitasVegrehajtas(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public void tagFelvitel(String nev, String telefon, String lakcim) {
        try {
            String sql = "INSERT INTO `tagok` (`id`, `nev`, `telefon`, `lakcim`)"
                    + " VALUES (NULL,"
                    + " '" + nev + "', '" + telefon + "', '" + lakcim + "'); ";

            utasitasVegrehajtas(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void konyvFelvitel(String sorozatszam, String kiado, String szerzo, String cim, String ertek) {
        try {
            String sql = "INSERT INTO `konyvek` (`sorozatszam`, `kiado`, `szerzo`, `cim`, `ertek`)"
                    + " VALUES ('" + sorozatszam + "',"
                    + " '" + kiado + "', '" + szerzo + "',"
                    + " '" + cim + "', '" + ertek + "'); ";

            utasitasVegrehajtas(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean kolcsonzesFelvitel(int konyvSorozatszam, int tagId) {
        try {
            String konyvEllenorzes = "SELECT COUNT(kolcsonzesek.id) AS darab"
                    + " FROM `kolcsonzesek` WHERE kolcsonzesek.konyv = " + konyvSorozatszam;
            ResultSet konyvValasz = lekerdezesVegrehajtas(konyvEllenorzes);
            konyvValasz.next();
            if (konyvValasz.getInt("darab") != 0) {
                return false;
            }

            
            String tagEllenorzes = "SELECT COUNT(kolcsonzesek.id) AS darab"
                    + " FROM `kolcsonzesek` WHERE kolcsonzesek.tag = " + tagId;
            ResultSet tagValasz = lekerdezesVegrehajtas(tagEllenorzes);
            tagValasz.next();
            if (konyvValasz.getInt("darab") >= 3) {
                return false;
            }

            String sql = "INSERT INTO `kolcsonzesek` (`id`, `konyv`, `tag`, `idopont`)"
                    + " VALUES (NULL, '" + konyvSorozatszam + "',"
                    + " '" + tagId + "', current_timestamp()); ";

            utasitasVegrehajtas(sql);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Adatbazis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
