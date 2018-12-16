package cache;

import pieces.Piece;

public class EnPassant extends Move{
    public EnPassant(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY, toX, toY);
    }

    public EnPassant(Piece piece, int toX, int toY) {
        super(piece, toX, toY);
    }

}
