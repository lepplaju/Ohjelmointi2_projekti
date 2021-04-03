package luokat;

import static luokat.Laji.rand;
import java.io.PrintStream;
import java.util.Calendar;

import fi.jyu.mit.ohj2.Mjonot;

/** Kertoo p‰iv‰m‰‰r‰n, jolloin suoritusta on tehty ja sitoo suorituksen annetuun p‰iv‰m‰‰r‰‰n
 * @author Lepplaju
 * @version 15.3.2021
 *
 */
public class Pvm {
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
     * loka.toString() === "13.10.2021";
     * </pre>
     */
    public void rekisteroi() {
        this.tunnusNro = seuraavaNro++;
    }
    
    /**
     * t‰ytet‰‰n Pvm tarvittavat tiedot
     */
    public void taytaTiedot() {
        this.pv = rand(1,28);
        this.kk = rand(1,12);
        this.vv = 2021;
    }
    
    /**
     * @param i pv
     * @param j kk
     * @param k vv
     */
    public void taytaTiedot(int i, int j, int k) {        
        this.pv=i;
        this.kk=j;
        this.vv=k;
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




    /**
     * @param out mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.pv +"." + this.kk+"." + this.vv);
        
    }


    /**Poimitaan pvm:n tiedot k‰yttˆliittym‰‰n
     * @param rivi rivi teksti‰ tiedostossa
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb,'|',getTunnusNro()));
        this.pv = Mjonot.erota(sb, '.',pv);
        this.kk = Mjonot.erota(sb, '.',kk);
        this.vv = Mjonot.erota(sb, '.',vv);
    }
}
