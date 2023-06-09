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

    private GameController gameController;

    @FXML
    private ComboBox<String> firstPlayerComboBox;
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

    // Pour linker le contrôleur de la vue principale
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

    // Pour pouvoir fermer la fenêtre modale sans appliquer les changements
    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        // Fermer la fenêtre modale sans appliquer les changements
        Stage stage = (Stage) firstPlayerComboBox.getScene().getWindow();
        stage.close();
    }

    // Pour pouvoir fermer la fenêtre modale en appliquant les changements
    @FXML
    public void handleApplyButton(ActionEvent actionEvent) {
        // Appliquer les changements aux paramètres du jeu
        boolean checkIfRestScore = false;

        // Appliquer les changements pour le premier joueur
        gameController.setFirstPlayer(firstPlayerComboBox.getValue());
        // Appliquer les changements pour le niveau de difficulté
        int sliderDifficultyLevelValue = (int) sliderDifficultyLevel.getValue() / 10;
        if (sliderDifficultyLevelValue < 10 != (gameController.settings.getDifficultyLevel() == 1))
            checkIfRestScore = true;
        gameController.settings.setDifficultyLevel(sliderDifficultyLevelValue < 10 ? 1 : 2);

        // Appliquer les changements pour le nombre de joueurs
        int sliderPlayerCountValue = (int) sliderPlayerCount.getValue() / 10;
        if (sliderPlayerCountValue < 10 != gameController.settings.getIsOnePlayerMode())
            checkIfRestScore = true;
        gameController.settings.setIsOnePlayerMode(sliderPlayerCountValue < 10);

        // Si le nombre de joueurs a changé ou le niveau de difficulté a changé, on réinitialise le score
        if (checkIfRestScore) {
            gameController.resetScore();
        }

        // Appliquer les changements pour les couleurs des joueurs
        gameController.player1.symbol.setColor(player1Color.getValue());
        gameController.player2.symbol.setColor(player2Color.getValue());
        gameController.bot.symbol.setColor(player2Color.getValue());

        // Appliquer les changements pour les pseudos des joueurs
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

    // Dès que la fenêtre modale est chargée on initialise les paramètres
    private void init() {
        // Initialiser les paramètres du jeu
        // Initialiser le nombres de joueurs
        if (gameController.settings.getIsOnePlayerMode()) {
            sliderPlayerCount.setValue(sliderPlayerCount.getMin() + 10);
            boxBotLevel.setDisable(false);
        } else {
            sliderPlayerCount.setValue(sliderPlayerCount.getMax() - 10);
            boxBotLevel.setDisable(true);
        }
        // Le nom du joueur 2 est "Bot" si le nombre de joueurs est 1
        // Sinon c'est "Joueur 2"
        if ((int) sliderPlayerCount.getValue() / 10 > 10) {
            player2Name.setText("Joueur 2");
        } else {
            player2Name.setText("Bot");
        }
        // Initialiser le niveau de difficulté
        if (gameController.settings.getDifficultyLevel() == 1) {
            sliderDifficultyLevel.setValue(sliderDifficultyLevel.getMin() + 10);
        } else {
            sliderDifficultyLevel.setValue(sliderDifficultyLevel.getMax() - 10);
        }
        // Initialiser le nom des joueurs
        int sliderValue1 = (int) sliderDifficultyLevel.getValue() / 10;
        labelDifficultyLevel.setText(String.valueOf(sliderValue1 < 10 ? 1 : 2));
        int sliderValue2 = (int) sliderPlayerCount.getValue() / 10;
        labelPlayerCount.setText(String.valueOf(sliderValue2 < 10 ? 1 : 2));

        // Fonction pour changer le nom du joueur 2 en fonction du niveau de difficulté
        sliderDifficultyLevel.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            int sliderValue = (int) sliderDifficultyLevel.getValue() / 10;
            labelDifficultyLevel.setText(String.valueOf(sliderValue < 10 ? 1 : 2));
            if (sliderValue < 10) {
                player2Pseudo.setText("Mattéo");
            } else {
                player2Pseudo.setText("Pascal");
            }
        });

        // Fonction pour changer le nom du joueur 2 en fonction du nombre de joueurs
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

        // Initialiser la couleur des joueurs
        player1Color.setValue(gameController.player1.symbol.getColor());
        player2Color.setValue(gameController.player2.symbol.getColor());

        // Initialiser les pseudos des joueurs
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
