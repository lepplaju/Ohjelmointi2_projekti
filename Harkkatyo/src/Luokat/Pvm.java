package luokat;

import static luokat.Laji.rand;
import java.io.PrintStream;
import java.util.Calendar;

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
     *  alustaa p‰iv‰m‰‰r‰n t‰ll‰ p‰iv‰lle
     */
    public Pvm() {
        var nyt = Calendar.getInstance();
        this.pv= nyt.get(Calendar.DATE);
        this.kk= nyt.get(Calendar.MONTH) - Calendar.JANUARY + 1;
        this.vv=nyt.get(Calendar.YEAR);
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
     * @return tietyn p‰iv‰m‰‰r‰n tunnusnumeron 
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    /**
     * tulostaa p‰iv‰m‰‰r‰n
     */ 
    @Override
    public String toString() {
        return this.pv +"." + this.kk+"." + this.vv;
    }

    /**
     * @param i pv
     * @param j kk
     * @param k vv
     */
    public void tayta(int i, int j, int k) {        
        this.pv=i;
        this.kk=j;
        this.vv=k;
    }


    /**
     * @param out mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(this.pv +"." + this.kk+"." + this.vv);
        
    }
}
