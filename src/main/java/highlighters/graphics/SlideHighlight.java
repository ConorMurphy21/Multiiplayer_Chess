package highlighters.graphics;

import board.Board;
import pieces.Piece;

public class SlideHighlight extends Highlight {

    public SlideHighlight(Piece piece, int x, int y, Piece take) {
        super(piece, x, y);
        setOnMouseClicked(e -> {
            HighlightGroup.clear();
            Board.getInstance().movePieceFromClient(piece,x,y);
            Board.getInstance().takeFromClient(take);
        });


    }
}
