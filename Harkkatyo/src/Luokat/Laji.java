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
        this.lajiId = 0;
    }
    
    /**
     *  Asettaa id numeron lajille
     */
    public void rekisteroi() {
        this.lajiId = seuraavaNro++;
    }
    
    /**alustaa lajin annetuilla tiedoilla
     * @param lajiIidee mikä lajin id-numero
     */
    public Laji(int lajiIidee) {
        lajiId=lajiIidee;
    }
    
    @Override
    public String toString() {
        return this.laji;
    }
    
    /**Teksti joka näkyy käyttäjälle
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
     * @param yla yläraja
     * @return satunnaisluvun
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
     }

}
