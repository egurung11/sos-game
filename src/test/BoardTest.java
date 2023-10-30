package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.GeneralGame;
import main.SimpleGame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Board;
import main.Board.Cell;
import main.GameGraphics;

import java.awt.*;
import java.util.Arrays;

public class BoardTest {

    //private Board board = new Board(board.boardSize);
    int boardSize;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testChoseBoardSize() {
        Board board = new GeneralGame(8);
        assertNotEquals(boardSize, board.getBoardSize());
    }

    @Test
    public void testCorrectGameState() {
        Board board = new GeneralGame(8);
        assertEquals(Board.GameState.NOT_STARTED, board.getGameState());
    }

    @Test
    public void testGamePlaying() {
        Board board = new GeneralGame(8);
        char turn = board.getTurn();
        board.makeMove(2, 2, Cell.S);
        assertNotEquals(Board.GameState.PLAYING, board.getGameState());
    }

    @Test
    public void testMakeMoveSimpleGame() {
        Board board = new SimpleGame(8);
        char turn = board.getTurn();
        board.makeMove(2, 2, Cell.S);
        assertNotEquals(turn, board.getTurn());
    }

    @Test
    public void testSimpleGameOverA() {
        Board board = new SimpleGame(8);
        board.makeMove(1, 1, Cell.S);
        board.makeMove(2, 2, Cell.O);
        board.makeMove(3, 3, Cell.S);
        assertEquals(Board.GameState.A_WON, board.getGameState());
    }

    @Test
    public void testSimpleGameOverB() {
        Board board = new SimpleGame(8);
        board.makeMove(1, 1, Cell.S);
        board.makeMove(2, 2, Cell.O);
        board.makeMove(2, 1, Cell.O);
        board.makeMove(3, 3, Cell.S);
        assertEquals(Board.GameState.B_WON, board.getGameState());
    }

    @Test
    public void testSimpleGameTie() {
        Board board = new SimpleGame(3);
        board.makeMove(0, 0, Cell.O);
        board.makeMove(0, 1, Cell.O);

        board.makeMove(0, 2, Cell.O);
        board.makeMove(1, 1, Cell.O);

        board.makeMove(1, 2, Cell.O);
        board.makeMove(1, 0, Cell.O);

        board.makeMove(2, 1, Cell.O);
        board.makeMove(2, 2, Cell.O);
        board.makeMove(2, 0, Cell.O);
        assertEquals(Board.GameState.DRAW, board.getGameState());
    }

    @Test
    public void testMakeMoveGeneralGame() {
        Board board = new GeneralGame(8);
        char turn = board.getTurn();
        board.makeMove(2, 2, Cell.S);
        assertNotEquals(turn, board.getTurn());
    }

    @Test
    public void testGeneralGameOverA() {
        Board board = new GeneralGame(3);
        board.makeMove(1, 1, Cell.S);
        board.makeMove(2, 2, Cell.O);

        board.makeMove(3, 3, Cell.S);
        board.makeMove(1, 2, Cell.S);

        board.makeMove(2, 1, Cell.O);
        board.makeMove(3, 2, Cell.S);

        board.makeMove(1, 3, Cell.S);
        board.makeMove(2, 3, Cell.O);

        board.makeMove(3, 1, Cell.S);
        assertNotEquals(Board.GameState.A_WON, board.getGameState());
    }

    @Test
    public void testGeneralGameOverB() {
        Board board = new GeneralGame(3);
        board.makeMove(0, 0, Cell.S);
        board.makeMove(1, 1, Cell.O);

        board.makeMove(0, 1, Cell.S);
        board.makeMove(2, 2, Cell.S);

        board.makeMove(1, 0, Cell.O);
        board.makeMove(2, 1, Cell.S);

        board.makeMove(0, 2, Cell.S);
        board.makeMove(2, 0, Cell.S);
        assertNotEquals(Board.GameState.B_WON, board.getGameState());
    }

    @Test
    public void testGeneralGameOverTie() {
        Board board = new GeneralGame(3);
        board.makeMove(0, 0, Cell.O);
        board.makeMove(0, 1, Cell.O);

        board.makeMove(0, 3, Cell.O);
        board.makeMove(1, 1, Cell.O);

        board.makeMove(1, 2, Cell.O);
        board.makeMove(1, 3, Cell.O);

        board.makeMove(2, 1, Cell.O);
        board.makeMove(2, 0, Cell.O);
        assertNotEquals(Board.GameState.DRAW, board.getGameState());
    }

    @Test
    public void testCorrectBoardSize() {
        if (boardSize < 13 && boardSize > 2)
            assertEquals(" ", boardSize, boardSize);
    }

}