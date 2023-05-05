package models;

public class Settings {

    private Player firstPlayer;
    private Boolean firstPlayerIsRandom;

    public Settings(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
        this.firstPlayerIsRandom = false;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Boolean getFirstPlayerIsRandom() {
        return firstPlayerIsRandom;
    }


    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setFirstPlayerIsRandom(Boolean firstPlayerIsRandom) {
        this.firstPlayerIsRandom = firstPlayerIsRandom;
    }
}
