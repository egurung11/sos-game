package test;

import main.Board;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameGraphicsTest {
    private Board board;

    @Before
    public void setUp() throws Exception {
        //board = SimpleGame();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmptyBoard() {
        int row = 8;
        int column = 8;
        int boardSize = 0;
        assertEquals("", row >= 0 && row < boardSize && column >= 0 && column < boardSize, false);

    }

    @Test
    public void test() {
        //board.makeMove(0, 0, Cell);
        //board.makeMove(1, 1);
        //new TicTacToeGUI(game);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
