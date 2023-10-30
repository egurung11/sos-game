package main;

public class SimpleGame extends Board {
    public SimpleGame(int boardSize) {
        super(boardSize);
    }

    void updateGameState() {
        if (getPlayerAScore() > getPlayerBScore()) setGameState(GameState.A_WON);
        else if (getPlayerBScore() > getPlayerAScore()) setGameState(GameState.B_WON);
        else if (boardFull()) setGameState(GameState.DRAW);
    }
}
