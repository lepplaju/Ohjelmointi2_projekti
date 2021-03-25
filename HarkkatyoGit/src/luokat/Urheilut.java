package luokat;

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
        
    }
    
}
