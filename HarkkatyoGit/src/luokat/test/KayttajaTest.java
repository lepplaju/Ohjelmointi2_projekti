package luokat.test;
// Generated by ComTest BEGIN
import luokat.SailoException;
import java.util.*;
import java.io.*;
import static org.junit.Assert.*;
import org.junit.*;
import luokat.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2021.04.27 08:51:36 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KayttajaTest {


  // Generated by ComTest BEGIN  // Kayttaja: 14
   private Kayttaja kayttaja; 
   private Pvm kesa1; 
   private Pvm kesa2; 
   private int pvmid1; 
   private int pvmid2; 
   private Urheilu juoksu21; 
   private Urheilu juoksu11; 
   private Urheilu juoksu22; 
   private Urheilu juoksu12; 
   private Urheilu juoksu23; 

   public void alustaKayttaja() {
     kayttaja = new Kayttaja(); 
     kesa1 = new Pvm(); kesa1.taytaTiedot(); kesa1.rekisteroi(); 
     kesa2 = new Pvm(); kesa2.taytaTiedot(); kesa2.rekisteroi(); 
     pvmid1 = kesa1.getTunnusNro(); 
     pvmid2 = kesa2.getTunnusNro(); 
     juoksu21 = new Urheilu(pvmid2); juoksu21.taytaTiedot(pvmid2); 
     juoksu11 = new Urheilu(pvmid1); juoksu11.taytaTiedot(pvmid1); 
     juoksu22 = new Urheilu(pvmid2); juoksu22.taytaTiedot(pvmid2); 
     juoksu12 = new Urheilu(pvmid1); juoksu12.taytaTiedot(pvmid1); 
     juoksu23 = new Urheilu(pvmid2); juoksu23.taytaTiedot(pvmid2); 
     try {
     kayttaja.lisaa(kesa1); 
     kayttaja.lisaa(kesa2); 
     kayttaja.lisaa(juoksu21); 
     kayttaja.lisaa(juoksu11); 
     kayttaja.lisaa(juoksu22); 
     kayttaja.lisaa(juoksu12); 
     kayttaja.lisaa(juoksu23); 
     } catch ( Exception e) {
        System.err.println(e.getMessage()); 
     }
   }
  // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLisaa69 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa69() throws SailoException {    // Kayttaja: 69
    alustaKayttaja(); 
    assertEquals("From: Kayttaja line: 72", 2, kayttaja.etsi("*").size()); 
    kayttaja.lisaa(kesa1); 
    assertEquals("From: Kayttaja line: 74", 3, kayttaja.etsi("*").size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testKorvaaTaiLisaa84 
   * @throws Exception when error
   */
  @Test
  public void testKorvaaTaiLisaa84() throws Exception {    // Kayttaja: 84
    alustaKayttaja(); 
    assertEquals("From: Kayttaja line: 87", 2, kayttaja.etsi("*").size()); 
    kayttaja.korvaaTaiLisaa(kesa1); 
    assertEquals("From: Kayttaja line: 89", 2, kayttaja.etsi("*").size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testAnnaUrheilut108 
   * @throws SailoException when error
   */
  @Test
  public void testAnnaUrheilut108() throws SailoException {    // Kayttaja: 108
    alustaKayttaja(); 
    Pvm kesa3 = new Pvm(); 
    kesa3.rekisteroi(); 
    kayttaja.lisaa(kesa3); 
    List<Urheilu> loytyneet; 
    loytyneet = kayttaja.annaUrheilut(kesa3); 
    assertEquals("From: Kayttaja line: 117", 0, loytyneet.size()); 
    loytyneet = kayttaja.annaUrheilut(kesa1); 
    assertEquals("From: Kayttaja line: 119", 2, loytyneet.size()); 
    assertEquals("From: Kayttaja line: 120", true, loytyneet.get(0) == juoksu11); 
    assertEquals("From: Kayttaja line: 121", true, loytyneet.get(1) == juoksu12); 
    loytyneet = kayttaja.annaUrheilut(kesa2); 
    assertEquals("From: Kayttaja line: 123", 3, loytyneet.size()); 
    assertEquals("From: Kayttaja line: 124", true, loytyneet.get(0) == juoksu21); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testLueTiedostosta184 
   * @throws SailoException when error
   */
  @Test
  public void testLueTiedostosta184() throws SailoException {    // Kayttaja: 184
    String hakemisto = "testihakemisto"; 
    File dir = new File(hakemisto); 
    File ftied  = new File(hakemisto+"/pvmt.dat"); 
    File fhtied = new File(hakemisto+"/urheilut.dat"); 
    dir.mkdir(); 
    ftied.delete(); 
    fhtied.delete(); 
    kayttaja = new Kayttaja();  // tiedostoja ei ole, tulee poikkeus 
    try {
    kayttaja.lueTiedostosta(hakemisto); 
    fail("Kayttaja: 196 Did not throw SailoException");
    } catch(SailoException _e_){ _e_.getMessage(); }
    alustaKayttaja(); 
    kayttaja.setTiedosto(hakemisto);  // nimi annettava koska uusi poisti sen
    kayttaja.tallenna(); 
    kayttaja = new Kayttaja(); 
    kayttaja.lueTiedostosta(hakemisto); 
    Collection<Pvm> kaikki = kayttaja.etsi(""); 
    Iterator<Pvm> it = kaikki.iterator(); 
    it.next().equals(kesa1); 
    it.next().equals(kesa2); 
    assertEquals("From: Kayttaja line: 206", false, it.hasNext()); 
    List<Urheilu> loytyneet = kayttaja.annaUrheilut(kesa1); 
    Iterator<Urheilu> ih = loytyneet.iterator(); 
    ih.next().equals(juoksu11); 
    ih.next().equals(juoksu12); 
    assertEquals("From: Kayttaja line: 211", false, ih.hasNext()); 
    loytyneet = kayttaja.annaUrheilut(kesa2); 
    ih = loytyneet.iterator(); 
    ih.next().equals(juoksu21); 
    ih.next().equals(juoksu22); 
    ih.next().equals(juoksu23); 
    assertEquals("From: Kayttaja line: 217", false, ih.hasNext()); 
    kayttaja.lisaa(kesa2); 
    kayttaja.lisaa(juoksu23); 
    kayttaja.tallenna();  // tekee molemmista .bak
    assertEquals("From: Kayttaja line: 221", true, ftied.delete()); 
    assertEquals("From: Kayttaja line: 222", true, fhtied.delete()); 
    File fbak = new File(hakemisto+"/pvmt.bak"); 
    File fhbak = new File(hakemisto+"/urheilut.bak"); 
    assertEquals("From: Kayttaja line: 225", true, fbak.delete()); 
    assertEquals("From: Kayttaja line: 226", true, fhbak.delete()); 
    assertEquals("From: Kayttaja line: 227", true, dir.delete()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testEtsi266 
   * @throws CloneNotSupportedException when error
   * @throws SailoException when error
   */
  @Test
  public void testEtsi266() throws CloneNotSupportedException, SailoException {    // Kayttaja: 266
    alustaKayttaja(); 
    Pvm pvm3 = new Pvm(); pvm3.rekisteroi(); 
    pvm3.aseta("11.11.2021"); 
    kayttaja.lisaa(pvm3); 
    Collection<Pvm> loytyneet = kayttaja.etsi("*11*"); 
    assertEquals("From: Kayttaja line: 274", 1, loytyneet.size()); 
    Iterator<Pvm> it = loytyneet.iterator(); 
    assertEquals("From: Kayttaja line: 276", true, it.next() == pvm3); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaPvm288 
   * @throws Exception when error
   */
  @Test
  public void testPoistaPvm288() throws Exception {    // Kayttaja: 288
    alustaKayttaja(); 
    assertEquals("From: Kayttaja line: 291", 2, kayttaja.etsi("*").size()); 
    assertEquals("From: Kayttaja line: 292", 2, kayttaja.annaUrheilut(kesa1).size()); 
    assertEquals("From: Kayttaja line: 293", true, kayttaja.poistaPvm(kesa1)); 
    assertEquals("From: Kayttaja line: 294", 1, kayttaja.etsi("*").size()); 
    assertEquals("From: Kayttaja line: 295", 0, kayttaja.annaUrheilut(kesa1).size()); 
    assertEquals("From: Kayttaja line: 296", 3, kayttaja.annaUrheilut(kesa2).size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testPoistaUrheilu310 
   * @throws Exception when error
   */
  @Test
  public void testPoistaUrheilu310() throws Exception {    // Kayttaja: 310
    alustaKayttaja(); 
    assertEquals("From: Kayttaja line: 313", 2, kayttaja.annaUrheilut(kesa1).size()); 
    kayttaja.poistaUrheilu(juoksu11); 
    assertEquals("From: Kayttaja line: 315", 1, kayttaja.annaUrheilut(kesa1).size()); 
  } // Generated by ComTest END
}