package Animators;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class TakeAnimator extends Animator {

    private Piece p;

    public TakeAnimator(Piece take, BooleanProperty turn, boolean set){
        super(turn,set);
        p = take;
    }

    @Override
    void tick(double percent){
        Platform.runLater(()->p.getNode().setOpacity(1-percent));
    }

    @Override
    void onEnd(){
        Platform.runLater(()-> PieceGroup.getInstance().getChildren().remove(p.getNode()));
        stop();
    }
}
