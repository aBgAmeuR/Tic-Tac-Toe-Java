package models;

public class Player {

    private String name;
    public Symbol symbol;
    private int score;

    public Player(String name, Symbol symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

}
