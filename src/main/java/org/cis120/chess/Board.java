package org.cis120.chess;
import java.util.ArrayList;
import java.util.List;

public class Board {
    Square[][] board;
    private boolean turn;
    public Board() {
        this.resetBoard();
    }
    public void resetBoard() {
        board = new Square[8][8];
        turn = true;
        boolean color = false;
        for (int row = 0; row < board.length; row++) {
            color = !color;
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = new Square(this, color, null, row, col);
                color = !color;
            }
        }
        board[0][0].placePiece(new Rook(false, board[0][0]));
        board[0][1].placePiece(new Knight(false, board[0][1]));
        board[0][2].placePiece(new Bishop(false, board[0][2]));
        board[0][3].placePiece(new Queen(false, board[0][3]));
        board[0][4].placePiece(new King(false, board[0][4]));
        board[0][5].placePiece(new Bishop(false, board[0][5]));
        board[0][6].placePiece(new Knight(false, board[0][6]));
        board[0][7].placePiece(new Rook(false, board[0][7]));
        for (int col = 0; col < board[1].length; col++) {
            board[1][col].placePiece(new Pawn(false, board[1][col], false));
        }
        board[7][0].placePiece(new Rook(true, board[7][0]));
        board[7][1].placePiece(new Knight(true, board[7][1]));
        board[7][2].placePiece(new Bishop(true, board[7][2]));
        board[7][3].placePiece(new Queen(true, board[7][3]));
        board[7][4].placePiece(new King(true, board[7][4]));
        board[7][5].placePiece(new Bishop(true, board[7][5]));
        board[7][6].placePiece(new Knight(true, board[7][6]));
        board[7][7].placePiece(new Rook(true, board[7][6]));
        for (int col = 0; col < board[6].length; col++) {
            board[6][col].placePiece(new Pawn(true, board[6][col], false));
        }
    }
    public boolean getSide() {
        return turn;
    }
    public Square[][] getBoard() {
        return board;
    }
    public Square select(int rank, int file) {
        Square selected = board[rank][file];
        List<Square> moves = new ArrayList<>();
        if (selected.isOccupied()) {
            if (selected.getOccupant().getColor() == turn) {
                selected.makeChoice();
                selected.repaint();
                moves = selected.getOccupant().getMoves(this, true);
                for (Square s : moves) {
                    s.highlight();
                    s.repaint();
                }
                return selected;
            }
        }
        return null;
    }
    public void deselect() {
        List<Square> ret = new ArrayList<Square>();
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isChoice()) {
                    col.removeChoice();
                    col.repaint();
                }
                if (col.isHighlighted()) {
                    ret.add(col);
                    col.dehighlight();
                    col.repaint();
                }
                if (col.isCheck()) {
                    col.removeCheck();
                    col.repaint();
                }
            }
        }
    }
    public void move(Square selected, Square destination) {
        boolean moved = false;
        if (selected.getOccupant() instanceof King) {
            if (Math.abs(destination.getFile() - selected.getFile()) == 2) {
                moved = true;
                int direction = -1;
                if (destination.getFile() > selected.getFile()) {
                    direction = 1;
                }
                for (int i = selected.getFile() + direction; i < 8; i += direction) {
                    if (board[selected.getRank()][i].getOccupant() instanceof Rook) {
                        Piece r = board[selected.getRank()][i].getOccupant();
                        destination.placePiece(selected.getOccupant());
                        destination.getOccupant().moved();
                        selected.removePiece();
                        board[selected.getRank()][destination.getFile() - direction].placePiece(r);
                        board[selected.getRank()][destination.getFile() - direction].getOccupant().moved();
                        board[selected.getRank()][i].removePiece();
                        board[selected.getRank()][i].repaint();
                        for (i = selected.getFile() + direction; i < 8; i += direction) {
                            board[selected.getRank()][i].repaint();
                            if (Math.abs(i - selected.getFile()) >= 3) {
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } else if (selected.getOccupant() instanceof Pawn) {
            if (destination.getRank() == 7 || destination.getRank() == 0) {
                destination.placePiece(selected.getOccupant());
                selected.getOccupant().moved();
                selected.removePiece();
                destination.placePiece(new Queen(turn, destination));
                destination.repaint();
                moved = true;
            }
            if (destination.getFile() != selected.getFile()) {
                boolean enPassant = false;
                try {
                    if (board[selected.getRank()][destination.getFile()].getOccupant() instanceof Pawn) {
                        if (board[selected.getRank()][destination.getFile()].getOccupant().getColor() != turn) {
                            if (((Pawn) board[selected.getRank()][destination.getFile()].getOccupant()).getEnPassant()) {
                                enPassant = true;
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException e) {
                    enPassant = enPassant;
                }
                if (enPassant) {
                    destination.placePiece(selected.getOccupant());
                    selected.removePiece();
                    board[selected.getRank()][destination.getFile()].removePiece();
                    moved = true;
                    selected.repaint();
                    board[selected.getRank()][destination.getFile()].repaint();
                }
            }
        }
        if (!moved) {
            destination.placePiece(selected.getOccupant());
            selected.getOccupant().moved();
            selected.removePiece();
        }
        List<Square> places = new ArrayList<Square>();
        if (inCheck() != null) {
            inCheck().makeCheck();
            places.add(inCheck());
        }
        places.add(destination);
        places.add(selected);
        for (Square sq : places) {
            sq.repaint();
        }
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied()) {
                    if (col.getOccupant() instanceof Pawn) {
                        if (col.getColor() == !turn) {
                            Pawn p = (Pawn) col.getOccupant();
                            p.removePassant();
                        }
                    }
                }
            }
        }
        turn = !turn;
    }
    public Square getSquare(int rank, int file) {
        return board[rank][file];
    }
    public Square inCheck() {
        Square oppKing = null;
        ArrayList<Square> myMoves = new ArrayList<>();
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied()) {
                    if (col.getOccupant().getColor() == turn) {
                        myMoves.addAll(col.getOccupant().getMoves(this, true));
                    } else {
                        if (col.getOccupant() instanceof King) {
                            oppKing = col;
                        }
                    }
                }
            }
        }
        if (myMoves.contains(oppKing)) {
            return oppKing;
        }
        return null;
    }
    public boolean isCheckmate() {
        ArrayList<Square> moves = new ArrayList<>();
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() == turn) {
                    moves.addAll(col.getOccupant().getMoves(this, true));
                }
            }
        }
        if (moves.size() == 0 && inCheck() != null) {
            return true;
        }
        moves = new ArrayList<>();
        turn = !turn;
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() == turn) {
                    moves.addAll(col.getOccupant().getMoves(this, true));
                }
            }
        }
        if (moves.size() == 0 && inCheck() != null) {
            return true;
        }
        turn = !turn;
        return false;
    }
    public boolean isStalemate() {
        ArrayList<Square> moves = new ArrayList<>();
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() == turn) {
                    moves.addAll(col.getOccupant().getMoves(this, true));
                }
            }
        }
        if (moves.size() == 0 && inCheck() == null) {
            return true;
        }
        moves = new ArrayList<>();
        turn = !turn;
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() == turn) {
                    moves.addAll(col.getOccupant().getMoves(this, true));
                }
            }
        }
        if (moves.size() == 0 && inCheck() == null) {
            return true;
        }
        turn = !turn;
        return false;
    }
    public boolean isCheck(Square source, Square destination) {
        Piece mine = source.getOccupant();
        Piece temp = null;
        if (destination.isOccupied()) {
            temp = destination.getOccupant();
        }
        destination.placePiece(source.getOccupant());
        source.removePiece();
        Square kingSquare = null;
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() == turn && col.getOccupant() instanceof King) {
                    kingSquare = col;
                    break;
                }
            }
        }
        boolean isCheck = false;
        for (Square[] row : board) {
            for (Square col : row) {
                if (col.isOccupied() && col.getOccupant().getColor() != turn) {
                    if (col.getOccupant().getMoves(this, false).contains(kingSquare)) {
                        isCheck = true;
                        break;
                    }
                }
            }
        }
        destination.removePiece();
        if (temp != null) {
            destination.placePiece(temp);
        }
        source.placePiece(mine);
        return isCheck;
    }
}