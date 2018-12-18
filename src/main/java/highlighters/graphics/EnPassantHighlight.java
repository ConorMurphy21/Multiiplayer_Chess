package highlighters.graphics;

import board.Board;
import cache.EnPassant;
import cache.Move;
import cache.MoveCache;
import pieces.Piece;

public class EnPassantHighlight extends Highlight {

    public EnPassantHighlight(Piece piece, int x, int y) {
        super(piece, x, y);
        setOnMouseClicked(e -> {
            HighlightGroup.clear();
            Move move = new EnPassant(piece,x,y);
            MoveCache.getInstance().addMove(move,false);
        });


    }
}
