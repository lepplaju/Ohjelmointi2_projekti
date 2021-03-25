package luokat;

import java.util.List;

/**Luokka kertoo kuka ohjelmaa k�ytt��
 * @author Lepplaju
 * @version 8.3.2021
 *
 */
public class Kayttaja {

    private Lajit lajit = new Lajit();
    private Urheilut urheilut = new Urheilut();
    private Pvmt pvmt = new Pvmt();

    
    /**
     * List��n uusi harrastus kerhoon
     * @param laj lis�tt�v� harrastus 
     */
    public void lisaa(Laji laj) {
        lajit.lisaa(laj);
    }
    
    /**lis�t��n uusi urheilu urheilut-taulukkoon
     * @param urheilu lis�tt�v� urheilu
     */
    public void lisaa(Urheilu urheilu) {
        urheilut.lisaa(urheilu);
    }
    
    /**Lis�t��n uusi p�iv�m��r� taulukkoon
     * @param pvm p�iv�m��r�n tiedot
     */
    public void lisaa(Pvm pvm) {
        pvmt.lisaa(pvm);
    }

    /**Antaa tietyn p�iv�m��r�n kaikki urheilusuoritukset n�kyviin
     * @param pvm p�iv�m��r�, jonka tiedot halutaan n�kyviin
     * @return tietyn p�iv�m��r�n tiedot
     */
    public List<Urheilu> annaUrheilut(Pvm pvm) {
        return urheilut.annaUrheilut(pvm.getTunnusNro());
    }
    
    /**Halutaan kuinka monta eri p�iv�m��r��n on olemassa
     * @return int kuinka monena eri p�iv�n� on kirjaus
     * @example
     * <pre name="test">
     * Kayttaja kayttajaX = new Kayttaja()
     * kayttajaX
     * </pre>
     */
    public int eriPaivat() {
        return pvmt.getPvmLkm();
    }
    
    /**Palauttaa listan p�iv�m��r�n tunnusnumeron avulla
     * @param tunnusNro p�iv�m��r�n tunnusnumero
     * @return p�iv�m��r�n listasta
     */
    public Pvm annaPvm(int tunnusNro){
        return pvmt.annaPvm(tunnusNro+1);
    }
    
   // public voi
    
    /**
     * p��ohjelma
     * @param args antaa varmaan jossain vaiheessa k�ytt�j�n nimen
     */
    public static void main(String[] args) {
        Pvm pvm1 = new Pvm();
        System.out.println(pvm1);
        Urheilu urheilu1 = new Urheilu();
        System.out.println(urheilu1);
        Laji laji1 = new Laji(urheilu1.getLajiId());
        System.out.println(laji1);        
        System.out.println(pvm1 +", "+ urheilu1 +", " + laji1);
        
        Kayttaja kayttaja = new Kayttaja();
        
        Pvm paiva1 = new Pvm(), paiva2 = new Pvm(2,2,2);
        paiva1.rekisteroi();
        paiva1.taytaTiedot();
        paiva2.rekisteroi();
        paiva2.taytaTiedot();

        kayttaja.lisaa(paiva1);
        kayttaja.lisaa(paiva2);
        int id1 = paiva1.getTunnusNro();
        int id2 = paiva2.getTunnusNro();
        Urheilu sali11 = new Urheilu(id1); sali11.taytaTiedot(id1); kayttaja.lisaa(sali11);
        Urheilu sali12 = new Urheilu(id1); sali12.taytaTiedot(id1); kayttaja.lisaa(sali12);
        Urheilu pitsi21 = new Urheilu(id2); pitsi21.taytaTiedot(id2); kayttaja.lisaa(pitsi21);
        Urheilu pitsi22 = new Urheilu(id2); pitsi22.taytaTiedot(id2); kayttaja.lisaa(pitsi22);
        Urheilu sali23 = new Urheilu(id2); sali23.taytaTiedot(id2); kayttaja.lisaa(sali23);
        
        System.out.println("=====================");
        
        for (int i = 0; i < kayttaja.eriPaivat(); i++) {
            Pvm pvm = kayttaja.annaPvm(i);
            System.out.println();
            System.out.print("P�iv�m��r� paikassa: " + i+1 +" = ");
            pvm.tulosta(System.out);
            List<Urheilu> loytyneet = kayttaja.annaUrheilut(pvm);
            for (Urheilu urheilu : loytyneet)
                urheilu.tulosta(System.out);
        }
    }
    
}