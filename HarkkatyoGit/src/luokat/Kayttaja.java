package luokat;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**Luokka kertoo kuka ohjelmaa k‰ytt‰‰
 * @author Lepplaju
 * @version 25.3.2021
 *
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
    
    /**Lis‰t‰‰n uusi p‰iv‰m‰‰r‰ taulukkoon
     * @param pvm p‰iv‰m‰‰r‰n tiedot
     */
    public void lisaa(Pvm pvm) {
        pvmt.lisaa(pvm);
    }
    
    /**Korvaa p‰iv‰m‰‰r‰n, jos sit‰ on muutettu
     * @param pvm mik‰ p‰iv‰m‰‰r‰ on kyseess‰.
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
     */
    public List<Urheilu> annaUrheilut(Pvm pvm) {
        return urheilut.annaUrheilut(pvm.getTunnusNro());
    }
    
    /**Halutaan kuinka monta eri p‰iv‰m‰‰r‰‰n on olemassa
     * @return int kuinka monena eri p‰iv‰n‰ on kirjaus
     */
    public int getEriPaivat() {
        return pvmt.getPvmLkm();
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
     */ 
    public Collection<Pvm> etsi() throws SailoException { 
        return pvmt.etsi(); 
    } 
    
    /**Poistaa p‰iv‰m‰‰r‰n
     * @param pvm joka halutaan poistaa
     * @return montako pvm poistettiin
     */
    public boolean poistaPvm(Pvm pvm) {
        if(pvm==null) return false;
        boolean poistettiinko =pvmt.poistaPvm(pvm);
        urheilut.poistaPvmUrheilut(pvm.getTunnusNro());
        return poistettiinko;        
    }
   
    public void poistaUrheilu(Urheilu urh) {
        urheilut.poistaUrheilu(urh);
        
    }
  
    /**
     * p‰‰ohjelma
     * @param args antaa varmaan jossain vaiheessa k‰ytt‰j‰n nimen
     */
    public static void main(String[] args) {
        Pvm pvm1 = new Pvm();
        System.out.println(pvm1);
        Urheilu urheilu1 = new Urheilu();
        System.out.println(urheilu1);
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
        
        for (int i = 0; i < kayttaja.getEriPaivat(); i++) {
            Pvm pvm = kayttaja.annaPvm(i);
            System.out.println();
            System.out.print("P‰iv‰m‰‰r‰ paikassa: " + i+1 +" = ");
            pvm.tulosta(System.out);
            List<Urheilu> loytyneet = kayttaja.annaUrheilut(pvm);
            for (Urheilu urheilu : loytyneet)
                urheilu.tulosta(System.out);
        }
    }




    
}
