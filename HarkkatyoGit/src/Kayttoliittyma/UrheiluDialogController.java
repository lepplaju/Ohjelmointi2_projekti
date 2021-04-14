package Kayttoliittyma;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import luokat.Urheilu;

/**
 * @author Lepplaju
 * @version 9.4.2021
 *
 */
public class UrheiluDialogController implements ModalControllerInterface<Urheilu>, Initializable{
    
    @FXML private GridPane gridUrheilu;
    @FXML private Label labelVirhe;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();      
    }
    
    @FXML private void handleOK() {
        ModalController.closeStage(labelVirhe);
    }

    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public Urheilu getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }
      
    @Override
    public void setDefault(Urheilu oletus) {
        urhKohdalla = oletus;
        naytaUrheilu(edits, urhKohdalla);
    }
    
    //=============================================
   
    private Urheilu urhKohdalla;
    private TextField[] edits;
    private static Urheilu apuUrheilu = new Urheilu();
    private int kentta = 0;
    
    
    private void alusta() {
        edits = luoKentat(gridUrheilu);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosUrheiluun((TextField)(e.getSource())));    
    }
    
    /**
     * K‰sitell‰‰n j‰seneen tullut muutos
     * @param edit muuttunut kentt‰
     */
    protected void kasitteleMuutosUrheiluun(TextField edit) {
        if (urhKohdalla == null) return;
        int k = getFieldId(edit,apuUrheilu.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = urhKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
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
     * Palautetaan komponentin id:st‰ saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mik‰ arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna 
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
    
    /**
     * Luodaan GridPaneen j‰senen tiedot
     * @param gridJasen mihin tiedot luodaan
     * @return luodut tekstikent‰t
     */
    public static TextField[] luoKentat(GridPane gridJasen) {
        gridJasen.getChildren().clear();
        TextField[] edits = new TextField[apuUrheilu.getKenttia()];
        
        for (int i=0, k = apuUrheilu.ekaKentta(); k < apuUrheilu.getKenttia(); k++, i++) {
            Label label = new Label(apuUrheilu.getKysymys(k));
            gridJasen.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridJasen.add(edit, 1, i);
        }
        return edits;
    }
    
    /**
     * N‰ytet‰‰n j‰senen tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeist‰ johon n‰ytet‰‰n
     * @param urh n‰ytett‰v‰ urheilu
     */
    public static void naytaUrheilu(TextField[] edits, Urheilu urh) {
        if (urh == null) return;
        for (int k = urh.ekaKentta(); k < urh.getKenttia(); k++) {
            edits[k].setText(urh.anna(k));
        }
    }


}

