package highlighters.graphics;

import board.Board;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Piece;
import pieces.graphics.PieceGroup;
import utils.SizeUtil;

public class Highlight extends Rectangle {

    private static SizeUtil su;

    private static Piece[][] pieces;

    private static final Color color = Color.rgb(24,200,200,0.6);

    private static final Color select = Color.rgb(0,0,140,0.6);

    public Highlight(int x, int y, Piece piece){
        if(pieces == null)pieces = Board.getInstance().getPieces();
        if(su == null)su = SizeUtil.getInstance();
        su.sizeHighlight(this,x,y);
        this.setFill(color);
        setOnMouseEntered(e -> setFill(select));
        setOnMouseExited(e -> setFill(color));
        setOnMouseClicked(e -> {
            Piece p = pieces[x][y];
            if(p != null)
                PieceGroup.getInstance().getChildren().remove(p.getNode());

            pieces[piece.getX()][piece.getY()] = null;

            piece.movePiece(x,y);

            pieces[x][y] = piece;


            HighlightGroup.getInstance().getChildren().removeIf(o -> o instanceof Highlight);
        });
    }
}
