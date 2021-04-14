package Kayttoliittyma;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
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
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelTiedot;
    @FXML private ComboBoxChooser<Laji> cbLajit;
    @FXML private ListChooser<Pvm> chooserPvm;
    @FXML private StringGrid<Urheilu>urheiluTable;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }

    @FXML private void handleHakuehto() {
        if (pvmKohdalla !=null ) hae(pvmKohdalla.getTunnusNro());       
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

    @FXML private void MuokkaaUrheilua() {
        muokkaaUrheilu();
    }

    @FXML private void handleMuokkaaPvm() {
        muokkaaPvm();
    }

    @FXML private void MuokkaaLajeja() {
        Dialogs.showMessageDialog("ei osata muokata lajeja vielä");
    }

    @FXML private void UusiKentta() {
        Dialogs.showMessageDialog("ei osata lisätä uutta kenttää");
    }

    @FXML private void HandleUusiPvm() {
        uusiPvm();
    }

    @FXML private void HandleUusiUrheilu() {
        uusiUrheilu();
    }
    
    @FXML private void HandleUusiLaji() {
        uusiLaji();
    }
    
    @FXML private void HandlePoistaPvm() {
        Dialogs.showMessageDialog("ei osata poistaa");
    }
    
    
//===========================================================================================
// Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia

    private Kayttaja kayttaja = new Kayttaja();
    private Pvm pvmKohdalla;
    private String kayttajannimi="Late";
    private static Urheilu apuUrheilu = new Urheilu();
    Urheilu urhKohdalla;

    /**
     * Alustaa tiedot yhteen isoon tekstikenttään
     */
    protected void alusta() {
        panelTiedot.setFitToHeight(true);
        
        chooserPvm.clear();
        chooserPvm.addSelectionListener(e -> naytaTiedot());
        
     // alustetaan harrastustaulukon otsikot 
        int eka = apuUrheilu.ekaKentta(); 
        int lkm = apuUrheilu.getKenttia(); 
        String[] headings = new String[lkm-eka]; 
        for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apuUrheilu.getKysymys(k); 
        urheiluTable.initTable(headings); 
        urheiluTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        urheiluTable.setEditable(false); 
        urheiluTable.setPlaceholder(new Label("Ei kirjattuja urheiluita tälle päivälle")); 

    }

    /**
     * Näyttää urheilun tiedot päivämäärän kohdalla
     */
    protected void naytaTiedot() {
        pvmKohdalla = chooserPvm.getSelectedObject();  
        if (pvmKohdalla == null) return;
        naytaUrheilut(pvmKohdalla);
        
        urheiluTable.setOnMouseClicked( e -> {
        int r = urheiluTable.getRowNr();
        urhKohdalla = urheiluTable.getObject(r);
        if (  e.getClickCount() == 2)  muokkaaUrheilu();
                });
        
    }
    
    private void naytaUrheilut(Pvm pvm) {
        urheiluTable.clear();
        if (pvm == null)return;
        
        try {
            List<Urheilu> urheilut = kayttaja.annaUrheilut(pvm);
            if (urheilut.size()==0) return;
            for (Urheilu urh:urheilut) {
                naytaUrheilu(urh);
            }
        } catch (Exception e) {
            //
        }
              
    }
    
    private void naytaUrheilu(Urheilu urh) {
        int kenttia = urh.getKenttia();
        String[] rivi = new String[kenttia-urh.ekaKentta()];
        for (int i = 0, k=urh.ekaKentta(); k<kenttia; i++, k++) {
            rivi[i] = urh.anna(k);
        }        
        urheiluTable.add(urh,rivi);
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
     * @return Palauttaa vireheen tai ei mitään
     */
    protected String lueTiedosto(String nimi) {
        kayttajannimi = nimi;
        setTitle("Päiväkirja: Käyttäjä - " + kayttajannimi);
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
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = KayttajanNimiController.kysyNimi(null, kayttajannimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    /**
     * Saa tiedostosta luetut lajit näkyviin
     */
    public void getLajitNakyviin() {        
        for (int i =0; i<kayttaja.getLajiLkm(); i++) {
            Laji uusi = kayttaja.annaLaji(i);
            if (uusi!=null)
                cbLajit.add(uusi);    
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


    
    /**Hakee tämän päivämäärän tiedot listaan
     * @param pvmId mikä on päivän id
     */
    protected void hae(int pvmId) {
        chooserPvm.clear();
        int index=0;
        
        for (int i = 0; i < kayttaja.getEriPaivat(); i++) {
            Pvm pvm = kayttaja.annaPvm(i);
            if (pvm.getTunnusNro() == pvmId) index = i;
            chooserPvm.add(pvm.toString(), pvm);
        }
        chooserPvm.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
    
    /**
     * Lisätään uusi Päivämäärä
     */
    protected void uusiPvm() {
        try {
            Pvm uusi = new Pvm();
            uusi = PvmDialogController.kysyPvm(null, uusi);
            if(uusi==null) return;
            uusi.rekisteroi();
            kayttaja.lisaa(uusi);
            hae(uusi.getTunnusNro());
        }catch(Exception e) {
            Dialogs.showMessageDialog("uuden luomnen epäonnistui" + e);
            return;
        }
    }
    
    /**
     * Lisää uuden lajin
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
     * Tekee uuden tyhjän harrastuksen editointia varten 
     */ 
    public void uusiUrheilu() { 
        if ( pvmKohdalla == null ) return;  
        Urheilu urh = new Urheilu();  
        urh.rekisteroi();  
        urh.taytaTiedot(pvmKohdalla.getTunnusNro());  
        try {
            kayttaja.lisaa(urh);
        } catch (Exception e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        } 
        hae(pvmKohdalla.getTunnusNro());          
    }
    
    /**
     * @param kayttaja käyttäjä jota käytetään tässä käyttöliittymässä
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        naytaTiedot();
    }

    /**
     * Avaa PvmDialogin ja sen sisällä pystytään muokkaamaan päivämäärää
     */
    public void muokkaaPvm() {
        if (pvmKohdalla == null) return;
        try {
            Pvm pvm = pvmKohdalla.clone();
            pvm = PvmDialogController.kysyPvm(null,pvm);
            if (pvm == null) return;
            kayttaja.korvaaTaiLisaa(pvm);
            hae(pvm.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    /**
     *  Avaa UrheiluDialogin ja sen sisällä pystytään muokkaamaan urheilun tietoja 
     */
    public void muokkaaUrheilu(){       
        ModalController.showModal(UrheiluDialogController.class.getResource("UrheiluDialogView.fxml"),
                "Urheilun muokkaaminen", null, urhKohdalla);
        
    }
    

    /**
     * @param os Tietovirta
     * @param pvm Mikä päivämäärä
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
