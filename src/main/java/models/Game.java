package models;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Game {

    private Player[] players;
    private Player currentPlayer;
    public Grid grid;
    public boolean isOver = false;

    public Game(Player player1, Player player2, Player firstPlayer) {
        this.players = new Player[]{player1, player2};
        this.currentPlayer = firstPlayer;
        this.grid = new Grid(3);
    }

    public Game(Player player1, Player player2) {
        this.players = new Player[]{player1, player2};
        this.currentPlayer = (Math.random() < 0.5) ? player1 : player2;
        this.grid = new Grid(3);
    }


    public boolean makeMove(int row, int col) {
        if (grid.isEmptyCell(row, col) && !isOver) {
            grid.setSymbol(row, col, currentPlayer.getSymbol());
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


}
