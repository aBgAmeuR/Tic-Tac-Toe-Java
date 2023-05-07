package models;

public class Bot extends Player {

    private Grid grid;

    public Bot(String name, Symbol symbol) {
        super(name, symbol);
    }

    public void play(Grid grid) {
        this.grid = grid;
    }

    public void play() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        for (int i = 0; i < this.grid.getSize(); i++) {
            for (int j = 0; j < this.grid.getSize(); j++) {
                if (this.grid.isEmptyCell(i, j)) {
                    this.grid.setSymbol(i, j, this.getSymbol());
                    int score = minimax(this.grid, 0, false);
                    this.grid.setSymbol(i, j, null);
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        this.grid.setSymbol(bestMove[0], bestMove[1], this.getSymbol());

    }

    private int minimax(Grid grid, int depth, boolean isMaximizing) {
        if (grid.checkWin() || depth == 0) {
            return evaluate(grid);
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < grid.getSize(); i++) {
                for (int j = 0; j < grid.getSize(); j++) {
                    if (grid.isEmptyCell(i, j)) {
                        grid.setSymbol(i, j, this.getSymbol());
                        int score = minimax(grid, depth - 1, false);
                        grid.setSymbol(i, j, null);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < grid.getSize(); i++) {
                for (int j = 0; j < grid.getSize(); j++) {
                    if (grid.isEmptyCell(i, j)) {
                        grid.setSymbol(i, j, this.getSymbol());
                        int score = minimax(grid, depth - 1, true);
                        grid.setSymbol(i, j, null);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private int evaluate(Grid grid) {
        if (grid.checkWin()) {
            if (grid.getWinner() == this.getSymbol()) {
                return 1;
            } else if (grid.getWinner() == null) {
                return 0;
            } else {
                return -1;
            }
        }
        return 0;
    }
}
