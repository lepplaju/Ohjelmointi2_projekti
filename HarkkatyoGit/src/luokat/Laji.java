package luokat;


/**Luokka kertoo lajin
 * @author Lepplaju
 * @version 8.3.2021
 *
 */
public class Laji {
    private static int seuraavaNro =1;
    private int lajiId=0;
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
        this.laji = "Lajin nimi";
    }
    
    @Override
    public String toString() {
        return this.laji;
    }
    
    /**Teksti joka n‰kyy k‰ytt‰j‰lle
     * @return lajin nimen
     */
    public String getLaji() {
        return laji;
    }
    
    /**Palauttaa lajin tunnusnumeron
     * @return tunnusnumero
     */
    public int getLajiId() {
        return this.lajiId; 
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

}
