package pieces;

import highlighters.Highlighter;
import javafx.beans.property.IntegerProperty;

public abstract class PieceBase implements Piece {


    IntegerProperty xProperty,yProperty;

    boolean isWhite, hasMoved;

    Highlighter highlighter;


    public PieceBase(){}
    public PieceBase(boolean isWhite, Highlighter highlighter){
        this.isWhite = isWhite;
        this.highlighter = highlighter;
        hasMoved = false;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }
}
