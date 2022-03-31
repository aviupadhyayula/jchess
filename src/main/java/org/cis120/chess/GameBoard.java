package org.cis120.chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private Board chess;
    private JLabel status;
    private Square[][] board;
    boolean selc = false;
    boolean isCheckmate = false;
    boolean isStalemate = false;
    private Square selected;
    public static final int BOARD_DIM = 800;
    public static final int SQUARE_DIM = BOARD_DIM / 8;
    public GameBoard(JLabel statusInit, JPanel game) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        chess = new Board();
        status = statusInit;
        game.setLayout(new BorderLayout());
        game.add(this, BorderLayout.CENTER);
        updateStatus();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (selc) {
                    Point p = e.getPoint();
                    Square target = chess.getSquare(p.y / 100, p.x / 100);
                    if (selected.getOccupant().getMoves(chess, true).contains(target)) {
                        chess.deselect();
                        chess.move(selected, target);
                        selc = false;
                        isCheckmate = chess.isCheckmate();
                        isStalemate = chess.isStalemate();
                        updateStatus();
                    } else {
                        chess.deselect();
                        if (selected != target) {
                            selected = chess.select(p.y / 100, p.x / 100);
                            if (selected == null) {
                                selc = false;
                            }
                        } else {
                            selc = false;
                            selected = null;
                        }
                    }
                } else {
                    Point p = e.getPoint();
                    selected = chess.select(p.y / 100, p.x / 100);
                    if (selected != null) {
                        selc = true;
                    }
                }
            }
        });
    }
    public void updateStatus() {
        String side = "";
        if (chess.getSide()) {
            side = "White";
        } else {
            side = "Black";
        }
        if (isCheckmate) {
            status.setText("Game Over");
        } else if (isStalemate) {
            status.setText("Game Over");
        } else {
            status.setText(side + "'s Turn");
        }
    }
    public void reset() {
        removeAll();
        chess.resetBoard();
        board = chess.getBoard();
        setLayout(new GridLayout(8, 8));
        for (int rank = 0; rank < board.length; rank++) {
            for (int file = 0; file < board[rank].length; file++) {
                add(board[rank][file]);
            }
        }
        revalidate();
        repaint();
        requestFocusInWindow();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_DIM, BOARD_DIM);
    }
}