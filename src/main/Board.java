package main;

import java.util.Arrays;

public abstract class Board {
    public enum Cell {EMPTY, S, O}
    public enum GameState {
        PLAYING, DRAW, A_WON, B_WON, NOT_STARTED
    }

    private final Cell[][] grid;
    private char turn;
    private GameState currentGameState;

    private Player pA, pB;

    public Board(int boardSize) {
        grid = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++)
            Arrays.fill(grid[i], Cell.EMPTY);
        turn = 'A';
        currentGameState = GameState.NOT_STARTED;
        pA = new Player();
        pB = new Player();
    }

    public int getBoardSize() {
        return grid.length;
    }

    public int getPlayerAScore() {
        return pA.getScore();
    }

    public int getPlayerBScore() {
        return pB.getScore();
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < grid.length && column >= 0 && column < grid.length)
            return grid[row][column];

        return null;
    }

    public char getTurn() {
        return turn;
    }

    public void makeMove(int row, int column, Cell move) {
        if (row >= 0 && row < grid.length && column >= 0 && column < grid.length && grid[row][column] == Cell.EMPTY) {
            grid[row][column] = move;

            if (turn == 'A') {
                pA.incScore(countSOS(row, column));
                updateGameState();
                turn = 'B';
            } else {
                pB.incScore(countSOS(row, column));
                updateGameState();
                turn = 'A';
            }
        }
    }

    public void resetGame() {
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid.length; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        currentGameState = GameState.NOT_STARTED;
        turn = 'A';
    }

    public boolean boardFull() {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell == Cell.EMPTY) {
                    return false; // an empty cell found, not full
                }
            }
        }
        return true;
    }

    abstract void updateGameState();

    private int countSOS(int row, int col) {
        Cell move = grid[row][col];

        int count = 0;

        if (move == Cell.O) {
            if (row > 0 && row < grid.length-1) {
                if (grid[row-1][col] == Cell.S && grid[row+1][col] == Cell.S) count++;
                if (col > 0 && col < grid.length-1) {
                    if (grid[row-1][col+1] == Cell.S && grid[row+1][col-1] == Cell.S) count++;
                    if (grid[row+1][col+1] == Cell.S && grid[row-1][col-1] == Cell.S) count++;
                }
            }
            if (col > 0 && col < grid.length-1)
                if (grid[row][col-1] == Cell.S && grid[row][col+1] == Cell.S) count++;
        } else if (move == Cell.S) {
            if (row > 1) {
                if (grid[row-1][col] == Cell.O && grid[row-2][col] == Cell.S) count++;
                if (col > 1)
                    if (grid[row-1][col-1] == Cell.O && grid[row-2][col-2] == Cell.S) count++;
                if (col < grid.length-2)
                    if (grid[row-1][col+1] == Cell.O && grid[row-2][col+2] == Cell.S) count++;
            }
            if (row < grid.length-2) {
                if (grid[row+1][col] == Cell.O && grid[row+2][col] == Cell.S) count++;
                if (col > 1)
                    if (grid[row+1][col-1] == Cell.O && grid[row+2][col-2] == Cell.S) count++;
                if (col < grid.length-2)
                    if (grid[row+1][col+1] == Cell.O && grid[row+2][col+2] == Cell.S) count++;
            }
            if (col > 1)
                if (grid[row][col-1] == Cell.O && grid[row][col-2] == Cell.S) count++;
            if (col < grid.length-2)
                if (grid[row][col+1] == Cell.O && grid[row][col+2] == Cell.S) count++;
        }

        return count;
    }

    public void setGameState(GameState gameState) {
        currentGameState = gameState;
    }
    public GameState getGameState() {
        return currentGameState;
    }

}