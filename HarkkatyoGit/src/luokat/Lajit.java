package luokat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "lajit";

   
    
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
        muutettu = true;
    }

    /**Lukee Lajit tiedostosta
     * @param tied tiedoston nimi
     * @throws SailoException jos lukeminen ep‰onnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Laji laj = new Laji();
                laj.parse(rivi); // voisi olla virhek‰sittely
                lisaa(laj);
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
    
    /**Tallentaa Lajit tiedostoon
     * @throws SailoException poikkeus
     */
    public void tallenna() throws SailoException {

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimet‰");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Laji laj : lajit) {
                if (laj!=null) fo.println(laj.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }

    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }

    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }

    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
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
    
    /**P‰‰ohjelma tallentamisen testaamiseksi
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        //
    }
    
}
