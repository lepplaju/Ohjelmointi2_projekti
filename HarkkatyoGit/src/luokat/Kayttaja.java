package luokat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**Luokka kertoo kuka ohjelmaa k‰ytt‰‰
 * @author Lepplaju
 * @version 27.4.2021
 *
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import luokat.SailoException;
 *  private Kayttaja kayttaja;
 *  private Pvm kesa1;
 *  private Pvm kesa2;
 *  private int pvmid1;
 *  private int pvmid2;
 *  private Urheilu juoksu21;
 *  private Urheilu juoksu11;
 *  private Urheilu juoksu22; 
 *  private Urheilu juoksu12; 
 *  private Urheilu juoksu23;
 *  
 *  public void alustaKayttaja() {
 *    kayttaja = new Kayttaja();
 *    kesa1 = new Pvm(); kesa1.taytaTiedot(); kesa1.rekisteroi();
 *    kesa2 = new Pvm(); kesa2.taytaTiedot(); kesa2.rekisteroi();
 *    pvmid1 = kesa1.getTunnusNro();
 *    pvmid2 = kesa2.getTunnusNro();
 *    juoksu21 = new Urheilu(pvmid2); juoksu21.taytaTiedot(pvmid2);
 *    juoksu11 = new Urheilu(pvmid1); juoksu11.taytaTiedot(pvmid1);
 *    juoksu22 = new Urheilu(pvmid2); juoksu22.taytaTiedot(pvmid2); 
 *    juoksu12 = new Urheilu(pvmid1); juoksu12.taytaTiedot(pvmid1); 
 *    juoksu23 = new Urheilu(pvmid2); juoksu23.taytaTiedot(pvmid2);
 *    try {
 *    kayttaja.lisaa(kesa1);
 *    kayttaja.lisaa(kesa2);
 *    kayttaja.lisaa(juoksu21);
 *    kayttaja.lisaa(juoksu11);
 *    kayttaja.lisaa(juoksu22);
 *    kayttaja.lisaa(juoksu12);
 *    kayttaja.lisaa(juoksu23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Kayttaja {

    private Urheilut urheilut = new Urheilut();
    private Pvmt pvmt = new Pvmt();

    
    /**lis‰t‰‰n uusi urheilu urheilut-taulukkoon
     * @param urheilu lis‰tt‰v‰ urheilu
     */
    public void lisaa(Urheilu urheilu) {
        urheilut.lisaa(urheilu);
    }
    
    /**Lis‰t‰‰n uusi p‰iv‰m‰‰r‰ listaan
     * @param pvm p‰iv‰m‰‰r‰n tiedot
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException  
     *  alustaKayttaja();
     *  kayttaja.etsi("*").size() === 2;
     *  kayttaja.lisaa(kesa1);
     *  kayttaja.etsi("*").size() === 3;
     */
    public void lisaa(Pvm pvm) {
        pvmt.lisaa(pvm);
    }
    
    /**Korvaa p‰iv‰m‰‰r‰n, jos sit‰ on muutettu
     * @param pvm mik‰ p‰iv‰m‰‰r‰ on kyseess‰.
     * 
     * @example
     * <pre name="test">
     * #THROWS Exception  
     *  alustaKayttaja();
     *  kayttaja.etsi("*").size() === 2;
     *  kayttaja.korvaaTaiLisaa(kesa1);
     *  kayttaja.etsi("*").size() === 2;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Pvm pvm ) {
        pvmt.korvaaTaiLisaa(pvm);
    }
    
    /**Korvaa urheilun jos sit‰ on muutettu
     * @param urh mik‰ urheilu on kyseess‰
    */
    public void korvaaTaiLisaa(Urheilu urh ) {
        urheilut.korvaaTaiLisaa(urh);
    }

    /**Antaa tietyn p‰iv‰m‰‰r‰n kaikki urheilusuoritukset n‰kyviin
     * @param pvm p‰iv‰m‰‰r‰, jonka tiedot halutaan n‰kyviin
     * @return tietyn p‰iv‰m‰‰r‰n tiedot
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     *  alustaKayttaja();
     *  Pvm kesa3 = new Pvm();
     *  kesa3.rekisteroi();
     *  kayttaja.lisaa(kesa3);
     *  List<Urheilu> loytyneet;
     *  loytyneet = kayttaja.annaUrheilut(kesa3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kayttaja.annaUrheilut(kesa1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == juoksu11 === true;
     *  loytyneet.get(1) == juoksu12 === true;
     *  loytyneet = kayttaja.annaUrheilut(kesa2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == juoksu21 === true;
     * </pre> 
     */
    public List<Urheilu> annaUrheilut(Pvm pvm) {
        return urheilut.annaUrheilut(pvm.getTunnusNro());
    }
    
    
    /**Palauttaa listan p‰iv‰m‰‰r‰n tunnusnumeron avulla
     * @param tunnusNro p‰iv‰m‰‰r‰n tunnusnumero
     * @return p‰iv‰m‰‰r‰n listasta
     */
    public Pvm annaPvm(int tunnusNro){
        return pvmt.annaPvm(tunnusNro+1);
    }
    
    /**Etsii kaikki p‰iv‰m‰‰r‰t listaan
     * @return kaikki p‰iv‰m‰‰r‰t
     */
    public Collection<Pvm> annaPvmt() {
            List<Pvm> loydetyt = new ArrayList<Pvm>();
                     for (Pvm pv : pvmt)
                         loydetyt.add(pv);
                     return loydetyt;            
    }
    
    /**Etsii p‰iv‰m‰‰r‰t hakuehdon mukaan
     * @param ehto mik‰ tekstikent‰n sis‰ltˆ on
     * @return palauttaa listan p‰iv‰m‰‰rist‰, joka t‰ytt‰‰ ehdon
     * @throws SailoException palauttaa poikkeuksen
     */
    public Collection<Pvm> annaPvmt(String ehto) throws SailoException {        
        return etsi(ehto);
    } 
    
    /**Halutaan kuinka monta eri p‰iv‰m‰‰r‰‰n on olemassa
     * @return int kuinka monena eri p‰iv‰n‰ on kirjaus
     */
    public int getEriPaivat() {
        return pvmt.getPvmLkm();
    }
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        pvmt.setTiedostonPerusNimi(hakemistonNimi + "pvmt");
        urheilut.setTiedostonPerusNimi(hakemistonNimi + "urheilut");
    }
    
    /**Lukee k‰ytt‰j‰n tiedot tiedostot
     * @param nimi tiedoston nimi
     * @throws SailoException jos lukeiminen ep‰onnistui
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;  
     *  String hakemisto = "testihakemisto";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/pvmt.dat");
     *  File fhtied = new File(hakemisto+"/urheilut.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  fhtied.delete();
     *  kayttaja = new Kayttaja(); // tiedostoja ei ole, tulee poikkeus 
     *  kayttaja.lueTiedostosta(hakemisto); #THROWS SailoException
     *  alustaKayttaja();
     *  kayttaja.setTiedosto(hakemisto); // nimi annettava koska uusi poisti sen
     *  kayttaja.tallenna(); 
     *  kayttaja = new Kayttaja();
     *  kayttaja.lueTiedostosta(hakemisto);
     *  Collection<Pvm> kaikki = kayttaja.etsi(""); 
     *  Iterator<Pvm> it = kaikki.iterator();
     *  it.next().equals(kesa1);
     *  it.next().equals(kesa2);
     *  it.hasNext() === false;
     *  List<Urheilu> loytyneet = kayttaja.annaUrheilut(kesa1);
     *  Iterator<Urheilu> ih = loytyneet.iterator();
     *  ih.next().equals(juoksu11);
     *  ih.next().equals(juoksu12);
     *  ih.hasNext() === false;
     *  loytyneet = kayttaja.annaUrheilut(kesa2);
     *  ih = loytyneet.iterator();
     *  ih.next().equals(juoksu21);
     *  ih.next().equals(juoksu22);
     *  ih.next().equals(juoksu23);
     *  ih.hasNext() === false;
     *  kayttaja.lisaa(kesa2);
     *  kayttaja.lisaa(juoksu23);
     *  kayttaja.tallenna(); // tekee molemmista .bak
     *  ftied.delete()  === true;
     *  fhtied.delete() === true;
     *  File fbak = new File(hakemisto+"/pvmt.bak");
     *  File fhbak = new File(hakemisto+"/urheilut.bak");
     *  fbak.delete() === true;
     *  fhbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        pvmt = new Pvmt(); 
        urheilut = new Urheilut();

        setTiedosto(nimi);
        pvmt.lueTiedostosta();
        urheilut.lueTiedostosta();
    }
    
    /**Tallentaa Tiedoston
     * @throws SailoException jos tulee poikkeus
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            pvmt.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        
       try {
           urheilut.tallenna();
       } catch ( SailoException ex ) {
           virhe = ex.getMessage();
       }
        
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }

    /** 
     * 
     * @param hakuehto tekstin‰
     * @return listan p‰iv‰m‰‰rist‰, jotka vastaavat hakuehtoa
     * @throws SailoException Jos jotakin menee v‰‰rin
     * 
     * @example 
     * <pre name="test">
     * #THROWS CloneNotSupportedException, SailoException
     * #import java.util.*;
     * alustaKayttaja();
     * Pvm pvm3 = new Pvm(); pvm3.rekisteroi();
     * pvm3.aseta("11.11.2021");
     * kayttaja.lisaa(pvm3);
     * Collection<Pvm> loytyneet = kayttaja.etsi("*11*");
     * loytyneet.size() === 1;
     * Iterator<Pvm> it = loytyneet.iterator();
     * it.next() == pvm3 === true; 
     * </pre>
     */ 
    public Collection<Pvm> etsi(String hakuehto) throws SailoException { 
        return pvmt.etsi(hakuehto); 
    } 
    
    /**Poistaa p‰iv‰m‰‰r‰n
     * @param pvm joka halutaan poistaa
     * @return montako pvm poistettiin
     * 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKayttaja();
     *   kayttaja.etsi("*").size() === 2;
     *   kayttaja.annaUrheilut(kesa1).size() === 2;
     *   kayttaja.poistaPvm(kesa1) === true;
     *   kayttaja.etsi("*").size() === 1;
     *   kayttaja.annaUrheilut(kesa1).size() === 0;
     *   kayttaja.annaUrheilut(kesa2).size() === 3;
     * </pre>
     */
    public boolean poistaPvm(Pvm pvm) {
        if(pvm==null) return false;
        boolean poistettiinko =pvmt.poistaPvm(pvm);
        urheilut.poistaPvmUrheilut(pvm.getTunnusNro());
        return poistettiinko;        
    }
   
    /** Poistetaan valittu urheilu
     * @param urh urheilu joka halutaan poistaa
     * 
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaKayttaja();
     *   kayttaja.annaUrheilut(kesa1).size() === 2;
     *   kayttaja.poistaUrheilu(juoksu11);
     *   kayttaja.annaUrheilut(kesa1).size() === 1;
     */ 
    public void poistaUrheilu(Urheilu urh) {
        urheilut.poistaUrheilu(urh);        
    }
    
    /**palauttaa keskiarvon urheilukirjauksien tyytyv‰isyydest‰
     * @return palauttaa tyytyv‰isyyden
     */
    public double getKAtyytyvaisyys() {
        return urheilut.getKAtyytyvaisyys();
    }
    
    /**palauttaa keskiarvon urheilukirjauksien intensiteetist‰  
     * @return kaikkien kirjauksien intensiteetin keskiarvon
     */
    public double getKAintensiteetti() {
        return urheilut.getKAintensiteetti();
    }
}





    
