package cache;

import pieces.Piece;

public class Promotion extends Move {

    char newType;

    public Promotion(int fromX, int fromY, int toX, int toY, char newType) {
        super(fromX, fromY, toX, toY);
        this.newType = newType;
    }

    public Promotion(Piece piece, int toX, int toY, char newType) {
        super(piece, toX, toY);
        this.newType = newType;
    }
}
