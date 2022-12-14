package luokat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**Urheilut - luokka, joka pit?? yll? Urheilu-luokan taulukkoa
 * @author Lepplaju
 * @version 9.3.2021
 *
 */
public class Urheilut implements Iterable<Urheilu> {

    private double KAtyytyvaisyys;
    private double KAintensity;
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";
    private List<Urheilu> alkiot = new ArrayList<Urheilu>();
    
    /**antaa kaikki urheilut tietylt? p?iv?m??r?lt?
     * @param pvmTunnusNro p?iv?m??r?n tunnusnumero
     * @return kaikki tietyn p?iv?n urheilusuoritukset
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


    /**lis?t??n listaan uusi urheilu
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
     * i2.next().equals(sali1);
     * i2.next().equals(lenkki1);
     * i2.next().equals(lenkki2);
     * i2.next().equals(sali2);
     * i2.next().equals(latka1);

     * </pre>
     */
    public void lisaa(Urheilu urh) {
        alkiot.add(urh);
        muutettu=true;
        lasketyytyvaisyys();
        laskeintensity();
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
     * @throws SailoException jos tiedoston lukeminen ep?onnistuu
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
     *  i.next().toString().equals(urh1.toString());
     *  i.next().toString().equals(urh2.toString());
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
                urheilu.parse(rivi); // voisi olla virhek?sittely
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
     * Luetaan aikaisemmin annetun nimisest? tiedostosta
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
     * Palauttaa tiedoston nimen, jota k?ytet??n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Palauttaa tiedoston nimen, jota k?ytet??n tallennukseen
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
        if (muutettu == false) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimet?");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Urheilu urh : this) {
                fo.println(urh.toString());
            }
            //} catch ( IOException e ) { // ei heit? poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Palauttaa kerhon harrastusten lukum??r?n
     * @return urheilujen lukum??r?n
     */
    public int getLkm() {
        return alkiot.size();
    }


    /** Korvataan tai lis?t??n uusi urheilu
     * @param urh urheilu, jonka tietoja muokataan
     */
    public void korvaaTaiLisaa(Urheilu urh) {
        int id = urh.getTunnusNro();
        for (int i = 0; i<alkiot.size(); i++) {
            if (alkiot.get(i).getTunnusNro()==id) {
                alkiot.set(i,urh);
                muutettu=true;
                return;       
            }
        }
        lisaa(urh);
    }

    /**Poistetaan p?iv?m??r??n liittyv?t urheilut
     * @param pvmId - mink? p?iv?m??r?n kaikki merkinn?t halutaan poista
     * @return kuinka monta urheilua poistettiin
     */
    public int poistaPvmUrheilut(int pvmId) {
        int n = 0;
        for (Iterator<Urheilu> it = alkiot.iterator(); it.hasNext();) {
            Urheilu urh = it.next();
            if(urh.getPvmNro()==pvmId) {
                it.remove();
                n++;
            }
        }
        if (n>0)muutettu = true;
        return n;
    }

    /**Poistetaan yksitt?inen urheilu
     * @param urh kirjaus, joka halutaan poistaa
     * @return true jos poistettiin
     */
    public boolean poistaUrheilu(Urheilu urh) {
        boolean ret = alkiot.remove(urh);
        if (ret==true) muutettu = true;
        return ret;
    }

    /**palauttaa apumuuttujan, jossa on tyytyv?isyyden keskiarvo kaikista 
     * urheilu-kirjauksista
     * @return tyytyv?isyyden keskiarvon
     */
    public double getKAtyytyvaisyys() {
        return KAtyytyvaisyys;
    }
    

    /**palauttaa apumuuttujan, jossa on intensiteetin keskiarvo
     * @return intensityn keskiarvo
     */
    public double getKAintensiteetti() {
        return KAintensity;
    }
    
    /**
     * Laskee tyytyv?isyyden KAtyytyv?isyys-muuttujaan
     */
    public void lasketyytyvaisyys() {
        double KA=0;
        for (Iterator<Urheilu> it = alkiot.iterator(); it.hasNext();) {
            Urheilu urh = it.next();
            KA+=urh.getTyytyvaisyys();
        }
        KAtyytyvaisyys = KA/alkiot.size();
    }

    /**
     * Laskee intensiteetin KAintensity muuttujaan
     */
    public void laskeintensity() {
        double KA=0;
        for (Iterator<Urheilu> it = alkiot.iterator(); it.hasNext();) {
            Urheilu urh = it.next();
            KA+=urh.getIntensity();
        }
        KAintensity = KA/alkiot.size();
    }
    
}
