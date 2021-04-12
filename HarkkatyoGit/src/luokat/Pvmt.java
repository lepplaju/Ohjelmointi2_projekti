package luokat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Kayttoliittyma.PvmDialogController;

/**
 * @author Lepplaju
 * @version 17.3.2021
 *
 */
public class Pvmt implements Iterable<Pvm>{

    private boolean muutettu = false;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "pvmt";
    private final List<Pvm> paivamaarat = new ArrayList<Pvm>();
    
    /** Lis‰‰ p‰iv‰m‰‰r‰n, p‰iv‰m‰‰r‰t-listaan
     * @param paiv Pvm
     * @example
     * <pre name="test">
     * Pvmt paivamaaratX = new Pvmt();
     * Pvm paiva11 = new Pvm(1,1,1);
     * Pvm paiva22 = new Pvm();
     * paiva11.rekisteroi();
     * paiva22.rekisteroi();
     * paivamaaratX.getPvmLkm() === 0;
     * paivamaaratX.lisaa(paiva11);
     * paivamaaratX.lisaa(paiva22);
     * paivamaaratX.getPvmLkm() ===2;
     * paivamaaratX.annaPvm(1) === paiva11;
     * paivamaaratX.annaPvm(2) === paiva22;
     * </pre>
     */
    public void lisaa(Pvm paiv) {
        paivamaarat.add(paiv);
        muutettu = true;
    }
    
    /**Palauttaa listassa olevien kirjauksien lukum‰‰r‰n
     * @return alkioiden lkm
     */
    public int getPvmLkm() {
        return paivamaarat.size();
    }
    
    @Override
    public Iterator<Pvm> iterator() {
        return paivamaarat.iterator();
    }
    
    /**Tulostaa annetulla tunnusnumerolla olevan p‰iv‰m‰‰r‰n listasta
     * @param tunnusnro tietyn p‰iv‰m‰‰r‰n oma tunnusnumero
     * @return Pvm
     */
    public Pvm annaPvm(int tunnusnro) {
        for (Pvm peevee : paivamaarat) {
            if (peevee.getTunnusNro() == tunnusnro) return peevee;            
        }
        return null;
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien j‰senten viitteet 
     * @param hakuehto hakuehto  
     * @return kaikki lˆytyneet p‰iv‰m‰‰r‰t
     */ 
    @SuppressWarnings("unused")
    public Collection<Pvm> etsi(String hakuehto) { 
        Collection<Pvm> loytyneet = new ArrayList<Pvm>(); 
        for (Pvm pvm : this) { 
            loytyneet.add(pvm);  
        } 
        return loytyneet; 
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
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Ei ole nime‰");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Error");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehd‰‰n jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;               
                Pvm pvm = new Pvm();
                pvm.parse(rivi); // voisi olla virhek‰sittely
                lisaa(pvm);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    /**
     * Tallentaa j‰senistˆn tiedostoon.  Kesken.
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    /**Korvataan tai lis‰t‰‰n uusi p‰iv‰m‰‰r‰.
     * @param pvm mit‰ p‰iv‰m‰‰r‰‰ muutetaan
     */
    public void korvaaTaiLisaa(Pvm pvm) {      
        int id = pvm.getTunnusNro();
        for (int i = 0; i<getPvmLkm(); i++) {
            if (paivamaarat.get(i).getTunnusNro()==id) {
                paivamaarat.set(i,pvm);
                muutettu=true;
                return;
            }
        }
        lisaa(pvm);
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
            fo.println(getKokoNimi());
            fo.println(getPvmLkm());
            for (Pvm pvm : this) {
                fo.println(pvm.toString());
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
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    
    /**
     * @param args not in use
     * @throws SailoException poikkeus
     */
    public static void main(String[] args) throws SailoException {
        Pvmt testipaivat = new Pvmt();
        
        testipaivat.lueTiedostosta("pvmt");
        
        Pvm nyt = new Pvm();
        Pvm tammikuu = new Pvm();
        Pvm helmikuu = new Pvm();
        
        nyt.taytaTiedot();
        helmikuu.taytaTiedot();
        tammikuu.taytaTiedot();
        tammikuu.rekisteroi();
        
        try {
            System.out.println("pvm-luokan testi ========================================");
            
           testipaivat.lisaa(tammikuu);
            
            for (int i = 1; i < testipaivat.getPvmLkm(); i++) {
                Pvm pvm = testipaivat.annaPvm(i);
                System.out.println("Pvm Id: " + pvm.getTunnusNro());
                pvm.tulosta(System.out);
            } 
            
        } catch (Exception e) {
            System.out.println("Virhe " + e.getMessage());
        }
        
        testipaivat.tallenna();
        }


    }
