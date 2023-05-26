package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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

    @FXML
    private TextField player1Pseudo;

    @FXML
    private TextField player2Pseudo;

    @FXML
    private HBox player2PseudoHBox;

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
        boolean checkIfRestScore = false;

        gameController.setFirstPlayer(firstPlayerComboBox.getValue());
        int sliderDifficultyLevelValue = (int) sliderDifficultyLevel.getValue() / 10;
        if (sliderDifficultyLevelValue < 10 != (gameController.settings.getDifficultyLevel() == 1)) checkIfRestScore = true;
        gameController.settings.setDifficultyLevel(sliderDifficultyLevelValue < 10 ? 1 : 2);

        int sliderPlayerCountValue = (int) sliderPlayerCount.getValue() / 10;
        if (sliderPlayerCountValue < 10 != gameController.settings.getIsOnePlayerMode()) checkIfRestScore = true;
        gameController.settings.setIsOnePlayerMode(sliderPlayerCountValue < 10);

        if (checkIfRestScore) {
            gameController.resetScore();
        }

        gameController.player1.symbol.setColor(player1Color.getValue());
        gameController.player2.symbol.setColor(player2Color.getValue());
        gameController.bot.symbol.setColor(player2Color.getValue());

        gameController.player1.setName(player1Pseudo.getText());
        if (gameController.settings.getIsOnePlayerMode()) {
            if (gameController.settings.getDifficultyLevel() == 1) {
                gameController.bot.setName("Mattéo");
            } else {
                gameController.bot.setName("Pascal");
            }
            gameController.bot.setName(player2Pseudo.getText());
        } else {
            gameController.player2.setName(player2Pseudo.getText());
        }

        // Fermer la fenêtre modale après avoir appliqué les changements
        Stage stage = (Stage) firstPlayerComboBox.getScene().getWindow();
        stage.close();
    }

    private void init() {
        if (gameController.settings.getIsOnePlayerMode()) {
            sliderPlayerCount.setValue(sliderPlayerCount.getMin() + 10);
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
        if (gameController.settings.getDifficultyLevel() == 1) {
            sliderDifficultyLevel.setValue(sliderDifficultyLevel.getMin() + 10);
        } else {
            sliderDifficultyLevel.setValue(sliderDifficultyLevel.getMax() - 10);
        };

        int sliderValue1 = (int) sliderDifficultyLevel.getValue() / 10;
        labelDifficultyLevel.setText(String.valueOf(sliderValue1 < 10 ? 1 : 2));
        int sliderValue2 = (int) sliderPlayerCount.getValue() / 10;
        labelPlayerCount.setText(String.valueOf(sliderValue2 < 10 ? 1 : 2));

        sliderDifficultyLevel.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            int sliderValue = (int) sliderDifficultyLevel.getValue() / 10;
            labelDifficultyLevel.setText(String.valueOf(sliderValue < 10 ? 1 : 2));
            if (sliderValue < 10) {
                player2Pseudo.setText("Mattéo");
            } else {
                player2Pseudo.setText("Pascal");
            }
        });

        sliderPlayerCount.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            int sliderValue = (int) sliderPlayerCount.getValue() / 10;
            labelPlayerCount.setText(String.valueOf(sliderValue < 10 ? 1 : 2));
            boxBotLevel.setDisable(sliderValue > 10);
            if (sliderValue > 10) {
                player2Name.setText("Joueur 2");
                player2Pseudo.setText(gameController.player2.getName());
                player2PseudoHBox.setDisable(false);
            } else {
                player2Name.setText("Bot");
                if (gameController.settings.getDifficultyLevel() == 1) {
                    player2Pseudo.setText("Mattéo");
                } else {
                    player2Pseudo.setText("Pascal");
                }
                player2PseudoHBox.setDisable(true);
            }
        });

        player1Color.setValue(gameController.player1.symbol.getColor());
        player2Color.setValue(gameController.player2.symbol.getColor());

        player1Pseudo.setText(gameController.player1.getName());
        if (gameController.settings.getIsOnePlayerMode()) {
            if (gameController.settings.getDifficultyLevel() == 1) {
                player2Pseudo.setText("Mattéo");
            } else {
                player2Pseudo.setText("Pascal");
            }
            player2PseudoHBox.setDisable(true);
        } else {
            player2Pseudo.setText(gameController.player2.getName());
            player2PseudoHBox.setDisable(false);
        }
    }

}
