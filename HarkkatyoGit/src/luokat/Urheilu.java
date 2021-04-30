package luokat;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
    private String lajiNimi;
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
        this.klo = "rand";
        this.pvmNro = paivaid;
        this.lajiNimi = " " +"rand";
        this.suoritusaika = "rand";
        this.intensity = 1;
        this.tyytyvaisyys = 1;
        this.muutaLisattavaa ="*muuta lis‰tt‰v‰‰";
    }
    
    /**
     * T‰ytt‰‰ tiedot Urheiluun ilman yhteytt‰ mihink‰‰n p‰iv‰m‰‰r‰‰n
     */
    public void taytaTiedot() {
        this.klo = "rand";
        this.lajiNimi = " " +"rand";
        this.suoritusaika = "rand";
        this.intensity = 1;
        this.tyytyvaisyys = 1;
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
        out.println("Lajin id: " + this.lajiNimi + ", kelloanika: " + this.klo + ", "+ " treenin kesto: "
                +this.suoritusaika+", " +"treenin intensiteetti: " + this.intensity +", "
                +  "treeniin tyytyv‰isyys: " + this.tyytyvaisyys+", "  +this.muutaLisattavaa);
    }
    

    /**Selvitt‰‰ urheilun tiedot | erotellusta merkkijonosta
     * @param rivi yhden urheilun tiedot
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pvmNro = Mjonot.erota(sb, '|', pvmNro);
        lajiNimi=Mjonot.erota(sb, '|',lajiNimi);
        klo=Mjonot.erota(sb, '|',klo);       
        suoritusaika=Mjonot.erota(sb, '|',suoritusaika);
        intensity=Mjonot.erota(sb, '|',intensity);
        tyytyvaisyys=Mjonot.erota(sb, '|',tyytyvaisyys);
        muutaLisattavaa=Mjonot.erota(sb, '|',"ei muuta lis‰tt‰v‰‰");
    }
    
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + pvmNro + "|" + lajiNimi + "|" + klo + "|" + suoritusaika 
                + "|" + intensity + "|" + tyytyvaisyys  + "|" + muutaLisattavaa;
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
     * @return palauttaa mihin p‰iv‰m‰‰r‰n Id-numeroon t‰m‰ urheilu on liitetty
     */
    public int getPvmNro() {
        return this.pvmNro;
    }
          
    /**
     * @return palauttaa urheilun lajin nimen
     */
    public String getLajiId() {
        return this.lajiNimi;
    }

    /** 
     * @return Ensimm‰inen sarake, joka on k‰ytt‰j‰lle n‰kyviss‰
     */
    public int ekaKentta() {
        return 2;
    }

    /**
     * @return Sarakkeiden lukum‰‰r‰
     */
    public int getKenttia() {
        return 8;
    }

    /** Hakee StringGridin tietyn kent‰n
     * @param k mink‰ kent‰n kysymys halutaan
     * @return kyseisen kent‰n otsikko
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0:
            return "id";
        case 1:
            return "pvmId";
        case 2:
            return "Lajin nimi";
        case 3:
            return "Klo";
        case 4:
            return "Suoritusaika";
        case 5: 
            return "Intensiteetti";
        case 6: 
            return "Tyytyvaisyys";
        case 7: 
            return "Muuta Lis‰tt‰v‰‰";
        default:
            return "???";
    }

    }
    
    @Override
    public Urheilu clone() throws CloneNotSupportedException { 
        return (Urheilu)super.clone();
    }


    /** asetetaan valitun kent‰n sis‰ltˆ
     * @param k mink‰ kent‰n sis‰ltˆ
     * @param s asetettava sis‰ltˆ merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuilder sb = new StringBuilder(st);
        if (st.contains("|")) return "Sis‰lt‰‰ kielletyn merkin |";
        switch (k) {
        case 0:
            return "id";
        case 1:
            return "pvmId";
        case 2:
            lajiNimi=st;
            return null;
        case 3:
            int tunnit = Mjonot.erota(sb,':', -1);
            if (tunnit<0 || tunnit>24) return "tarkista kellonajan oikeellisuus";
            int minuutit = Mjonot.erotaInt(sb, 0);
            if (minuutit<0||minuutit>59) return "tarkista kellonajan oikeellisuus";
            klo = st;
            return null;
        case 4:
            suoritusaika = st;
            return null;
        case 5: 
            double intenss= Mjonot.erotaDouble(sb,0);
            if (intenss<0||intenss>10) return "Intensiteetti pit‰‰ olla luku 0-10 v‰list‰"; 
            intensity = intenss; 
            return null;
        case 6: 
            double tyytyv= Mjonot.erotaDouble(sb,0);
            if (tyytyv<0||tyytyv>10) return "Tyytyv‰isyys pit‰‰ olla luku 0-10 v‰list‰"; 
            tyytyvaisyys = tyytyv; 
            return null;
        case 7: 
            if (st.length()>25) return "Ei avauduta t‰‰ll‰";
            muutaLisattavaa = st;
            return null;
        default:
            return "V‰‰r‰ kent‰n indeksi";  
        }
    }

    /**
     * @param k Mink‰ kent‰n sis‰ltˆ halutaan
     * @return valitun kent‰n sis‰ltˆ
     */
    public String anna(int k) {
        switch (k) {           
        case 0:
            return "" + tunnusNro;
        case 1:
            return "" +pvmNro;
        case 2:
            return "" + lajiNimi;
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

    /**Asettaa lajin nimen
     * @param s mik‰ lajin nimeksi halutaan
     * @return palauttaa tyhj‰n
     */
    public String setlajiId(String s) {      
        lajiNimi =s;
        return null;
    }

    /** Asettaa kellonajan urheilun tietoihin
     * @param s teksti, joka halutaan sijoittaa
     * @return null
     */
    public String setKlo(String s) {
        klo = s;
        return null;
    }

    /** asettaa urheilulle p‰iv‰m‰‰r‰n tunnusnumeron 
     * @param pvm mik‰ p‰iv‰m‰‰r‰ on kyseess‰
     */
    public void setPvmId(Pvm pvm) {
        pvmNro=pvm.getTunnusNro();
        
    }

    /**Palauttaa yksitt‰isen urheilukirjauksen tyytyv‰isyyden
     * @return tyytyv‰isyys reaalilukuna
     */
    public double getTyytyvaisyys() {
        return this.tyytyvaisyys;
        
    }

    /**palauttaa yksitt‰isen urheilukirjauksen intensiteetin    
     * @return intensiteetti reaalilukuna
     */
    public double getIntensity() {
        return this.intensity;
    }
    
}
