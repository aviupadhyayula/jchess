package org.cis120.chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("Chess");
        frame.setLocation(250, 250);
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Initializing");
        status_panel.add(status);
        JPanel game = new JPanel();
        final GameBoard board = new GameBoard(status, game);
        frame.add(game, BorderLayout.CENTER);
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);
        String instructText = "This implementation of chess follows the standard rules of two-player" +
                " chess. Click on a piece to show all its available moves. If under check, the king will" +
                " be highlighted with a red circle. The status bar at the bottom of the page will display" +
                " the state of the game. For the sake of simplicity, pawns are automatically promoted into " +
                " queens.";
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = new JTextArea(instructText, 2, 40);
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                JOptionPane.showMessageDialog(frame, ta, "Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        control_panel.add(instructions);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        board.reset();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}