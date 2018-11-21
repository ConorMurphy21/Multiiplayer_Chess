package highlighters.graphics;

import board.Board;
import pieces.Piece;

public class CastleHighlight extends Highlight{

    public CastleHighlight(Piece piece1, int x1, int y1, Piece piece2, int x2, int y2){
        super(piece1,x1,y1);
        setOnMouseClicked(e -> {
            Board.getInstance().movePieceFromClient(piece1,x1,y1);
            Board.getInstance().movePieceFromClient(piece2,x2,y2);
            HighlightGroup.clear();
        });
    }

}
