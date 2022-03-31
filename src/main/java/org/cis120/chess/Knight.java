package org.cis120.chess;
import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(boolean white, Square square) {
        super(white, square);
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        int rank = super.getSquare().getRank();
        int file = super.getSquare().getFile();
        ArrayList<Square> res = new ArrayList<>();
        Square[][] board = b.getBoard();
        for (int row = rank - 2; row <= rank + 2; row += 4) {
            try {
                if (canMove(board[row][file + 1])) {
                    res.add(board[row][file + 1]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                res = res;
            }
            try {
                if (canMove(board[row][file - 1])) {
                    res.add(board[row][file - 1]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                res = res;
            }
        }
        for (int row = rank - 1; row <= rank + 1; row += 2) {
            try {
                if (canMove(board[row][file + 2])) {
                    res.add(board[row][file + 2]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                res = res;
            }
            try {
                if (canMove(board[row][file - 2])) {
                    res.add(board[row][file - 2]);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                res = res;
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
            return "files/white_knight.png";
        } else {
            return "files/black_knight.png";
        }
    }
}
