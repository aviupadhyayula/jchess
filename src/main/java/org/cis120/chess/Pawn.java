package org.cis120.chess;
import java.util.ArrayList;

public class Pawn extends Piece {
    boolean enPassant;
    public Pawn(boolean white, Square square, boolean enPassant) {
        super(white, square);
        this.enPassant = enPassant;
    }
    public boolean getEnPassant() {
        return this.enPassant;
    }
    public void removePassant() {
        enPassant = false;
    }
    @Override
    public void moved() {
        super.moved();
        enPassant = true;
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        ArrayList<Square> res = new ArrayList<>();
        Square[][] board = b.getBoard();
        int rank = super.getSquare().getRank();
        int file = super.getSquare().getFile();
        int movement = 1;
        if (super.getColor()) {
            movement = -1;
        }
        if (rank + movement >= 0 && rank + movement < board.length) {
            if (!board[rank + movement][file].isOccupied()) {
                res.add(board[rank + movement][file]);
            }
            if (!hasMoved()) {
                if (!board[rank + 2 * movement][file].isOccupied()) {
                    res.add(board[rank + 2 * movement][file]);
                }
            }
        }
        for (int col = file - 1; col <= file + 1; col += 2) {
            try {
                if (board[rank][col].isOccupied()) {
                    if (board[rank][col].getOccupant() instanceof Pawn) {
                        Pawn neighbor = (Pawn) (board[rank][col].getOccupant());
                        if (neighbor.getEnPassant()) {
                            if (rank + movement < board.length && rank + movement >= 0) {
                                res.add(board[rank + movement][col]);
                            }
                        }
                    }
                }
                if (rank + movement < board.length && rank + movement >= 0) {
                    if (board[rank + movement][col].isOccupied()) {
                        if (super.canMove(board[rank + movement][col])) {
                            res.add(board[rank + movement][col]);
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                shouldCheck = shouldCheck;
            }
        }
        if (shouldCheck) {
            ArrayList<Square> proper = new ArrayList<>();
            for (Square sq : res) {
                if (!b.isCheck(getSquare(), sq)) {
                    proper.add(sq);
                }
            }
            return proper;
        }
        return res;
    }
    @Override
    public String getImage() {
        if (getColor()) {
            return "files/white_pawn.png";
        } else {
            return "files/black_pawn.png";
        }
    }
}
