package Kayttoliittyma;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import luokat.Pvm;

/**
 * @author Lepplaju
 * @version 8.4.2021
 *
 */
public class PvmDialogController implements ModalControllerInterface<Pvm>, Initializable {

    @FXML private Label labelVirhe;
    @FXML private TextField editPaivays;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }

    /**
     * Mit‰ tapahtuu kun painetaan ok
     */
    @FXML public void handleOK() {
        String virhe=pvmKohdalla.aseta(editPaivays.getText());
        if (pvmKohdalla != null && pvmKohdalla.getPaivays().trim().equals("")) {
                naytaVirhe("ei saa olla tyhj‰");
                return;           
        }
        if (virhe==null) palautettava=pvmKohdalla; ModalController.closeStage(labelVirhe);
    }
    
    /**
     * Mit‰ tapahtuu kun painetaan cancel
     */
    @FXML public void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public Pvm getResult() {       
        return palautettava;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(Pvm oletus) {
        pvmKohdalla = oletus;
        naytaPvm(pvmKohdalla);
    }
    

    
    //====================================================

    private Pvm pvmKohdalla;
    private Pvm palautettava;
    
    /**
     *  Alustaa dialogin
     */
    protected void alusta() {
        editPaivays.setOnKeyReleased(e-> kasitteleMuutosPvm((TextField)(e.getSource())));
    }
    
    /**
     * N‰ytt‰‰ p‰iv‰m‰‰r‰n muokkausikkunassa
     * @param pvm mik‰ p‰iv‰m‰‰r‰ on kyseess‰
     */
    private void naytaPvm(Pvm pvm) {
        if (pvm.getTunnusNro() != 0)
            editPaivays.setText(pvm.getPaivays());
        else {
            pvm.taytaTanaan();
            editPaivays.setText(pvm.getPaivays());
        }
    }
    
    /**
     * N‰ytt‰‰ poikkeavan syntaksin
     * @param virhe aputeksti, joka halutaan n‰ytt‰‰ ruudulla
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }   

    /**
     * Asettaa p‰iv‰m‰‰r‰lle uuden arvon
     * @param edit muutoskent‰n sis‰ltˆ
     */
    private void kasitteleMuutosPvm(TextField edit) {
        if (pvmKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = pvmKohdalla.aseta(s);
        if (virhe==null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);

        }
    }
    
    /**P‰iv‰m‰‰r‰n muokkausDialogi
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mink‰ p‰iv‰m‰‰r‰n kohdalla ollaan, eli mit‰ halutaan muokata
     * @return p‰iv‰m‰‰r‰n, jos sit‰ on muokattu
     */
    public static Pvm kysyPvm(Stage modalityStage, Pvm oletus) {
        return ModalController.showModal(PaivakirjaGUIController.class.getResource("PvmDialogView.fxml"),
                "Kayttaja", modalityStage, oletus);
        
    }

    
}
