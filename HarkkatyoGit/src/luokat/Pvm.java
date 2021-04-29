package luokat;

import java.io.PrintStream;
import java.util.Calendar;

import fi.jyu.mit.ohj2.Mjonot;

/** Kertoo p‰iv‰m‰‰r‰n, jolloin suoritusta on tehty ja sitoo suorituksen annetuun p‰iv‰m‰‰r‰‰n
 * @author Lepplaju
 * @version 15.3.2021
 *
 */
public class Pvm implements Cloneable, Comparable<Pvm> {
    private int tunnusNro;
    private static int seuraavaNro = 1;
    private int pv;
    private int kk;
    private int vv;
    
    
    /**
     * @return palauttaa p‰iv‰n
     */
    public int getPv() {
        return this.pv;
    }
    
    /**
     * @return palauttaa kuukauden
     */
    public int getKk() {
        return this.kk;
    }
    
    /**
     * @return palauttaa vuoden
     */
    public int getVv() {
        return this.vv;
    }
    
    /**
     *  alustaa p‰iv‰m‰‰r‰n t‰ll‰ p‰iv‰lle
     */
    public Pvm() {
        //
        }
    
    
    /**Alustaa p‰iv‰m‰‰r‰n annetuilla arvoilla
     * @param pv p‰iv‰
     * @param kk kuukausi
     * @param vv vuosi
     */
    public Pvm(int pv, int kk, int vv) {
        this.pv=pv;
        this.kk=kk;
        this.vv=vv;
    }
    
    /**
     * Annetaan tunnusnumero p‰iv‰m‰‰r‰lle, n‰in estet‰‰n usean saman p‰iv‰m‰‰r‰n n‰kymist‰
     * @example
     * <pre name="test">
     * Pvm tammi = new Pvm(11,1,2021);
     * Pvm loka = new Pvm(13,10,2021);
     * tammi.getTunnusNro() === 0;
     * loka.getTunnusNro() === 0;
     * tammi.rekisteroi();
     * loka.rekisteroi();
     * loka.getTunnusNro() === 2;
     * loka.toString().equals("13.10.2021");
     * </pre>
     */
    public void rekisteroi() {
        this.tunnusNro = seuraavaNro++;
    }
    
    /**
     * t‰ytet‰‰n Pvm tarvittavat tiedot
     */
    public void taytaTiedot() {
        this.pv = 28;
        this.kk = 12;
        this.vv = 2021;
    }
    
    /** T‰ytt‰‰ halutut tiedot
     * @param p P‰iv‰
     * @param k Kuukausi
     * @param v Vuosi
     */
    public void taytaTiedot(int p, int k, int v) {        
        this.pv=p;
        this.kk=k;
        this.vv=v;
    }   
    
    /**
     * Laittaa pvm:n tiedot t‰lle p‰iv‰lle
     */
    public void taytaTanaan() {
        var nyt = Calendar.getInstance();
        this.pv= nyt.get(Calendar.DATE);
        this.kk= nyt.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        this.vv=nyt.get(Calendar.YEAR);
    }

    /**
     * @return tietyn p‰iv‰m‰‰r‰n tunnusnumeron 
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /**
     * @param id p‰iv‰m‰‰r‰n tunnusnumero
     */
    public void setTunnusNro(int id) {
        tunnusNro = id;
        if (seuraavaNro<=id) seuraavaNro =id+1;
    }
    
    /**
     * tulostaa p‰iv‰m‰‰r‰n
     */ 
    @Override
    public String toString() { 
        return this.tunnusNro + "|" + this.pv +"." + this.kk+"." + this.vv;
    }

    /**Palauttaa tiedot ilman ett‰ tunnusNro tulee n‰ytˆlle
     * @return p‰iv‰yksen ilman tunnusnumeroa
     */
    public String getPaivays() {
        return this.pv +"." + this.kk+"." + this.vv;
    }

    @Override
    public Pvm clone() throws CloneNotSupportedException{
        Pvm uusi;
        uusi = (Pvm) super.clone();
        return uusi;
    }
    
    /** Asettaa p‰iv‰m‰‰r‰n kun muokataan jo olemassa olevaa
     * @param tekstikentta mit‰ tekstikentt‰‰n on syˆtetty
     * @return tyhj‰n jos ei ongelmia
     */
    public String aseta(String tekstikentta) {
        StringBuilder sb = new StringBuilder(tekstikentta);
        this.pv = Mjonot.erota(sb, '.',pv);
        this.kk = Mjonot.erota(sb, '.',kk);
        this.vv = Mjonot.erota(sb, '.',vv);
        if(kk<1||kk>12 || pv>31|| pv<1 ||vv<1 || vv>2021) return "tarkista kent‰n oikeellisuus"; 
        return null;
    }
    
    /**Poimitaan pvm:n tiedot k‰yttˆliittym‰‰n
     * @param rivi rivi teksti‰ tiedostossa
     * @example
     * <pre name="test">
     *   Pvm pvm = new Pvm();
     *   pvm.parse("1.1.2020");
     *   pvm.rekisteroi();
     *   pvm.getTunnusNro() === 3;
     *   pvm.getPaivays().equals("1.1.2020");
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb,'|',getTunnusNro()));
        this.pv = Mjonot.erota(sb, '.',pv);
        this.kk = Mjonot.erota(sb, '.',kk);
        this.vv = Mjonot.erota(sb, '.',vv);
    }

    /**
     * J‰rjestet‰‰n p‰iv‰m‰‰r‰t oikeaan j‰rjestykseen vertailemalla
     * @param pvm mihin verrataan
     * @return 1, -1 tai 0 -  0 jos samat, 1 jos mihin verrataan suurempi
     */
    @Override
    public int compareTo(Pvm pvm) {      
        if (this.vv<pvm.vv) return 1;
        if (this.vv>pvm.vv) return -1;
        
        if (this.vv==pvm.vv){
            if (this.kk<pvm.kk) return 1; 
            if (this.vv>pvm.vv) return -1;
            
            if (this.kk==pvm.kk) {
                if (this.pv<pvm.pv) return 1; 
                if (this.pv>pvm.pv) return -1;
                
                if (this.pv==pvm.pv) return 0;
            }
        }
            return 2;
            }        
    }


