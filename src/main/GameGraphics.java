package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import main.Board.Cell;

/*
 * The GUI code was adapted from code originally written by
 * Prof. Chua Hock Chuan, Nanyang Technological University
 */

public class GameGraphics extends JFrame {

    public static final int CANVAS_SIZE = 400;

    public static final int SYMBOL_STROKE_WIDTH = 1;

    Container contentPane;
    JRadioButton blueS;
    JRadioButton redS;
    JRadioButton blueComputer;
    JRadioButton redComputer;
    JButton replay;
    JLabel turn;
    GameBoardCanvas gameBoardCanvas;
    JLabel redScore;
    JLabel blueScore;

    private Board board;
    private JLabel gameStatusBar;

    Timer timer;

    public GameGraphics() {
        // Check for computer move every 1 second
        timer = new Timer(500, e -> {
            if ((board.getTurn() == 'A' && blueComputer.isSelected()
                    || board.getTurn() == 'B' && redComputer.isSelected())
                    && board.getGameState() == Board.GameState.PLAYING) {
                board.computerMove();
                blueScore.setText("Score: " + board.getPlayerAScore());
                redScore.setText("Score: " + board.getPlayerBScore());
                gameStatusBar.setText(getGameStatusText());

                setTurnLabel(board.getTurn() == 'A' ? "Blue" : "Red");
                contentPane.repaint();
            }

        });
        this.board = new GeneralGame(8);
        setContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setTitle("S O S");
        setResizable(false);
        setVisible(true);
    }

    public void setTurnLabel(String newTurn) {
        turn.setText("Current Turn: " + newTurn);
    }

    private void setGameBoardCanvas() {
        gameBoardCanvas = new GameBoardCanvas();

        gameBoardCanvas.setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
    }

    private void setContentPane () {
        setGameBoardCanvas();

        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(gameBoardCanvas, BorderLayout.CENTER);

        JRadioButton generalGame = new JRadioButton("General Game");
        generalGame.setSelected(true);
        JRadioButton simpleGame = new JRadioButton("Simple Game");
        JLabel modeLabel = new JLabel("SOS");

        ButtonGroup gameModeGroup = new ButtonGroup();
        gameModeGroup.add(generalGame);
        gameModeGroup.add(simpleGame);

        JSpinner sizeOfBoard = new JSpinner(new SpinnerNumberModel(8, 3, 12 ,1));

        JPanel modePanel = new JPanel();
        modePanel.add(modeLabel);
        modePanel.add(generalGame);
        modePanel.add(simpleGame);
        modePanel.add(sizeOfBoard);

        contentPane.add(modePanel, BorderLayout.NORTH);

        // Blue Side Buttons
        JLabel blueLabel = new JLabel("Blue Player");
        JRadioButton blueHuman = new JRadioButton("Human");
        blueHuman.setSelected(true);
        blueComputer = new JRadioButton("Computer");
        blueS = new JRadioButton("S");
        blueS.setSelected(true);
        JRadioButton blueO = new JRadioButton("O");
        blueScore = new JLabel("Score: 0");

        ButtonGroup blueGroup = new ButtonGroup();
        blueGroup.add(blueS);
        blueGroup.add(blueO);

        ButtonGroup blueGroup2 = new ButtonGroup();
        blueGroup2.add(blueHuman);
        blueGroup2.add(blueComputer);

        JPanel bluePanel = new JPanel();
        bluePanel.add(blueLabel);
        bluePanel.add(blueHuman);
        bluePanel.add(blueS);
        bluePanel.add(blueO);
        bluePanel.add(blueComputer);
        bluePanel.add(blueScore);

        contentPane.add(bluePanel, BorderLayout.WEST);

        // Red Side Buttons
        JLabel redLabel = new JLabel("Red Player");
        redS = new JRadioButton("S");
        redS.setSelected(true);
        JRadioButton redO = new JRadioButton("O");
        JRadioButton redHuman = new JRadioButton("Human");
        redHuman.setSelected(true);
        redComputer = new JRadioButton("Computer");
        redScore = new JLabel("Score: 0");

        ButtonGroup redGroup = new ButtonGroup();
        redGroup.add(redS);
        redGroup.add(redO);

        ButtonGroup redGroup2 = new ButtonGroup();
        redGroup2.add(redHuman);
        redGroup2.add(redComputer);

        JPanel redPanel = new JPanel();
        redPanel.add(redLabel);
        redPanel.add(redHuman);
        redPanel.add(redS);
        redPanel.add(redO);
        redPanel.add(redComputer);
        redPanel.add(redScore);

        contentPane.add(redPanel, BorderLayout.EAST);

        // Current Turn
        turn = new JLabel("Current Turn: " + (board.getTurn() == 'A' ? "Blue" : "Red"));

        JPanel turnPanel = new JPanel();
        turnPanel.add(turn);

        JButton submit = new JButton("Start Game!");
        JButton reset = new JButton("Reset");
        JButton fileRecord = new JButton("Send to File");
        replay = new JButton("Replay");
        reset.setVisible(false);
        replay.setVisible(false);

        submit.addActionListener(e -> {
            board = generalGame.isSelected()
                    ? new GeneralGame((Integer) sizeOfBoard.getValue())
                    : new SimpleGame((Integer) sizeOfBoard.getValue());
            board.setGameState(Board.GameState.PLAYING);
            setGameBoardCanvas();
            timer.start();
            submit.setVisible(false);
            reset.setVisible(true);
            contentPane.repaint();
        });

        reset.addActionListener(e-> {
            board.resetGame();
            gameStatusBar.setText("");
            timer.stop();
            reset.setVisible(false);
            submit.setVisible(true);
            contentPane.repaint();
        });

        fileRecord.addActionListener(e-> {
            board.gameToFile();
            fileRecord.setVisible(false);
            replay.setVisible(true);
        });

        replay.addActionListener(e-> {
            board.replayGame();
            replay.setVisible(false);
            fileRecord.setVisible(true);
            contentPane.repaint();
        });

        turnPanel.add(submit);
        turnPanel.add(reset);
        turnPanel.add(fileRecord);
        turnPanel.add(replay);

        gameStatusBar = new JLabel("  ");
        //gameStatusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        gameStatusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        turnPanel.add(gameStatusBar);

        contentPane.add(turnPanel, BorderLayout.SOUTH);

        // Size of panels
        modePanel.setPreferredSize(new Dimension(100, 100));
        bluePanel.setPreferredSize(new Dimension(100, 100));
        redPanel.setPreferredSize(new Dimension(100, 100));
        turnPanel.setPreferredSize(new Dimension(100, 100));
        contentPane.setVisible(true);
    }

