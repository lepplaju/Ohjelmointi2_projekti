package Kayttoliittyma;

import java.io.PrintStream;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import luokat.Kayttaja;
import luokat.Laji;
import luokat.Pvm;
import luokat.Urheilu;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List; 
import java.util.ResourceBundle;



/**
 * @author Lepplaju
 * @version 9.3.2021
 *
 */
public class PaivakirjaGUIController implements Initializable{
      
    @FXML private TextField hakuehto;
    @FXML private ComboBoxChooser<Laji> cbLajit;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelTiedot;
    @FXML private ListChooser<Pvm> chooserPvm;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }

    @FXML private void handleHakuehto() {
        String hakukentta = cbLajit.getSelectedText();
        String ehto = hakuehto.getText(); 
        if ( ehto.isEmpty() )
            naytaVirhe(null);
        else
            naytaVirhe("Ei osata viel‰ hakea " + hakukentta + ": " + ehto);
    }



    @FXML
    void MuokkaaKenttia(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata muokata kentti‰ viel‰");
    }

    @FXML
    void MuokkaaKirjausta(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata muokata kirjausta viel‰");
    }

    @FXML
    void MuokkaaLajeja(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata muokata lajeja viel‰");
    }

    @FXML
    void Tallenna(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata tallentaa");
    }

    @FXML
    void UusiKentta(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata lis‰t‰ uutta kentt‰‰");
    }

    @FXML
    void HandleUusiKirjaus(ActionEvent event) {
        uusiKirjaus();
    }

    @FXML
    void HandleUusiUrheilu(ActionEvent event) {
        uusiUrheilu();
    }
    
    @FXML
    void HandleUusiLaji(ActionEvent event) {
        uusiLaji();
    }
    
    
//===========================================================================================
// T‰st‰ eteenp‰in ei k‰yttˆliittym‰‰n suoraan liittyv‰‰ koodia

    private Kayttaja kayttaja = new Kayttaja();
    private Pvm pvmKohdalla;
    private TextArea areaText = new TextArea();

    /**
     * Alustaa tiedot yhteen isoon tekstikentt‰‰n
     */
    protected void alusta() {
        panelTiedot.setContent(areaText);
        areaText.setFont(new Font("Courier New", 12));
        panelTiedot.setFitToHeight(true);
        
        chooserPvm.clear();
        chooserPvm.addSelectionListener(e -> naytaTiedot());
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
     * 
     */
    protected void naytaTiedot() {
        pvmKohdalla = chooserPvm.getSelectedObject();

        if (pvmKohdalla == null) return;

        areaText.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaText)) {
            tulosta(os,pvmKohdalla);  
        }
    }
    
    /**Hakee t‰m‰n p‰iv‰m‰‰r‰n tiedot listaan
     * @param pvmId mik‰ on p‰iv‰n id
     */
    protected void hae(int pvmId) {
        chooserPvm.clear();

        int index = 0;
        for (int i = 0; i < kayttaja.getEriPaivat(); i++) {
            Pvm pvm = kayttaja.annaPvm(i);
            if (pvm.getTunnusNro() == pvmId) index = i;
            chooserPvm.add("aa", pvm);
        }
        chooserPvm.setSelectedIndex(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ j‰senen
    }
    
    /**
     * Lis‰t‰‰n uusi P‰iv‰m‰‰r‰
     */
    protected void uusiKirjaus() {
        Pvm uusi = new Pvm();
        uusi.rekisteroi();
        uusi.taytaTiedot();
        try {
            kayttaja.lisaa(uusi);
        } catch (Exception e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    }  
    
    /**
     * Lis‰‰ uuden lajin
     */
    public void uusiLaji() {
        Laji uusi = new Laji();
        uusi.rekisteroi();
        uusi.taytaTiedot();       
        try {
            kayttaja.lisaa(uusi);
        } catch (Exception e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        cbLajit.add(uusi);
    }
    
    /** 
     * Tekee uuden tyhj‰n harrastuksen editointia varten 
     */ 
    public void uusiUrheilu() { 
        if ( pvmKohdalla == null ) return;  
        Urheilu urh = new Urheilu();  
        urh.rekisteroi();  
        urh.taytaTiedot(pvmKohdalla.getTunnusNro());  
        kayttaja.lisaa(urh);  
        hae(pvmKohdalla.getTunnusNro());          
    }


    /**
     * @param os Tietovirta
     * @param pvm Mik‰ p‰iv‰m‰‰r‰
     */
    public void tulosta(PrintStream os, final Pvm pvm) {
        os.println("----------------------------------------------");
        pvm.tulosta(os);
        os.println("----------------------------------------------");
        List<Urheilu> urheilut = kayttaja.annaUrheilut(pvm);   
        for (Urheilu urh : urheilut)
            urh.tulosta(os);  
    }





}
