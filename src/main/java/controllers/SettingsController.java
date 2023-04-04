package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private ComboBox<String> firstPlayerComboBox;

    private GameController gameController;

    public void setGameController(GameController gameController) {
        // Récupérer le contrôleur de la vue principale
        this.gameController = gameController;
        if (gameController.getFirstPlayerIsRandom()) {
            firstPlayerComboBox.setValue("Aléatoire");
        } else {
            firstPlayerComboBox.setValue(gameController.getFirstPlayer().getName());
        }
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        // Fermer la fenêtre modale sans appliquer les changements
        Stage stage = (Stage) firstPlayerComboBox.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleApplyButton(ActionEvent actionEvent) {
        // Appliquer les changements aux paramètres du jeu
        String selectedFirstPlayer = firstPlayerComboBox.getValue();
        if (selectedFirstPlayer == "Aléatoire") {
            gameController.setFirstPlayerIsRandom(true);
        } else {
            gameController.setFirstPlayerIsRandom(false);
            gameController.setFirstPlayer(selectedFirstPlayer);
        }
        System.out.println("First player: " + gameController.getFirstPlayer().getName());
        System.out.println("First player is random: " + gameController.getFirstPlayerIsRandom());

        // TODO: autre paramètres

        // Fermer la fenêtre modale après avoir appliqué les changements
        Stage stage = (Stage) firstPlayerComboBox.getScene().getWindow();
        stage.close();
    }
}
