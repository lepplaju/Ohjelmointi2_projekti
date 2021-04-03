package luokat;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Lepplaju
 * @version 1.0, 2.4.2021
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa
     * k�ytett�v� viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
