package models;

public class Settings {

    private Player firstPlayer;
    private Boolean firstPlayerIsRandom;
    private Boolean isOnePlayerMode;
    private int difficultyLevel;

    public Settings(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
        this.firstPlayerIsRandom = false;
        this.isOnePlayerMode = true;
        this.difficultyLevel = 2;
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

    public Boolean getIsOnePlayerMode() {
        return isOnePlayerMode;
    }

    public void setIsOnePlayerMode(Boolean isOnePlayerMode) {
        this.isOnePlayerMode = isOnePlayerMode;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