    private String getGameStatusText() {
        switch (board.getGameState()) {
            case A_WON:
                return "Blue Player Wins!";
            case B_WON:
                return "Red Player Wins!";
            case DRAW:
                return "Tie Game!";
            default:
                return "";
        }
    }

    class GameBoardCanvas extends JPanel {
        GameBoardCanvas() {
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (board.getGameState() == Board.GameState.PLAYING) {

                        System.out.println("Valid click");
                        int rowSelected = e.getY() / (CANVAS_SIZE / board.getBoardSize());
                        int colSelected = e.getX() / (CANVAS_SIZE / board.getBoardSize());
                        //board.makeMove(rowSelected, colSelected);
                        Cell move;
                        if (board.getTurn() == 'A') {
                            move = blueS.isSelected() ? Cell.S : Cell.O;
                        } else {
                            move = redS.isSelected() ? Cell.S : Cell.O;
                        }
                        board.makeMove(rowSelected, colSelected, move);

                        blueScore.setText("Score: " + board.getPlayerAScore());
                        redScore.setText("Score: " + board.getPlayerBScore());
                        gameStatusBar.setText(getGameStatusText());

                        setTurnLabel(board.getTurn() == 'A' ? "Blue" : "Red");
                    }
                    repaint();
                }
            });
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (board.getGameState() == Board.GameState.PLAYING) {
                        System.out.println("Valid click");
                        int rowSelected = e.getY() / (CANVAS_SIZE / board.getBoardSize());
                        int colSelected = e.getX() / (CANVAS_SIZE / board.getBoardSize());
                        //board.makeMove(rowSelected, colSelected);
                        Cell move;
                        if (board.getTurn() == 'A') {
                            move = blueS.isSelected() ? Cell.S : Cell.O;
                        } else {
                            move = redS.isSelected() ? Cell.S : Cell.O;
                        }
                        board.makeMove(rowSelected, colSelected, move);

                        blueScore.setText("Score: " + board.getPlayerAScore());
                        redScore.setText("Score: " + board.getPlayerBScore());
                        gameStatusBar.setText(getGameStatusText());

                        setTurnLabel(board.getTurn() == 'A' ? "Blue" : "Red");
                    }

                    repaint();
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
            drawGridLines(g);
            drawBoard(g);
        }

        private void drawGridLines(Graphics g) {
            int lineThickness = 4;
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < board.getBoardSize(); ++row) {
                g.fillRoundRect(0, CANVAS_SIZE / board.getBoardSize() * row - (lineThickness/2),
                        CANVAS_SIZE - 1, lineThickness, board.getBoardSize(), board.getBoardSize());
            }
            for (int col = 1; col < board.getBoardSize(); ++col) {
                g.fillRoundRect(CANVAS_SIZE / board.getBoardSize() * col - (lineThickness/2), 0,
                        lineThickness, CANVAS_SIZE - 1, board.getBoardSize(), board.getBoardSize());
            }
        }

        private void drawBoard(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;

            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < board.getBoardSize(); ++row) {
                for (int col = 0; col < board.getBoardSize(); ++col) {
                    int x = col * (CANVAS_SIZE / board.getBoardSize());
                    int y = (row + 1) * (CANVAS_SIZE / board.getBoardSize());

                    // Set font size to 90% of cell size
                    g2d.setFont(new Font("Arial", Font.PLAIN, (int)(.9 * (CANVAS_SIZE / board.getBoardSize()))));
                    g2d.setColor(Color.BLACK);
                    if (board.getCell(row, col) == Cell.S) {
                        g2d.drawString("S", x + 8, y - 8);
                    } else if (board.getCell(row, col) == Cell.O) {
                        g2d.drawString("O", x + 8, y - 8);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGraphics::new);
    }
}
