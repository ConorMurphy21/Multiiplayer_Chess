package cache;

import pieces.Piece;

public class NormalMove extends Move {
    public NormalMove(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY, toX, toY);
    }

    public NormalMove(Piece piece, int toX, int toY) {
        super(piece, toX, toY);
    }
}
