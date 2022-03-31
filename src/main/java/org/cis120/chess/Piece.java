package org.cis120.chess;
import java.util.ArrayList;

public abstract class Piece {
    private boolean white;
    private Square square;
    private boolean moved;
    public Piece(boolean white, Square square) {
        this.white = white;
        this.square = square;
        moved = false;
    }
    public void moved() {
        moved = true;
    }
    public boolean hasMoved() {
        return moved;
    }
    public boolean getColor() {
        return white;
    }
    public Square getSquare() {
        return square;
    }
    public void setSquare(Square sq) {
        this.square = sq;
    }
    public boolean canMove(Square sq) {
        if (!sq.isOccupied()) {
            return true;
        } else {
            if (this.getColor() != sq.getOccupant().getColor()) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<Square> getLinearMoves(Board b) {
        int rank = getSquare().getRank();
        int file = getSquare().getFile();
        ArrayList<Square> res = new ArrayList<>();
        Square[][] board = b.getBoard();
        for (int i = rank - 1; i >= 0; i--) {
            if (canMove(board[i][file])) {
                res.add(board[i][file]);
                if (board[i][file].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = rank + 1; i < board.length; i++) {
            if (canMove(board[i][file])) {
                res.add(board[i][file]);
                if (board[i][file].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = file + 1; i < board[rank].length; i++) {
            if (canMove(board[rank][i])) {
                res.add(board[rank][i]);
                if (board[rank][i].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = file - 1; i >= 0; i--) {
            if (canMove(board[rank][i])) {
                res.add(board[rank][i]);
                if (board[rank][i].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        return res;
    }
    public ArrayList<Square> getDiagonalMoves(Board b) {
        int rank = this.getSquare().getRank();
        int file = this.getSquare().getFile();
        ArrayList<Square> res = new ArrayList<>();
        Square[][] board = b.getBoard();
        for (int i = rank - 1, j = file - 1; i >= 0 && j >= 0; i--, j--) {
            if (canMove(board[i][j])) {
                res.add(board[i][j]);
                if (board[i][j].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = rank - 1, j = file + 1; i >= 0 && j < board[i].length; i--, j++) {
            if (canMove(board[i][j])) {
                res.add(board[i][j]);
                if (board[i][j].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = rank + 1, j = file + 1; i < board.length && j < board[i].length; i++, j++) {
            if (canMove(board[i][j])) {
                res.add(board[i][j]);
                if (board[i][j].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = rank + 1, j = file - 1; i < board.length && j >= 0; i++, j--) {
            if (canMove(board[i][j])) {
                res.add(board[i][j]);
                if (board[i][j].isOccupied()) {
                    break;
                }
            } else {
                break;
            }
        }
        return res;
    }
    public abstract ArrayList<Square> getMoves(Board b, boolean shouldCheck);
    public abstract String getImage();
}
