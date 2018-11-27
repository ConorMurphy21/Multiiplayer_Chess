package Animators;

import board.Check;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class PieceAnimator extends Animator {


    double xDif,yDif,startX,startY,endX,endY;

    Piece piece;

    public PieceAnimator(Piece piece, int endX, int endY){
        super();
        this.piece = piece;
        this.startX = piece.getX();
        this.startY = piece.getY();
        this.endX = endX;
        this.endY = endY;
        this.xDif = endX - startX;
        this.yDif = endY - startY;
        Platform.runLater(()->piece.getNode().toFront());
    }


    @Override
    void tick(double percent){
        double x = startX + (xDif * percent);
        double y = startY + (yDif * percent);
        Platform.runLater(()->piece.movePiece(x,y));

    }

    @Override
    void onEnd(){
        Platform.runLater(()->piece.movePiece(endX,endY));
        Platform.runLater(()-> Check.getInstance().checkCheck());
        stop();
    }
}
