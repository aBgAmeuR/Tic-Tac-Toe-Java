package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Game;
import models.OSymbol;
import models.Player;
import models.XSymbol;

import java.io.IOException;

public class GameController {

    Player player1 = new Player("Joueur 1", new XSymbol());
    Player player2 = new Player("Joueur 2", new OSymbol());
    Game game;
    Boolean firstPlayerIsRandom = true;
    Player firstPlayer = player1;

    public int equalScore = 0;

    @FXML
    private GridPane gameBoard;

    @FXML
    private Text player1Score;

    @FXML
    private Text player2Score;

    @FXML
    private Text equalityScore;

    @FXML
    private VBox msgEndGame;

    @FXML
    private Button replayButton;

    @FXML
    private Text winnerText;

    @FXML
    public void initialize() {
        createGame();
    }

    private void createGame() {
        if (firstPlayerIsRandom) {
            game = new Game(player1, player2);
        } else {
            game = new Game(player1, player2, firstPlayer);
        }
        for (int i = 0; i < gameBoard.getChildren().size(); i++) {
            if (gameBoard.getChildren().get(i) instanceof Button) {
                ((Button) gameBoard.getChildren().get(i)).setText("");
                ((Button) gameBoard.getChildren().get(i)).getStyleClass().remove("gray");
            }
        }
        msgEndGame.setVisible(false);
        msgEndGame.setDisable(true);
        msgEndGame.setManaged(false);
        player1Score.getStyleClass().remove("gray");
        player2Score.getStyleClass().remove("gray");
        equalityScore.getStyleClass().remove("gray");
    }
    @FXML
    void handleButtonClick(ActionEvent event) {
        // Quand un joueur clique sur un bouton de la grille
        Button button = (Button) event.getSource();
        int row = gameBoard.getRowIndex(button) / 2;
        int col = gameBoard.getColumnIndex(button) / 2;
        if (game.makeMove(row, col)) {
            button.setText(game.getCurrentPlayer().getSymbol().toString());
            if (game.grid.checkWin()) {
                game.getCurrentPlayer().incrementScore();
                game.isOver = true;
                updateScore();
                showLine();
            } else if (game.grid.isFull()) {
                equalScore++;
                game.isOver = true;
                updateScore();
            } else {
                game.switchPlayer();
            }
        }
    }

    void updateScore() {
        player1Score.setText(String.valueOf(player1.getScore()));
        player2Score.setText(String.valueOf(player2.getScore()));
        equalityScore.setText(String.valueOf(equalScore));
        if (game.isOver) {
            msgEndGame.setVisible(true);
            msgEndGame.setDisable(false);
            msgEndGame.setManaged(true);
            if (game.grid.checkWin()) {
                winnerText.setText(game.getCurrentPlayer().getName() + " a gagné !");
            } else {
                winnerText.setText("Egalité !");
            }
        }
    }

    void showLine() {
        int[] res = game.grid.getWinningCases();
        for (int i = 0; i < res.length; i++) {
            res[i] *= 2;
        }
        for (int i = 0; i < gameBoard.getChildren().size(); i++) {
            if (gameBoard.getChildren().get(i) instanceof Button) {
                int row = gameBoard.getRowIndex(gameBoard.getChildren().get(i));
                int col = gameBoard.getColumnIndex(gameBoard.getChildren().get(i));
                if (row == res[0] && col == res[1] || row == res[2] && col == res[3] || row == res[4] && col == res[5]) {
                } else {
                    gameBoard.getChildren().get(i).getStyleClass().add("gray");
                }
            }
        }
    }

    @FXML
    void handleQuitterClick(ActionEvent event) {
        // Fermer l'application
        System.exit(0);
    }

    @FXML
    void ResetClick(ActionEvent event) {
        // Réinitialiser le jeu
        createGame();
    }

    @FXML
    public void openRules() throws IOException {
        // Charger la vue des règles du jeu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Rules.fxml"));
        Parent root = loader.load();

        Stage rulesStage = new Stage();
        rulesStage.initModality(Modality.APPLICATION_MODAL);
        rulesStage.setTitle("Règles du jeu");
        rulesStage.setScene(new Scene(root));
        rulesStage.setResizable(false);
        rulesStage.showAndWait();
    }

    @FXML
    void openSettings() throws IOException {
        // Charger la vue des paramètres du jeu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Settings.fxml"));
        Parent root = loader.load();
        SettingsController settingsController = loader.getController();

        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.APPLICATION_MODAL);
        settingsStage.setTitle("Paramètres");
        settingsStage.setScene(new Scene(root));
        settingsStage.setResizable(false);
        settingsController.setGameController(this);
        settingsStage.showAndWait();
    }

    public void setFirstPlayer(String selectedFirstPlayer) {
        switch (selectedFirstPlayer) {
            case "Joueur 1":
                firstPlayer = player1;
                break;
            case "Joueur 2":
                firstPlayer = player2;
                break;
            case "Aléatoire":
                firstPlayerIsRandom = true;
                break;
        }
    }

    public boolean getFirstPlayerIsRandom() {
        return firstPlayerIsRandom;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayerIsRandom(boolean b) {
        firstPlayerIsRandom = b;
    }

    @FXML
    void replayGame(ActionEvent event) {
        createGame();
    }
}