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
    
    /** Lisää päivämäärän, päivämäärät-listaan
     * @param paiv Pvm
     */
    public void lisaa(Pvm paiv) {
        paivamaarat.add(paiv);
    }
    
    /**Palauttaa listassa olevien kirjauksien lukumäärän
     * @return alkioiden lkm
     */
    public int getPvmLkm() {
        return paivamaarat.size();
    }
    
    @Override
    public Iterator<Pvm> iterator() {
        return paivamaarat.iterator();
    }
    
    /**Tulostaa annetulla tunnusnumerolla olevan päivämäärän listasta
     * @param tunnusnro tietyn päivämäärän oma tunnusnumero
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
