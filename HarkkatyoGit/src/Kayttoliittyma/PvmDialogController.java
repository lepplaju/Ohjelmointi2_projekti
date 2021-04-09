package Kayttoliittyma;

import java.net.URL;
import java.util.ResourceBundle;

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
    
    /**
     * Mit‰ tapahtuu kun painetaan ok
     */
    @FXML public void handleOK() {
        ModalController.closeStage(labelVirhe);
    }
    
    /**
     * Mit‰ tapahtuu kun painetaan cancel
     */
    @FXML public void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public Pvm getResult() {
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }
    
    //====================================================

    private Pvm pvmKohdalla;
    
    private void naytaPvm(Pvm pvm) {
        if (pvm == null) return;
        editPaivays.setText(pvm.getPaivays());
    }
    
    /**P‰iv‰m‰‰r‰n muokkausDialogi
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mink‰ p‰iv‰m‰‰r‰n kohdalla ollaan, eli mit‰ halutaan muokata
     * @return p‰iv‰m‰‰r‰n, jos sit‰ on muokattu
     */
    public static Pvm kysyPvm(Stage modalityStage, Pvm oletus) {
        return ModalController.showModal(PaivakirjaGUIController.class.getResource("PvmDialogView.fxml"), "Pvm", modalityStage, oletus);
        
    }
    
}
