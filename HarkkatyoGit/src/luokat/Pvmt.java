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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import fi.jyu.mit.ohj2.WildChars;

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
     * paivamaaratX.annaPvm(paiva11.getTunnusNro()) === paiva11;
     * paivamaaratX.annaPvm(paiva22.getTunnusNro()) === paiva22;
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
    
    /** Etsit‰‰n listaan hakuehdon perusteella, eli p‰iv‰m‰‰r‰n perusteella
     * @param hakuEhto tekstikent‰n sis‰ltˆ
     * @return palauttaa kaikki, jotka t‰ytt‰v‰t hakuehdon
     */
    public Collection <Pvm> etsi(String hakuEhto){
        String ehto ="*";
        if(hakuEhto!=null&& hakuEhto.length()>0)ehto = hakuEhto;
        List<Pvm> loytyneet = new ArrayList<Pvm>();
        
        for(Pvm pv:this) {
            if (WildChars.onkoSamat(pv.getPaivays(), ehto)) loytyneet.add(pv);
        }
        Collections.sort(loytyneet);
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
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Pvmt pvmt = new Pvmt();
     *  Pvm paiva1 = new Pvm();
     *  Pvm paiva2 = new Pvm();
     *  paiva1.taytaTiedot();
     *  paiva2.taytaTiedot();
     *  paiva1.rekisteroi();
     *  paiva2.rekisteroi();
     *  String hakemisto = "testikayttaja";
     *  String tiedNimi = hakemisto+"/pvmt";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  pvmt.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  pvmt.lisaa(paiva1);
     *  pvmt.lisaa(paiva2);
     *  pvmt.tallenna();
     *  pvmt = new Pvmt();            // Poistetaan vanhat luomalla uusi
     *  pvmt.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Pvm> i = pvmt.iterator();
     *  i.next().equals(paiva1);
     *  i.next().equals(paiva2);
     *  i.hasNext() === false;
     *  pvmt.lisaa(paiva2);
     *  pvmt.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
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
     * Palauttaa K‰ytt‰j‰n koko nimen
     * @return k‰ytt‰j‰n koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    /**poistaa tietyn pvm:n 
     * @param pvm , jota etsit‰‰n
     * @return true jos lˆydettiin
     */
    public boolean poistaPvm(Pvm pvm) {
        boolean ret = paivamaarat.remove(pvm);
        if (ret==true) muutettu = true;
        return ret;
    }

    /**Etsii pvmn idn perustella
     * @param id tunnusnumero, jonka avulla etsit‰‰n
     * @return j‰senen indeksi tai -1 jos ei lˆytynyt
     */
    public int etsiId(int id) {
        for (int i = 0; i<getPvmLkm(); i++)
            if (paivamaarat.get(i).getTunnusNro()==id) return i;
        return -1;
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
