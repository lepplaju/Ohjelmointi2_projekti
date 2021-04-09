package Kayttoliittyma;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
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
import luokat.Pvmt;
import luokat.SailoException;
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
        if (pvmKohdalla !=null )
            hae(pvmKohdalla.getTunnusNro());
        
    }
    
    
    @FXML private void HandleLopeta() {
        tallenna();
        Platform.exit();
    } 

    @FXML private void HandleTallenna() {
        tallenna();
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }


    @FXML
    void MuokkaaUrheilua(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata muokata urheilua viel‰");
    }

    @FXML
    void handleMuokkaaPvm(ActionEvent event) {
        muokkaaPvm();
    }

    @FXML
    void MuokkaaLajeja(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata muokata lajeja viel‰");
    }

    @FXML
    void UusiKentta(ActionEvent event) {
        Dialogs.showMessageDialog("ei osata lis‰t‰ uutta kentt‰‰");
    }

    @FXML
    void HandleUusiPvm(ActionEvent event) {
        uusiPvm();
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
    private String kayttajannimi="Late";

    /**
     * Alustaa tiedot yhteen isoon tekstikentt‰‰n
     */
    protected void alusta() {
        //panelTiedot.setContent(areaText);
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
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
   
    
    /**
     * @param nimi Lukee tiedoston
     * @return Palauttaa vireheen tai ei mit‰‰n
     */
    protected String lueTiedosto(String nimi) {
        kayttajannimi = nimi;
        setTitle("P‰iv‰kirja: K‰ytt‰j‰ - " + kayttajannimi);
        try {
            kayttaja.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }

    /**
     * Kysyt‰‰n tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = KayttajanNimiController.kysyNimi(null, kayttajannimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    /**
     * Saa tiedostosta luetut lajit n‰kyviin
     */
    public void getLajitNakyviin() {        
        for (int i =0; i<kayttaja.getLajiLkm(); i++) {
            Laji uusi = kayttaja.annaLaji(i);
            if (uusi!=null)
                cbLajit.add(uusi);    
        }
        
    }

    
    /**
     * N‰ytt‰‰ urheilun tiedot p‰iv‰m‰‰r‰n kohdalla
     */
    protected void naytaTiedot() {
        pvmKohdalla = chooserPvm.getSelectedObject();
        
        getLajitNakyviin();
        
        if (pvmKohdalla == null) {
            areaText.clear();
            return;
        }

        areaText.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaText)) {
            tulosta(os,pvmKohdalla);  
        }
            
    }
    
    private String tallenna() {
        try {
            kayttaja.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }


    
    /**Hakee t‰m‰n p‰iv‰m‰‰r‰n tiedot listaan
     * @param pvmId mik‰ on p‰iv‰n id
     */
    protected void hae(int pvmId) {
        chooserPvm.clear();
        int index=0;
        
        for (int i = 0; i < kayttaja.getEriPaivat(); i++) {
            Pvm pvm = kayttaja.annaPvm(i);
            if (pvm.getTunnusNro() == pvmId) index = i;
            chooserPvm.add(pvm.toString(), pvm);
        }
        chooserPvm.setSelectedIndex(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ j‰senen
    }
    
    /**
     * Lis‰t‰‰n uusi P‰iv‰m‰‰r‰
     */
    protected void uusiPvm() {
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
        try {
            kayttaja.lisaa(urh);
        } catch (Exception e) {
            Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! " + e.getMessage());
        } 
        hae(pvmKohdalla.getTunnusNro());          
    }
    
    /**
     * @param kayttaja k‰ytt‰j‰ jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        naytaTiedot();
    }

    /**
     * Avaa PvmDialogin ja sen sis‰ll‰ pystyt‰‰n muokkaamaan p‰iv‰m‰‰r‰‰
     */
    public void muokkaaPvm() {
        PvmDialogController.kysyPvm(null,pvmKohdalla);
    }

    /**
     * @param os Tietovirta
     * @param pvm Mik‰ p‰iv‰m‰‰r‰
     */
    public void tulosta(PrintStream os, final Pvm pvm){
        os.println("----------------------------------------------");
        pvm.tulosta(os);
        os.println("----------------------------------------------");
        try {
            List<Urheilu> urheilut = kayttaja.annaUrheilut(pvm);   
            for (Urheilu urh : urheilut)
                urh.tulosta(os);  
        } catch (Exception ex) {
            Dialogs.showMessageDialog("ERROR" + ex.getMessage());
        }
    }

}
