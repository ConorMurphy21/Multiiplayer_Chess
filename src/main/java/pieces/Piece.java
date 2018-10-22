package pieces;

import highlighters.Highlighter;

public interface Piece {

    boolean isWhite();

    boolean hasMoved();

    Highlighter highlighter();

    void ini();
}
