package luokat;

import fi.jyu.mit.ohj2.Mjonot;

/**Luokka kertoo lajin
 * @author Lepplaju
 * @version 8.3.2021
 *
 */
public class Laji {
    private static int seuraavaNro =1;
    private int lajiId;
    private String laji="";
   
    /**
     * alustaa lajin nollaksi
     */
    public Laji() {
        //
    }
    
    /**Aliohjelma, joka alustaa lajin
     * @param aktiviteetti antaa lajille nimen
     */
    public Laji(String aktiviteetti) {
        this.laji = aktiviteetti;
    }
    
    /**
     *  Asettaa id numeron lajille
     * @example
     * <pre name="test">
     * Laji nyrkkeily = new Laji();
     * Laji laskettelu = new Laji("lasketteluuuuuuuuuu");
     * nyrkkeily.getLajiId() === 0;
     * laskettelu.getLajiId() === 0;
     * nyrkkeily.rekisteroi();
     * laskettelu.rekisteroi();
     * nyrkkeily.getLajiId() === 1;
     * laskettelu.getLajiId() === 2;
     * laskettelu.toString() === "lasketteluuuuuuuuuu";
     * </pre>
     */
    public void rekisteroi() {
        this.lajiId = seuraavaNro++;
    }
    
    /**
     * T‰ytt‰‰ lajin nimen - oikeasti k‰ytt‰j‰n pit‰‰ antaa itse
     */
    public void taytaTiedot() {
        this.laji = "Punttisali " + rand(1,10);
    }
    
    @Override
    public String toString() {
        return "" + lajiId + "|" + laji;
    }
    
    /**Teksti joka n‰kyy k‰ytt‰j‰lle
     * @return lajin nimen
     */
    public String getLaji() {
        return this.laji;
    }
    
    /**Palauttaa lajin tunnusnumeron
     * @return tunnusnumero
     */
    public int getLajiId() {
        return this.lajiId; 
    }
    
    /**asettaa lajille id:n
     * @param id numero
     */
    public void setTunnusNro(int id) {
        lajiId=id;
        if (seuraavaNro<=id) seuraavaNro =id+1;
    }
      
    /**random number generator
     * @param ala alaraja
     * @param yla yl‰raja
     * @return satunnaisluvun
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
     }

    /**
     * @param rivi tiedoston rivi teksti‰
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb,'|',getLajiId()));
        laji = Mjonot.erota(sb,'|',laji);
        
    }



}
