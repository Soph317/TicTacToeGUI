import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TicTacToeFrame extends JFrame {
    private static final int ROW = 3;
    private static final int COL = 3;
    private String[][] board = new String[ROW][COL];
    private TicTacToeTile[][] tiles = new TicTacToeTile[ROW][COL];

    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    JPanel boardPanel, controlPanel;
    JButton quitBtn;

    private String currentPlayer = "X";
    private int moveCnt = 0;

    public TicTacToeFrame() {
        super("Tic Tac Toe");
        setSize(500, 550);


        createBoardPnl();
        createControlPnl();
        resetGame();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createBoardPnl() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROW, COL));
        TileClickListener listener = new TileClickListener();

        for (int r = 0; r < ROW; r++) {
            for (int c = 0; c < COL; c++) {
                tiles[r][c] = new TicTacToeTile(r, c);
                tiles[r][c].addActionListener(listener);
                boardPanel.add(tiles[r][c]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);
    }

    private void createControlPnl() {
        controlPanel = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to quit?",
                    "Quit Game",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        controlPanel.add(quitBtn);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private class TileClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeTile clickedTile = (TicTacToeTile) e.getSource();
            int r = clickedTile.getRow();
            int c = clickedTile.getCol();


            if (!isValidMove(r, c)) {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "Invalid move! That space is already taken.",
                        "Illegal Move",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            board[r][c] = currentPlayer;
            clickedTile.setText(currentPlayer);
            moveCnt++;


            if (moveCnt >= MOVES_FOR_WIN && isWin(currentPlayer)) {
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        "Player " + currentPlayer + " wins!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                );
                promptPlayAgain();
                return;
            }

            if (moveCnt >= MOVES_FOR_TIE && isTie()) {
                String tieMsg = (moveCnt == 9)
                        ? "It's a tie! (Full Board)"
                        : "It's a tie! (No winning line remains)";
                JOptionPane.showMessageDialog(
                        TicTacToeFrame.this,
                        tieMsg,
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                );
                promptPlayAgain();
                return;
            }

            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

        private void resetGame() {
            for (int r = 0; r < ROW; r++) {
                for (int c = 0; c < COL; c++) {
                    board[r][c] = "";
                    tiles[r][c].setText("");
                }
            }
            moveCnt = 0;
            currentPlayer = "X";
        }

        private boolean isValidMove(int row, int col) {
            return board[row][col].equals("");

        }

        private void promptPlayAgain() {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to play another game?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION
            );
            if (response == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }

        private boolean isWin(String player) {
            if (isColWin(player) || isRowWin(player) || isDiagonalWin(player)) {
                return true;
            }

            return false;
        }

        private boolean isColWin(String player) {
            for (int col = 0; col < COL; col++) {
                if (board[0][col].equals(player) &&
                        board[1][col].equals(player) &&
                        board[2][col].equals(player)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isRowWin(String player) {

            for (int row = 0; row < ROW; row++) {
                if (board[row][0].equals(player) &&
                        board[row][1].equals(player) &&
                        board[row][2].equals(player)) {
                    return true;
                }
            }
            return false;
        }

        private boolean isDiagonalWin(String player) {
            if (board[0][0].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][2].equals(player)) {
                return true;
            }
            if (board[0][2].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][0].equals(player)) {
                return true;
            }
            return false;
        }


        private boolean isTie() {
            boolean xFlag = false;
            boolean oFlag = false;

            for (int row = 0; row < ROW; row++) {
                if (board[row][0].equals("X") ||
                        board[row][1].equals("X") ||
                        board[row][2].equals("X")) {
                    xFlag = true;
                }
                if (board[row][0].equals("O") ||
                        board[row][1].equals("O") ||
                        board[row][2].equals("O")) {
                    oFlag = true;
                }

                if (!(xFlag && oFlag)) {
                    return false;
                }

                xFlag = oFlag = false;

            }

            for (int col = 0; col < COL; col++) {
                if (board[0][col].equals("X") ||
                        board[1][col].equals("X") ||
                        board[2][col].equals("X")) {
                    xFlag = true;
                }
                if (board[0][col].equals("O") ||
                        board[1][col].equals("O") ||
                        board[2][col].equals("O")) {
                    oFlag = true;
                }

                if (!(xFlag && oFlag)) {
                    return false;
                }
            }

            xFlag = oFlag = false;

            if (board[0][0].equals("X") ||
                    board[1][1].equals("X") ||
                    board[2][2].equals("X")) {
                xFlag = true;
            }
            if (board[0][0].equals("O") ||
                    board[1][1].equals("O") ||
                    board[2][2].equals("O")) {
                oFlag = true;
            }
            if (!(xFlag && oFlag)) {
                return false;
            }
            xFlag = oFlag = false;

            if (board[0][2].equals("X") ||
                    board[1][1].equals("X") ||
                    board[2][0].equals("X")) {
                xFlag = true;
            }
            if (board[0][2].equals("O") ||
                    board[1][1].equals("O") ||
                    board[2][0].equals("O")) {
                oFlag = true;
            }
            if (!(xFlag && oFlag)) {
                return false;
            }


            return true;
        }
}


