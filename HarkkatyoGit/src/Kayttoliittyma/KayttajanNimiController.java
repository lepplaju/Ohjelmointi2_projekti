package Kayttoliittyma;


    import fi.jyu.mit.fxgui.ModalController;
    import fi.jyu.mit.fxgui.ModalControllerInterface;
    import javafx.fxml.FXML;
    import javafx.scene.control.TextField;
    import javafx.stage.Stage;

    /**
     * Kyst��n k�ytt�j�n nimi ja luodaan t�t� varten dialogi.
     * 
     * @author Lepplaju
     * @version 4.4.2021
     */
    public class KayttajanNimiController implements ModalControllerInterface<String> {
        
        @FXML private TextField textVastaus;
        private String vastaus = null;

        
        @FXML private void handleOK() {
            vastaus = textVastaus.getText();
            ModalController.closeStage(textVastaus);
        }

        
        @FXML private void handleCancel() {
            ModalController.closeStage(textVastaus);
        }


        @Override
        public String getResult() {
            return vastaus;
        }

        
        @Override
        public void setDefault(String oletus) {
            textVastaus.setText(oletus);
        }

        
        /**
         * Mit� tehd��n kun dialogi on n�ytetty
         */
        @Override
        public void handleShown() {
            textVastaus.requestFocus();
        }
        
        
        /**
         * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mit� nime� n�ytet��n oletuksena
         * @return null jos painetaan Cancel, muuten kirjoitettu nimi
         */
        public static String kysyNimi(Stage modalityStage, String oletus) {
            return ModalController.showModal(
                    KayttajanNimiController.class.getResource("KayttajanNimiView.fxml"),
                    "Late",
                    modalityStage, oletus);
        }
    }

