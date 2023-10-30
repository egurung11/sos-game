package main;

public class GeneralGame extends Board {
    public GeneralGame(int boardSize) {
        super(boardSize);
    }

    void updateGameState() {
        if (!boardFull()) return;

        if (getPlayerAScore() > getPlayerBScore()) setGameState(GameState.A_WON);
        else if (getPlayerBScore() > getPlayerAScore()) setGameState(GameState.B_WON);
        else setGameState(GameState.DRAW); // score equal
    }
}
