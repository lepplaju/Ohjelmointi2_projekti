package Kayttoliittyma;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import java.net.URL;

/**
 * @author Pekka
 * @version 30.1.2021
 *
 */
public class KayttoliittymaHTGUIController implements ModalControllerInterface<String> {    

    @FXML
    private Button Button_Vertailu;

    @FXML
    private Button Button_Ravinto;

    @FXML
    private Button Button_Liikunta;
    
    @FXML private void Eitoimi() {
    Dialogs.showMessageDialog("ei toimi");
    }
    
    @FXML private void AvaaLiikunta(){
        
        ModalController.showModal(KayttoliittymaHTGUIController.class.getResource("LiikuntaGUIView.fxml"), "",null, "");
    }    
    
    @FXML private void AvaaRuokailu(){ 

        ModalController.showModal(KayttoliittymaHTGUIController.class.getResource("RavintoGUIView.fxml"), "",null, "");
    }
    
    @FXML private void AvaaVertailu(){
        
        ModalController.showModal(KayttoliittymaHTGUIController.class.getResource("VertailuikkunaGUIView.fxml"), "",null, "");
    }
    

    
    @FXML private void UusiLiikunta(){
        
        ModalController.showModal(KayttoliittymaHTGUIController.class.getResource("LisaauusiLiikuntaGUIView.fxml"), "",null, "");
    }
    
    @FXML private void UusiRavinto(){
        
        ModalController.showModal(KayttoliittymaHTGUIController.class.getResource("LisaauusiravintoGUIView.fxml"), "",null, "");
    }

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void setDefault(String arg0) {
        // TODO Auto-generated method stub
        
    }
    
}