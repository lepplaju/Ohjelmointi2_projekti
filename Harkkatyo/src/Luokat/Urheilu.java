package Luokat;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Harjoitusty�n Urheilu-luokka aktiviteetin alalaji
 * @author Pekka
 * @version 25.2.2021
 *
 */
public class Urheilu {

    private String Pvm;
    private String Klo;
    
    private String Laji; // Urheilu // J��kiekko
    private String Suoritusaika;
    private double Intensity;
    private double Tyytyv�isyys;
    
    
    public void tulosta(PrintStream out) {
        out.println(Pvm + " " + Klo + " " + Laji  + " " + Suoritusaika  + " " +
    Intensity  + " " + Tyytyv�isyys);
    }
    
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    public void taytatiedot() {
        Pvm = "26.2.2021";
        Klo = "9:35";
        Laji = "J��kiekko";
        Suoritusaika = "2h";
        Intensity = 7;
        Tyytyv�isyys = 7;
        
    }
    
    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Urheilu jaakiekko1 = new Urheilu();
        jaakiekko1.taytatiedot();
        jaakiekko1.tulosta(System.out);

    }

}
