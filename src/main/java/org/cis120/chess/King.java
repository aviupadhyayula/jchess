package org.cis120.chess;
import java.util.ArrayList;

public class King extends Piece {
    private boolean castle;
    public King(boolean color, Square square) {
        super(color, square);
        castle = true;
    }
    @Override
    public ArrayList<Square> getMoves(Board b, boolean shouldCheck) {
        ArrayList<Square> res = new ArrayList<>();
        int rank = super.getSquare().getRank();
        int file = super.getSquare().getFile();
        Square[][] board = b.getBoard();
        for (int row = Math.max(0, rank - 1); row <= Math.min(rank + 1, board.length - 1); row++) {
            for (int col = Math.max(0, file - 1); col <= Math.min(file + 1, board[row].length - 1); col++) {
                if (super.canMove(board[row][col])) {
                    res.add(board[row][col]);
                }
            }
        }
        if (shouldCheck) {
            if (!hasMoved()) {
                for (int col = file + 1; col < board[rank].length; col++) {
                    if (board[rank][col].isOccupied()) {
                        if (board[rank][col].getOccupant().getColor() == getColor()) {
                            if (board[rank][col].getOccupant() instanceof Rook) {
                                if (!board[rank][col].getOccupant().hasMoved()) {
                                    if (!b.isCheck(getSquare(), board[rank][file + 1])) {
                                        System.out.println(file + 2);
                                        res.add(board[rank][file + 2]);
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
                for (int col = file - 1; col >= 0; col--) {
                    if (board[rank][col].isOccupied()) {
                        if (board[rank][col].getOccupant().getColor() == getColor()) {
                            if (board[rank][col].getOccupant() instanceof Rook) {
                                if (!board[rank][col].getOccupant().hasMoved()) {
                                    if (!b.isCheck(getSquare(), board[rank][file - 1])) {
                                        if (!b.isCheck(getSquare(), board[rank][file - 2])) {
                                            res.add(board[rank][file - 2]);
                                        }
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (shouldCheck) {
            ArrayList<Square> proper = new ArrayList();
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
            return "files/white_king.png";
        } else {
            return "files/black_king.png";
        }
    }
}
