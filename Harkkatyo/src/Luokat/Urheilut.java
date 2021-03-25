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
     */
    public void lisaa(Urheilu urh) {
        alkiot.add(urh);
        
    }
    
}
