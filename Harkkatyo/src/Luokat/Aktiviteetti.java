package Luokat;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Harjoitustyön luokka - aktiviteetti
 * @author Pekka
 * @version 25.2.2021
 *
 */
public class Aktiviteetti {

    private String Pvm; //tämä päivä vakio, erikseen jos kirjataan aikaisempien päivien tapahtumia
    private String Kategoria; //Urheilu tai Ravinto
    
    /**
     * tulostus metodi, saadaan tiedot haluttuun paikkaan.
     * @param out mikä formaatti, jona tulostaa
     */
    public void tulosta(PrintStream out) {
        out.println(Pvm + " " + Kategoria);
    }
    
    /**
     * toinen tulostus - apumetodi
     * @param os muuttaa oikeaksi tyypiksi
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Metodi, joka täyttää tiedot aktiviteettiin
     */
    private void taytatiedot() {
          this.Pvm="";
          this.Kategoria="";
    }
    
    /**
     * Pääohjelma
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Aktiviteetti aktiviteetti = new Aktiviteetti();
        aktiviteetti.taytatiedot();
        aktiviteetti.tulosta(System.out);
    }



}
