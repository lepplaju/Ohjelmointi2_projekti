package Luokat;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Harjoitusty�n luokka - aktiviteetti
 * @author Pekka
 * @version 25.2.2021
 *
 */
public class Aktiviteetti {

    private String Pvm; //t�m� p�iv� vakio, erikseen jos kirjataan aikaisempien p�ivien tapahtumia
    private String Kategoria; //Urheilu tai Ravinto
    
    /**
     * tulostus metodi, saadaan tiedot haluttuun paikkaan.
     * @param out mik� formaatti, jona tulostaa
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
     * Metodi, joka t�ytt�� tiedot aktiviteettiin
     */
    private void taytatiedot() {
          this.Pvm="";
          this.Kategoria="";
    }
    
    /**
     * P��ohjelma
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Aktiviteetti aktiviteetti = new Aktiviteetti();
        aktiviteetti.taytatiedot();
        aktiviteetti.tulosta(System.out);
    }



}
