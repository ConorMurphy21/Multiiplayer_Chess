package pieces;

import board.Board;
import highlighters.Highlighter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pieces.graphics.PieceGroup;
import pieces.graphics.PieceNode;
import utils.SizeUtil;


public abstract class PieceBase implements Piece {


    private DoubleProperty xProperty,yProperty;

    private boolean isWhite, hasMoved;

    private static SizeUtil sizeUtil;
    private static PieceGroup group;

    Highlighter highlighter;

    PieceNode node;

    PieceBase(boolean isWhite, int x, int y){
        xProperty = new SimpleDoubleProperty(x);
        yProperty = new SimpleDoubleProperty(y);
        this.isWhite = isWhite;
        hasMoved = false;
    }

    private void bindNode(){
        sizeUtil.sizePieceNode(node,xProperty,yProperty);
    }
    private void attachNodeToGroup(){
        group.getChildren().add(node);
    }
    private void attachToBoard(){
        Board.getInstance().addToBoard(this,getX(),getY());
    }
    public void ini(){
        if(sizeUtil == null)sizeUtil = SizeUtil.getInstance();
        if(group == null)group = PieceGroup.getInstance();
        bindNode();
        attachNodeToGroup();
        attachToBoard();
        if(sizeUtil.isFlipped() == isWhite){
            node.setMouseTransparent(true);
        }
        node.setOnMouseClicked(e -> highlighter.highlight(this));
    }


    public void movePiece(double x, double y){
        xProperty.set(x);
        yProperty.set(y);
    }

    @Override
    public int getX() {
        return (int)xProperty.get();
    }

    @Override
    public int getY() {
        return (int)yProperty.get();
    }

    public PieceNode getNode(){
        return node;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(){
        hasMoved = true;
    }

    @Override
    public Highlighter highlighter() {
        return highlighter;
    }

}
