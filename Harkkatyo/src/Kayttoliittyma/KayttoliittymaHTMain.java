package Kayttoliittyma;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Pekka
 * @version 30.1.2021
 *
 */
public class KayttoliittymaHTMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KayttoliittymaHTGUIView.fxml"));
            final Pane root = ldr.load();
            //final KayttoliittymaHTGUIController kayttoliittymahtCtrl = (KayttoliittymaHTGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kayttoliittymaht.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("KayttoliittymaHT");
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