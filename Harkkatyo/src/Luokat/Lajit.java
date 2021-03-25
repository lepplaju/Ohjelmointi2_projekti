package luokat;

import java.util.Arrays;

/** Luokka pitää koossa Laji-taulukkoa
 * @author Lepplaju
 * @version 8.3.2021
 *
 */
public class Lajit {
    
    private static int lkm = 0;
    private final int MAX_LKM = 15;
    private Laji[] lajit=new Laji[MAX_LKM];
   
    
    /**Lisätään uusi laji lajien taulukkoon
     * @param laji mikä lajin nimi
     * throws SailoException ;
     */
    public void lisaa(Laji laji) {      
        if (lkm >= lajit.length) lajit = Arrays.copyOf(lajit, lajit.length + 5);
        lajit[lkm++] = laji;
    }

    /**
     * @param i taulukon indeksi
     * @return viite lajiin, jonka indeksi i
     * @throws IndexOutOfBoundsException jos i invalidi
     */
    public String tulosta(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return lajit[i].toString();
    }
    
    /**tulostaa lajin nimen indeksin mukaan
     * @param i taulukon lajit indeksi
     * @return lajin
     */
    public String getLaji(int i) {
        return lajit[i].toString();
    }
    
}
