package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;

import java.io.IOException;

public class GameController {

    Player player1 = new Player("Joueur 1", new XSymbol());

    Player player2 = new Player("Joueur 2", new OSymbol());

    Bot bot = new Bot("Bot", new OSymbol());

    Game game;

    Settings settings = new Settings(player1);

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
        if (settings.getIsOnePlayerMode() && settings.getFirstPlayerIsRandom()) {
            game = new Game(player1, bot);
        } else if (settings.getIsOnePlayerMode()) {
            game = new Game(player1, bot, settings.getFirstPlayer());
        } else if (settings.getFirstPlayerIsRandom()) {
            game = new Game(player1, player2);
        } else {
            game = new Game(player1, player2, settings.getFirstPlayer());
        }
        for (int i = 0; i < gameBoard.getChildren().size(); i++) {
            if (gameBoard.getChildren().get(i) instanceof Button) {
                ((Button) gameBoard.getChildren().get(i)).setText("");
                gameBoard.getChildren().get(i).getStyleClass().remove("gray");
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
            checkWin();
        }
        if (game.getCurrentPlayer() instanceof Bot && !game.isOver) {
            int[] botMove = bot.play(game.grid, player1.getSymbol());
            game.makeMove(botMove[0], botMove[1]);
            Button botButton = getNodeByCoordinate(botMove[0], botMove[1]);
            assert botButton != null;
            botButton.setText(game.getCurrentPlayer().getSymbol().toString());
            checkWin();
        }
    }

    private Button getNodeByCoordinate(Integer row, Integer column) {
        for (Node node : gameBoard.getChildren()) {
            if (node instanceof Button) {
                if(GridPane.getRowIndex(node) == (row * 2) && GridPane.getColumnIndex(node) == (column * 2)){
                    return (Button) node;
                }
            }
        }
        return null;
    }
    private void checkWin() {
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
            case "Joueur 1" -> {
                settings.setFirstPlayer(player1);
                settings.setFirstPlayerIsRandom(false);
            }
            case "Joueur 2" -> {
                settings.setFirstPlayer(player2);
                settings.setFirstPlayerIsRandom(false);
            }
            case "Aléatoire" -> settings.setFirstPlayerIsRandom(true);
        }
    }

    @FXML
    void replayGame(ActionEvent event) {
        createGame();
    }
}