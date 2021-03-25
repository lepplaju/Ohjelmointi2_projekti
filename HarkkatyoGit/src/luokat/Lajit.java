package luokat;

import java.util.Arrays;

/** Luokka pit‰‰ koossa Laji-taulukkoa
 * @author Lepplaju
 * @version 8.3.2021
 *
 */
public class Lajit {
    
    private static int lkm = 0;
    private final int MAX_LKM = 15;
    private Laji[] lajit=new Laji[MAX_LKM];
   
    
    /**Lis‰t‰‰n uusi laji lajien taulukkoon
     * @param laji mik‰ lajin nimi
     * @example
     * <pre name="test">
     * Lajit lajitX = new Lajit();
     * Laji lenkki = new Laji("lenkki");
     * Laji punttisali = new Laji("punttisali");
     * lenkki.rekisteroi();
     * punttisali.rekisteroi();
     * lajitX.getLkm() === 0;
     * lajitX.lisaa(lenkki);
     * lajitX.lisaa(punttisali);
     * lajitX.getLkm() ===2;
     * lajitX.getLaji(0) === lenkki;
     * lajitX.getLaji(1) === punttisali;
     * </pre>
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
    public Laji getLaji(int i) {
        return lajit[i];
    }
    
    /**
     * @return kuinka monta lajia on olemassa
     */
    public int getLkm() {
        return lkm;
    }
    
}
