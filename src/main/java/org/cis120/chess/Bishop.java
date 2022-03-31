package org.cis120.chess;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(boolean color, Square square) {
        super(color, square);
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        ArrayList<Square> res = super.getDiagonalMoves(b);
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
            return "files/white_bishop.png";
        } else {
            return "files/black_bishop.png";
        }
    }
}