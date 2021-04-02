package luokat;

import java.io.OutputStream;
import java.io.PrintStream;
import static luokat.Laji.rand;

/**
 * @author Lepplaju
 * @version 9.3.2021
 *
 */
public class Urheilu {
    
    private static int seuraavaNro =1;
    private String klo;
    private int pvmNro;
    private int tunnusNro;
    private int lajiId=1;
    private String suoritusaika;
    private double intensity;
    private double tyytyvaisyys;
    private String muutaLisattavaa;
    
    /**
     * Urheilun alustus
     */
    public Urheilu() {
        //  
    }
    
    /**Luo uuden urheilun
     * @param paivaid mihin p‰iv‰m‰‰r‰n id:seen sidottu
     */
    public Urheilu(int paivaid) {
        this.pvmNro = paivaid;
    }
    
    /**
     * T‰ytt‰‰ tiedot Urheiluun
     * @param paivaid mink‰ p‰iv‰m‰‰r‰n tietoja
     */
    public void taytaTiedot(int paivaid) {
        this.klo = rand(6,21) + ";" + String.format("%02d",rand(0,59));
        this.pvmNro = paivaid;
        this.lajiId = rand(1,8);
        this.suoritusaika = rand(0,2) +":" + rand(30,59);
        this.intensity = rand(5, 10);
        this.tyytyvaisyys = rand(5, 10);
        this.muutaLisattavaa ="*muuta lis‰tt‰v‰‰";
    }
    
    /**
     * antaa urheilulle tunnusnumeron
     * @example
     * <pre name="test">
     *   Urheilu gymi1 = new Urheilu();
     *   gymi1.getTunnusNro() === 0;
     *   gymi1.rekisteroi();
     *   Urheilu kaljakellunta1 = new Urheilu();
     *   kaljakellunta1.rekisteroi();
     *   int n1 = gymi1.getTunnusNro();
     *   int n2 = kaljakellunta1.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public void rekisteroi() {
        this.tunnusNro = seuraavaNro++;  
    }
    
    /**tulostus
     * @param os tietovirta
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**Tulostetaan urheilun tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Lajin id: " + this.lajiId + ", kelloanika: " + this.klo + ", "+ " treenin kesto: "
                +this.suoritusaika+", " +"treenin intensiteetti: " + this.intensity +", "
                +  "treeniin tyytyv‰isyys: " + this.tyytyvaisyys+", "  +this.muutaLisattavaa);
    }
    
    @Override
    public String toString() {
        return /*getLaji(this.laji) +", " + */this.suoritusaika+", " +this.intensity +", " +  
                this.tyytyvaisyys+", "  +this.muutaLisattavaa;
    }

    /**
     * @return palauttaa lajin id:n
     */
    public int getTunnusNro() {
          return this.tunnusNro;
    }
    
    /**
     * @return palauttaa mihin p‰iv‰m‰‰r‰n Id-numeroon t‰m‰ urheilu on liitetty
     */
    public int getPvmNro() {
        return this.pvmNro;
    }
          
    /**
     * @return palauttaa Urheiluun liittyv‰n lajin Id:n
     */
    public int getLajiId() {
        return this.lajiId;
    }
        


}
