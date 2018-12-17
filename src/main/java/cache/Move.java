package cache;

import board.Board;
import pieces.Piece;
import utils.Vec;

public abstract class Move {
    Piece piece;
    int fromX, fromY, toX, toY;

    private Board board = Board.getInstance();

    Move(int fromX, int fromY, int toX, int toY) {
        this.piece = board.getPieces()[fromX][fromY];
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public Move(Piece piece, int toX, int toY) {
        this.piece = piece;
        this.fromX = piece.getX();
        this.fromY = piece.getY();
        this.toX = toX;
        this.toY = toY;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public Vec getFromVec(){
        return new Vec(fromX,fromY);
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public Vec getToVec(){
        return new Vec(toX,toY);
    }

    public abstract char getChar();


}
