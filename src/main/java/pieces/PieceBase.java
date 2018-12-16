package pieces;

import Animators.PieceAnimator;
import board.Board;
import highlighters.Highlighter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import pieces.graphics.PieceGroup;
import pieces.graphics.PieceNode;
import utils.SizeUtil;


public abstract class PieceBase implements Piece {


    private DoubleProperty xxProperty, yyProperty;

    private int x,y;

    private boolean isWhite, hasMoved;

    private static SizeUtil sizeUtil;
    private static PieceGroup group;

    Highlighter highlighter;

    PieceNode node;

    PieceBase(boolean isWhite, int x, int y){
        xxProperty = new SimpleDoubleProperty(x);
        yyProperty = new SimpleDoubleProperty(y);
        this.isWhite = isWhite;
        hasMoved = false;
    }

    private void bindNode(){
        sizeUtil.sizePieceNode(node,xxProperty, yyProperty);
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


    public void movePiece(int x, int y){
        new Thread(() ->
            new PieceAnimator(this,x,y).start()
        ).start();

    }

    public void moveGraphicNode(double x, double y){
        xxProperty.setValue(x);
        yyProperty.setValue(y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
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
