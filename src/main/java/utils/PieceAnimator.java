package utils;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class PieceAnimator extends AnimationTimer {

                            //how much time the animation will take
    private static final double ENDTIME = 550000000;

    private long startTime;

    private double xDif,yDif,startX,startY,endX,endY;

    private Piece piece,take;

    public PieceAnimator(Piece piece, Piece take, int endX, int endY){
        this.piece = piece;
        this.startX = piece.getX();
        this.startY = piece.getY();
        this.endX = endX;
        this.endY = endY;
        this.xDif = endX - startX;
        this.yDif = endY - startY;
        this.take = take;
        startTime = System.nanoTime();
        Platform.runLater(()->piece.getNode().toFront());

    }

    @Override
    public void handle(long l) {

        double percent = (l - startTime)/ENDTIME;

        if(percent >= 1){
            Platform.runLater(()->piece.movePiece(endX,endY));
            if(take != null)Platform.runLater(()->PieceGroup.getInstance().getChildren().remove(take.getNode()));
            stop();
            return;
        }

        double x = startX + (xDif * percent);
        double y = startY + (yDif * percent);

        Platform.runLater(()->piece.movePiece(x,y));

        if(take != null)Platform.runLater(()->take.getNode().setOpacity(1-percent));

    }
}
