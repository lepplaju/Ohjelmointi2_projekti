package Kayttoliittyma;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import luokat.Kayttaja;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Lepplaju
 * @version 4.4.2021
 *
 */
public class PaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaivakirjaGUIView.fxml"));
            final Pane root = (Pane)ldr.load();
            final PaivakirjaGUIController paivakirjaCtrl = (PaivakirjaGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paivakirja");
            
            
            primaryStage.setOnCloseRequest((event) -> {
                if ( !paivakirjaCtrl.voikoSulkea() ) event.consume();
            });
        
        Kayttaja kayttaja = new Kayttaja();  
        paivakirjaCtrl.setKayttaja(kayttaja); 

            
            primaryStage.show();
            
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                paivakirjaCtrl.lueTiedosto(params.getRaw().get(0));  
            else
                if ( !paivakirjaCtrl.avaa() ) Platform.exit();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**Käynnistetään käyttöliittymä
     * @param args komentorivin parametrit
     */
    public static void main(String[] args) {
        launch(args);
    }
}