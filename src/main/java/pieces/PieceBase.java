package pieces;

import board.Board;
import highlighters.Highlighter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pieces.graphics.PieceGroup;
import pieces.graphics.PieceNode;
import utils.SizeUtil;


public abstract class PieceBase implements Piece {


    private IntegerProperty xProperty,yProperty;

    private boolean isWhite, hasMoved;

    private static SizeUtil sizeUtil;
    private static PieceGroup group;

    Highlighter highlighter;

    PieceNode node;

    PieceBase(boolean isWhite, int x, int y){
        xProperty = new SimpleIntegerProperty(x);
        yProperty = new SimpleIntegerProperty(y);
        this.isWhite = isWhite;
        hasMoved = false;
    }

    private void bindNode(){
        sizeUtil.bindPieceNode(node,xProperty,yProperty);
    }
    private void attachNodeToGroup(){
        group.getChildren().add(node);
    }
    private void attachToBoard(){
        Board.getInstance().addToBoard(this,xProperty.get(),yProperty.get());
    }
    public void ini(){
        if(sizeUtil == null)sizeUtil = SizeUtil.getInstance();
        if(group == null)group = PieceGroup.getInstance();
        bindNode();
        attachNodeToGroup();
        attachToBoard();
    }


    @Override
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    @Override
    public Highlighter highlighter() {
        return highlighter;
    }
}
