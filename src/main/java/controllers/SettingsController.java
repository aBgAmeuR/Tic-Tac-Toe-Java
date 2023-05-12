package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsController {
    @FXML
    private ComboBox<String> firstPlayerComboBox;

    private GameController gameController;

    @FXML
    private Label labelDifficultyLevel;

    @FXML
    private Label labelPlayerCount;

    @FXML
    private ColorPicker player1Color;

    @FXML
    private ColorPicker player2Color;

    @FXML
    private VBox boxBotLevel;

    @FXML
    private Slider sliderDifficultyLevel;

    @FXML
    private Slider sliderPlayerCount;

    @FXML
    private Text player2Name;

    public void setGameController(GameController gameController) {
        // Récupérer le contrôleur de la vue principale
        this.gameController = gameController;
        if (gameController.settings.getFirstPlayerIsRandom()) {
            firstPlayerComboBox.setValue("Aléatoire");
        } else {
            firstPlayerComboBox.setValue(gameController.settings.getFirstPlayer().getName());
        }
         init();
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
        gameController.setFirstPlayer(firstPlayerComboBox.getValue());
        int sliderDifficultyLevelValue = (int) sliderDifficultyLevel.getValue() / 10;
        gameController.settings.setDifficultyLevel(sliderDifficultyLevelValue < 10 ? 1 : 2);

        int sliderPlayerCountValue = (int) sliderPlayerCount.getValue() / 10;
        gameController.settings.setIsOnePlayerMode(sliderPlayerCountValue < 10);
        // TODO: autre paramètres

        // Fermer la fenêtre modale après avoir appliqué les changements
        Stage stage = (Stage) firstPlayerComboBox.getScene().getWindow();
        stage.close();
    }

    private void init() {
        if (gameController.settings.getIsOnePlayerMode()) {
            sliderPlayerCount.setValue(sliderDifficultyLevel.getMin() + 10);
            boxBotLevel.setDisable(false);
        } else {
            sliderPlayerCount.setValue(sliderPlayerCount.getMax() - 10);
            boxBotLevel.setDisable(true);
        }
        if ((int) sliderPlayerCount.getValue() / 10 > 10) {
            player2Name.setText("Joueur 2");
        } else {
            player2Name.setText("Bot");
        }

        sliderDifficultyLevel.setValue(gameController.settings.getDifficultyLevel() * 10);

        int sliderValue1 = (int) sliderDifficultyLevel.getValue() / 10;
        labelDifficultyLevel.setText(String.valueOf(sliderValue1 < 10 ? 1 : 2));
        int sliderValue2 = (int) sliderPlayerCount.getValue() / 10;
        labelPlayerCount.setText(String.valueOf(sliderValue2 < 10 ? 1 : 2));

       sliderDifficultyLevel.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
           int sliderValue = (int) sliderDifficultyLevel.getValue() / 10;
           labelDifficultyLevel.setText(String.valueOf(sliderValue < 10 ? 1 : 2));
       });

       sliderPlayerCount.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
           int sliderValue = (int) sliderPlayerCount.getValue() / 10;
           labelPlayerCount.setText(String.valueOf(sliderValue < 10 ? 1 : 2));
           boxBotLevel.setDisable(sliderValue > 10);
           if (sliderValue > 10) {
               player2Name.setText("Joueur 2");
              } else {
                player2Name.setText("Bot");
           }
       });

       player1Color.setValue(gameController.player1.symbol.getColor());
       player2Color.setValue(gameController.player2.symbol.getColor());

       player1Color.setOnAction((e) -> {
           gameController.player1.symbol.setColor(player1Color.getValue());
       });

       player2Color.setOnAction((e) -> {
           gameController.player2.symbol.setColor(player2Color.getValue());
       });

    }

}
