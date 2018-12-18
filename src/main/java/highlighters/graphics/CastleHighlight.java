package highlighters.graphics;

import board.Board;
import cache.CastleMove;
import cache.Move;
import cache.MoveCache;
import pieces.Piece;

public class CastleHighlight extends Highlight{

    public CastleHighlight(Piece piece1, int x1, int y1, Piece piece2, int x2, int y2){
        super(piece1,x1,y1);
        setOnMouseClicked(e -> {
            Move move = new CastleMove(piece1,x1,y1,piece2,x2,y2);
            MoveCache.getInstance().addMove(move,false);
            HighlightGroup.clear();
        });
    }

}
