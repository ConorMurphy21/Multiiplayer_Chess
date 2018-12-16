package Animators;

import javafx.application.Platform;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class TakeAnimator extends Animator {

    private Piece p;

    public TakeAnimator(Piece take){
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

    public static void startInNewThread(Piece p){
        new Thread(()->
                new TakeAnimator(p).start()
        ).start();
    }
}
