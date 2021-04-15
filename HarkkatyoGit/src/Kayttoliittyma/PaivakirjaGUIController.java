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
 * @version 15.4.2021
 * Tyˆ 7
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
        Dialogs.showMessageDialog("ei osata muokata lajeja viel‰");
    }

    @FXML private void UusiKentta() {
        Dialogs.showMessageDialog("ei osata lis‰t‰ uutta kentt‰‰");
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
// T‰st‰ eteenp‰in ei k‰yttˆliittym‰‰n suoraan liittyv‰‰ koodia

    private Kayttaja kayttaja = new Kayttaja();
    private Pvm pvmKohdalla;
    private String kayttajannimi="Late";
    private static Urheilu apuUrheilu = new Urheilu();
    Urheilu urhKohdalla;

    /**
     * Alustaa  p‰iv‰m‰‰rien tiedot omaan listaan ja 
     * urheilujen tiedot tiedot StringGridiin
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
        urheiluTable.setPlaceholder(new Label("Ei kirjattuja urheiluita t‰lle p‰iv‰lle")); 

    }

    /**
     * N‰ytt‰‰ p‰iv‰m‰‰r‰‰n kuuluvat urheilut ja
     * kuuntelee hiirt‰ ja jos jotakin urheilua tuplaklikataan, avaa muokkausikkunan 
     * 
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
    
    /**
     * N‰ytt‰‰ tietyn p‰iv‰n urheilut
     * @param pvm tietty p‰iv‰m‰‰r‰
     */
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
    
    /**
     * N‰ytt‰‰ yksitt‰isen urheilun ja lis‰‰ tiedot automaattisesti StringGridiin
     * @param urh yksitt‰inen urheilu
     */
    private void naytaUrheilu(Urheilu urh) {
        int kenttia = urh.getKenttia();
        String[] rivi = new String[kenttia-urh.ekaKentta()];
        for (int i = 0, k=urh.ekaKentta(); k<kenttia; i++, k++) {
            rivi[i] = urh.anna(k);
        }        
        urheiluTable.add(urh,rivi);
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
     * Asettaa nimen ikkunalle
     * @param title ikkunan palkissa n‰kyv‰ teksti
     */
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }
   
    
    /**Luetaan tallennettu tiedosto ensimm‰iseen ikkunaan annetun nimen perusteella
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
     * Tallentaa tiedoston 
     * @return vain jos tapahtuu jokin virhe
     */
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
     * Loudaan uusi P‰iv‰m‰‰r‰
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
            Dialogs.showMessageDialog("uuden luomnen ep‰onnistui" + e);
            return;
        }
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
     * Tekee uuden tyhj‰n urheilun editointia varten 
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
     * @param kayttaja jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        naytaTiedot();
    }

    /**
     * Avaa PvmDialogin ja sen sis‰ll‰ pystyt‰‰n muokkaamaan p‰iv‰m‰‰r‰‰
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
     *  Avaa UrheiluDialogin ja sen sis‰ll‰ pystyt‰‰n muokkaamaan urheilun tietoja 
     */
    public void muokkaaUrheilu(){       
        ModalController.showModal(UrheiluDialogController.class.getResource("UrheiluDialogView.fxml"),
                "Urheilun muokkaaminen", null, urhKohdalla);        
        hae(pvmKohdalla.getTunnusNro());
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
