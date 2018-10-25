package pieces;

import highlighters.Highlighter;
import pieces.graphics.PieceNode;

public interface Piece {

    boolean isWhite();

    boolean hasMoved();

    Highlighter highlighter();

    PieceNode getNode();

    void movePiece(int x, int y);

    int getX();

    int getY();

    void ini();
}
