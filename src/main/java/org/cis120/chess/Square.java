package org.cis120.chess;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Square extends JComponent {
    private Board board;
    private final boolean white;
    private Piece occupant;
    private final int rank;
    private final int file;
    private final int dimension;
    private boolean highlight;
    private boolean check;
    private boolean isChoice = false;
    public Square(Board board, boolean white, Piece occupant, int rank, int file) {
        this.rank = rank;
        this.file = file;
        this.dimension = 100;
        this.white = white;
        this.occupant = occupant;
        this.board = board;
    }
    public boolean getColor() {
        return white;
    }
    public void highlight() {
        highlight = true;
    }
    public void dehighlight() {
        highlight = false;
    }
    public boolean isHighlighted() {
        return highlight;
    }
    public void makeCheck() {
        check = true;
    }
    public boolean isCheck() {
        return check;
    }
    public void removeCheck() {
        check = false;
    }
    public void makeChoice() {
        isChoice = true;
    }
    public boolean isChoice() {
        return isChoice;
    }
    public void removeChoice() {
        isChoice = false;
    }
    public int getRank() {
        return rank;
    }
    public int getFile() {
        return file;
    }
    public void uncheck() {
        check = false;
    }
    public boolean isOccupied() {
        return !(occupant == null);
    }
    public Piece getOccupant() {
        return occupant;
    }
    public void placePiece(Piece p) {
        this.occupant = p;
        p.setSquare(this);
    }
    public void removePiece() {
        this.occupant = null;
    }
    @Override
    public void paintComponent(Graphics g) {
        if (white) {
            g.setColor(new Color(216, 217, 231, 255));

        } else {
            g.setColor(new Color(122, 157, 178, 255));
        }
        if (isChoice) {
            g.setColor(new Color(110, 176, 187, 255));
        }
        g.fillRect(0, 0, dimension, dimension);
        if (isOccupied()) {
            BufferedImage pieceImage = null;
            try {
                pieceImage = ImageIO.read(new File(occupant.getImage()));
                g.drawImage(pieceImage, 0, 0, dimension, dimension, null, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (check) {
            g.setColor(new Color(229, 79, 79));
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(6.0F));
            g.drawOval(2, 2, (int)(0.95 * dimension), (int)(0.95 * dimension));
        } else {
            if (highlight) {
                if (isOccupied()) {
                    if (white) {
                        g.setColor(new Color(128, 139, 141, 255));
                    } else {
                        g.setColor(new Color(91, 119, 136, 255));
                    }
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setStroke(new BasicStroke(6.0F));
                    g.drawOval(2, 2, (int)(0.95 * dimension), (int)(0.95 * dimension));
                } else {
                    if (white) {
                        g.setColor(new Color(150, 164, 169, 255));
                    } else {
                        g.setColor(new Color(105, 142, 163, 255));
                    }
                    g.fillOval(dimension / 2 - (int)(0.15 * dimension), dimension / 2 - (int)(0.15 * dimension),
                            (int)(0.3 * dimension), (int)(0.3 * dimension));
                }
            }
        }
    }
}
