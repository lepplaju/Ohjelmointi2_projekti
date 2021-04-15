package luokat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**Urheilut - luokka, joka pit‰‰ yll‰ Urheilu-luokan taulukkoa
 * @author Lepplaju
 * @version 9.3.2021
 *
 */
public class Urheilut implements Iterable<Urheilu> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";
    private Collection<Urheilu> alkiot = new ArrayList<Urheilu>();
    
    /**antaa kaikki urheilut tietylt‰ p‰iv‰m‰‰r‰lt‰
     * @param pvmTunnusNro p‰iv‰m‰‰r‰n tunnusnumero
     * @return kaikki tietyn p‰iv‰n urheilusuoritukset
     */
    public List<Urheilu> annaUrheilut(int pvmTunnusNro){
        List<Urheilu> loydetyt = new ArrayList<Urheilu>();
                 for (Urheilu urh : alkiot)
                     if (urh.getPvmNro() == pvmTunnusNro) loydetyt.add(urh);
                 return loydetyt;
             
    }


    @Override
    public Iterator<Urheilu> iterator() {
        return alkiot.iterator();
    }


    /**lis‰t‰‰n listaan uusi urheilu
     * @param urh urheilun tiedot
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     *
     * Urheilut urheilutX = new Urheilut();
     * Urheilu sali1 = new Urheilu(1);
     * urheilutX.lisaa(sali1);
     * Urheilu lenkki1 = new Urheilu(2);
     * Urheilu lenkki2 = new Urheilu(2); 
     * urheilutX.lisaa(lenkki1);
     * urheilutX.lisaa(lenkki2);
     * Urheilu sali2 = new Urheilu(1); 
     * urheilutX.lisaa(sali2);
     * Urheilu latka1 = new Urheilu(2);
     * urheilutX.lisaa(latka1);
     * 
     * Iterator<Urheilu> i2=urheilutX.iterator();
     * i2.next() === sali1;
     * i2.next() === lenkki1;
     * i2.next() === lenkki2;
     * i2.next() === sali2;
     * i2.next() === latka1;

     * </pre>
     */
    public void lisaa(Urheilu urh) {
        alkiot.add(urh);
        muutettu=true;
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */

    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    /**
     * @param tied tiedoston nimi
     * @throws SailoException jos tiedoston lukeminen ep‰onnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Urheilut urheilut = new Urheilut();
     *  Urheilu urh1 = new Urheilu();
     *  Urheilu urh2 = new Urheilu();
     *  urh1.taytaTiedot();
     *  urh2.taytaTiedot();
     *  urh1.rekisteroi();
     *  urh2.rekisteroi();
     *  String hakemisto = "testikayttaja";
     *  String tiedNimi = hakemisto+"/urheilut";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  urheilut.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  urheilut.lisaa(urh1);
     *  urheilut.lisaa(urh2);
     *  urheilut.tallenna();
     *  urheilut = new Urheilut();            // Poistetaan vanhat luomalla uusi
     *  urheilut.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Urheilu> i = urheilut.iterator();
     *  i.next().toString() === urh1.toString();
     *  i.next().toString() === urh2.toString();
     *  i.hasNext() === false;
     *  urheilut.lisaa(urh2);
     *  urheilut.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            
            String rivi;

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;               
                Urheilu urheilu = new Urheilu();
                urheilu.parse(rivi); // voisi olla virhek‰sittely
                lisaa(urheilu);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**Asettaa tiedoston nimen
     * @param nimi parametrina
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */

    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */

    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    /**
     * tallentaa tiedoston
     * @throws SailoException poikkeus
     */
    public void tallenna() throws SailoException {
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimet‰");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Urheilu urh : this) {
                fo.println(urh.toString());
            }
            //} catch ( IOException e ) { // ei heit‰ poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    /**
     * Palauttaa kerhon harrastusten lukum‰‰r‰n
     * @return urheilujen lukum‰‰r‰n
     */
    public int getLkm() {
        return alkiot.size();
    }

    
}
