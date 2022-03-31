package org.cis120.chess;
import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(boolean color, Square square) {
        super(color, square);
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        ArrayList<Square> total = super.getLinearMoves(b);
        ArrayList<Square> diagonal = super.getDiagonalMoves(b);
        total.addAll(diagonal);
        if (shouldCheck) {
            ArrayList<Square> proper = new ArrayList<>();
            for (Square sq : total) {
                if (!b.isCheck(getSquare(), sq)) {
                    proper.add(sq);
                }
            }
            return proper;
        }
        return total;
    }
    @Override
    public String getImage() {
        if (getColor()) {
            return "files/white_queen.png";
        } else {
            return "files/black_queen.png";
        }
    }
}
