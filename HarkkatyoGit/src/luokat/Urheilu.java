package luokat;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
import static luokat.Laji.rand;
import luokat.Laji;
import luokat.Lajit;

/**
 * @author Lepplaju
 * @version 9.3.2021
 *
 */
public class Urheilu implements Cloneable{
    
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
     * @param paivaid mihin päivämäärän id:seen sidottu
     */
    public Urheilu(int paivaid) {
        this.pvmNro = paivaid;
    }
    
    /**
     * Täyttää tiedot Urheiluun
     * @param paivaid minkä päivämäärän tietoja
     */
    public void taytaTiedot(int paivaid) {
        this.klo = rand(6,21) + ":" + String.format("%02d",rand(0,59));
        this.pvmNro = paivaid;
        this.lajiId = rand(1,8);
        this.suoritusaika = rand(0,2) +":" + rand(30,59);
        this.intensity = rand(5, 10);
        this.tyytyvaisyys = rand(5, 10);
        this.muutaLisattavaa ="*muuta lisättävää";
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
                +  "treeniin tyytyväisyys: " + this.tyytyvaisyys+", "  +this.muutaLisattavaa);
    }
    

    /**Selvittää urheilun tiedot | erotellusta merkkijonosta
     * @param rivi yhden urheilun tiedot
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pvmNro = Mjonot.erota(sb, '|', pvmNro);
        lajiId=Mjonot.erota(sb, '|',lajiId);
        klo=Mjonot.erota(sb, '|',klo);       
        suoritusaika=Mjonot.erota(sb, '|',suoritusaika);
        intensity=Mjonot.erota(sb, '|',intensity);
        tyytyvaisyys=Mjonot.erota(sb, '|',tyytyvaisyys);
        muutaLisattavaa=Mjonot.erota(sb, '|',"ei muuta lisättävää");
    }
    
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + pvmNro + "|" + lajiId + "|" + klo + "|" + suoritusaika 
                + "|" + intensity + "|" + tyytyvaisyys;
    }

    /**Asettaa urheilun tunnusnumeron
     * @param id tunnusnumero
     */
    public void setTunnusNro(int id){
      this.tunnusNro =id;  
      if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * @return palauttaa lajin id:n
     */
    public int getTunnusNro() {
          return this.tunnusNro;
    }
    
    /**
     * @return palauttaa mihin päivämäärän Id-numeroon tämä urheilu on liitetty
     */
    public int getPvmNro() {
        return this.pvmNro;
    }
          
    /**
     * @return palauttaa Urheiluun liittyvän lajin Id:n
     */
    public int getLajiId() {
        return this.lajiId;
    }

    /** 
     * @return Ensimmäinen sarake, joka on käyttäjälle näkyvissä
     */
    public int ekaKentta() {
        return 2;
    }

    /**
     * @return Sarakkeiden lukumäärä
     */
    public int getKenttia() {
        return 8;
    }

    /** Hakee StringGridin tietyn kentän
     * @param k minkä kentän kysymys halutaan
     * @return kyseisen kentän otsikko
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0:
            return "id";
        case 1:
            return "pvmId";
        case 2:
            return "lajiId";
        case 3:
            return "klo";
        case 4:
            return "suoritusaika";
        case 5: 
            return "intensity";
        case 6: 
            return "tyytyvaisyys";
        case 7: 
            return "Muuta Lisättävää";
        default:
            return "???";
    }

    }
    
    @Override
    public Urheilu clone() throws CloneNotSupportedException { 
        return (Urheilu)super.clone();
    }


    /** asetetaan valitun kentän sisältö
     * @param k minkä kentän sisältö
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuilder sb = new StringBuilder(st);
        switch (k) {
        case 0:
            return "id";
        case 1:
            return "pvmId";
        case 2:
            return "lajiId";
        case 3:
            klo = st;
            return "klo";
        case 4:
            suoritusaika = st;
            return null;
        case 5: 
            intensity = Mjonot.erotaDouble(sb,0);
            return null;
        case 6: 
            tyytyvaisyys = Mjonot.erotaDouble(sb,0);
            return null;
        case 7: 
            muutaLisattavaa = st;
            return null;
        default:
            return "Väärä kentän indeksi";  
        }
    }

    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     */
    public String anna(int k) {
        switch (k) {           
        case 0:
            return "" + tunnusNro;
        case 1:
            return "" +pvmNro;
        case 2:
            return "" + lajiId;
        case 3:
            return "" + klo;
        case 4:
            return "" + suoritusaika;
        case 5: 
            return "" + intensity;
        case 6: 
            return "" + tyytyvaisyys;
        case 7: 
            return "" + muutaLisattavaa;
        default:
            return "???";
        }
    }
    
}
