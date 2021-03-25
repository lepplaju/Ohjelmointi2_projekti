package Kayttoliittyma;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Pekka
 * @version 9.3.2021
 *
 */
public class PaivakirjaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaivakirjaGUIView.fxml"));
            final Pane root = ldr.load();
            //final PaivakirjaGUIController paivakirjaCtrl = (PaivakirjaGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("paivakirja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Paivakirja");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}