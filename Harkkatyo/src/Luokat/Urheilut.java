package Luokat;

public class Urheilut {

    private static final int MAX_ALKIOTA=20;
    private int seuraava;
    private Urheilu[] alkiot;
    
    public Urheilut() {
        alkiot = new Urheilu[MAX_ALKIOTA];
    }
    
    public void lisaa(Urheilu urheilu)  {
        if ( seuraava >= alkiot.length) return;
        this.alkiot[this.seuraava] = urheilu;
        seuraava++;
    }

    
    public static void main(String[] args) {
        Urheilut urheilut = new Urheilut();
        Urheilu jaakiekko1 = new Urheilu();
        jaakiekko1.taytatiedot();
        urheilut.lisaa(jaakiekko1);


    }
    
}
