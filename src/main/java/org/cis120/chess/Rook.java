package org.cis120.chess;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(boolean white, Square square) {
        super(white, square);
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        ArrayList<Square> res = super.getLinearMoves(b);
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
            return "files/white_rook.png";
        } else {
            return "files/black_rook.png";
        }
    }
}