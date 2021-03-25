package luokat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Lepplaju
 * @version 17.3.2021
 *
 */
public class Pvmt implements Iterable<Pvm>{

    private Collection<Pvm> paivamaarat = new ArrayList<Pvm>();
    
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
     * @param args not in use
     */
    public static void main(String[] args) {
        
        Pvmt peeveet = new Pvmt();
        Pvm tammikuu = new Pvm();
        tammikuu.tayta(1,1,2021);
        Pvm helmikuu = new Pvm(1,2,2021);
        Pvm maaliskuu = new Pvm(1,3,2021);

        peeveet.lisaa(tammikuu);
        peeveet.lisaa(helmikuu);
        peeveet.lisaa(maaliskuu);
        
     //   List<Pvm> paivamaarat2 = peeveet.annaPvt();
     //   
     //   for (Pvm peevee : paivamaarat2) {
     //       System.out.println(peevee);
     //   }

    }


}
