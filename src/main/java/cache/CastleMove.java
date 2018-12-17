package cache;

import board.Board;
import pieces.Piece;

public class CastleMove extends Move {


    Piece rook;
    int rFromX, rFromY, rToX, rToY;

    CastleMove(int fromX, int fromY, int toX, int toY, int rFromX, int rFromY, int rToX, int rToY) {
        super(fromX, fromY, toX, toY);
        this.rFromX = rFromX;
        this.rFromY = rFromY;
        this.rToX = rToX;
        this.rToY = rToY;
        rook = Board.getInstance().getPieces()[rFromX][rFromY];
    }

    public CastleMove(Piece piece, int toX, int toY, Piece rook, int rToX, int rToY) {
        super(piece, toX, toY);
        this.rook = rook;
        this.rToX = rToX;
        this.rToY = rToY;
        this.rFromX = rook.getX();
        this.rFromY = rook.getY();
    }

    @Override
    public char getChar() {
        return 'c';
    }

}
