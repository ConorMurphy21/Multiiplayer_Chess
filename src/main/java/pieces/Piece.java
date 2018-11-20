package pieces;

import highlighters.Highlighter;
import pieces.graphics.PieceNode;

public interface Piece {

    boolean isWhite();

    boolean hasMoved();

    void setMoved();

    Highlighter highlighter();

    PieceNode getNode();

    void movePiece(double x, double y);

    int getX();

    int getY();

    void ini();
}
