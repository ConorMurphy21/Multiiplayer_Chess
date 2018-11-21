package utils;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class TakeAnimator extends AnimationTimer {

    private static final double ENDTIME = 550000000;

    private long startTime;

    private Piece p;

    public TakeAnimator(Piece take){
        p = take;
        startTime = System.nanoTime();
    }

    @Override
    public void handle(long l) {

        double percent = (l - startTime)/ENDTIME;

        if(percent >= 1){
            Platform.runLater(()-> PieceGroup.getInstance().getChildren().remove(p.getNode()));
            stop();
            return;
        }

        Platform.runLater(()->p.getNode().setOpacity(1-percent));
    }
}
