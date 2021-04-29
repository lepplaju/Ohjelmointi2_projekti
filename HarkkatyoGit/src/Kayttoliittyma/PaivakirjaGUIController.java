package Kayttoliittyma;

import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import luokat.Kayttaja;
import luokat.Pvm;
import luokat.SailoException;
import luokat.Urheilu;

import java.net.URL;
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
    @FXML private ListChooser<Pvm> chooserPvm;
    @FXML private StringGrid<Urheilu>urheiluTable;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }

    @FXML private void handleHakuehto() {
        hae(0);       
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
    
    @FXML private void handlePoistaPvm() {
        poistaPvm();
    }
    
    @FXML private void handlePoistaUrheilu() {
        poistaUrheilu();
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
    
    
//===========================================================================================
// T‰st‰ eteenp‰in ei k‰yttˆliittym‰‰n suoraan liittyv‰‰ koodia

    private Kayttaja kayttaja = new Kayttaja();
    private Pvm pvmKohdalla;
    private String kayttajannimi="Late";
    private static Urheilu apuUrheilu = new Urheilu();
    private Urheilu urhKohdalla;

    /**
     * Alustaa  p‰iv‰m‰‰rien tiedot omaan listaan ja 
     * urheilujen tiedot tiedot StringGridiin
     */
    protected void alusta() {
        
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
        
        chooserPvm.setOnMouseClicked(e->{
        if (  e.getClickCount() == 2) muokkaaPvm();
        });
        
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
            if(rivi[i].equals("null")) rivi[i]="-";
            if(rivi[i].equals("0.0")) rivi[i]="-";            
        }        
        urheiluTable.add(urh,rivi);
    }    
    
    /**
     * N‰ytt‰‰ poikkeavan syntaksin
     * @param virhe aputeksti, joka halutaan n‰ytt‰‰ ruudulla
     */
    public void naytaVirhe(String virhe) {
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
        boolean vastaus = Dialogs.showQuestionDialog("Sulkeminen", "Sulje ilman tallentamista?", "Sulje", "Peruuta");
        return vastaus;
    }
    
    /**Hakee t‰m‰n p‰iv‰m‰‰r‰n tiedot listaan
     * @param pvmId mik‰ on p‰iv‰n id
     */
    protected void hae(int pvmId) {
        int pvmNro = pvmId;
        if (pvmNro<=0) {
            Pvm kohdalla = pvmKohdalla;
            if (kohdalla !=null) pvmNro=kohdalla.getTunnusNro();
        }
        String ehto = hakuehto.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
        chooserPvm.clear();
        int index=0;    
        Collection<Pvm> pvmt;
        try {
        pvmt = kayttaja.annaPvmt(ehto);
        int i=0;       
        for (Pvm pvm: pvmt) {
            if (pvm.getTunnusNro() == pvmId) index = i;
            chooserPvm.add(pvm.getPaivays(), pvm);
            i++;
        }
    }catch (Exception ex) {
        Dialogs.showMessageDialog("J‰senen hakemisessa ongelmia! " + ex.getMessage());
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
     * Tekee uuden tyhj‰n urheilun editointia varten 
     */ 
    public void uusiUrheilu() { 
        if ( pvmKohdalla == null ) return;  
        try {
            Urheilu uusi = new Urheilu(pvmKohdalla.getTunnusNro());
            uusi = UrheiluDialogController.kysyUrheilu(null,uusi); 
            if (uusi ==null)return;
            uusi.rekisteroi();
            kayttaja.lisaa(uusi);
            hae(0);
        } catch (Exception e) {
            Dialogs.showMessageDialog("Lis‰‰minen ep‰onnistui: " + e.getMessage());
        }
    }
       
    
    /**
     *  Avaa UrheiluDialogin ja sen sis‰ll‰ pystyt‰‰n muokkaamaan urheilun tietoja 
     */
    public void muokkaaUrheilu() {
        try {
        Urheilu urh = urhKohdalla.clone();
        Urheilu muokattu = ModalController.showModal(UrheiluDialogController.class.getResource("UrheiluDialogView.fxml"),
                "Urheilun muokkaaminen", null, urh);        
        if (muokattu == null)return;
        kayttaja.korvaaTaiLisaa(muokattu);
        hae(pvmKohdalla.getTunnusNro());
        } catch(CloneNotSupportedException e) {
            //
        }
    }

    /**
     * @param kayttaja jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
     */
    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        naytaTiedot();
    }
    
     /**
      * Poistetaan p‰iv‰m‰‰r‰ ja sen sis‰ll‰ olevat urheilut
      */
    private void poistaPvm() {
       Pvm pvm = pvmKohdalla;
        if(pvm==null)return;
        if(!Dialogs.showQuestionDialog("Poisto", "Poistetaanko Pvm ja sen sis‰ll‰ olevat tiedot: "
                + pvm.getPaivays(),"kyll‰", "ei")) return;
        kayttaja.poistaPvm(pvm);
        int index = chooserPvm.getSelectedIndex();
        hae(0);
        chooserPvm.setSelectedIndex(index);    
    }
    
    /**
     * Poistetaan urheilu, joka on valittuna
     */
   private void poistaUrheilu() {
       int rivi = urheiluTable.getRowNr();
       if(rivi<0) return;
       Urheilu urh = urheiluTable.getObject();
       if( urh ==null) return;
       if(!Dialogs.showQuestionDialog("Poisto", "Poistetaanko valittu urheilu p‰iv‰m‰‰r‰lt‰: "
               + pvmKohdalla.getPaivays(),"kyll‰", "ei")) return;
       kayttaja.poistaUrheilu(urh);
       naytaUrheilut(pvmKohdalla);
       int urheiluja = urheiluTable.getItems().size();
       if (rivi>=urheiluja) rivi = urheiluja -1;
       urheiluTable.getFocusModel().focus(rivi);
   }
   


}
