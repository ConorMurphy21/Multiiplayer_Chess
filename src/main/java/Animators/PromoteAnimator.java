package Animators;

import board.Check;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import pieces.Piece;
import pieces.graphics.PieceGroup;

public class PromoteAnimator extends PieceAnimator {

    private Piece newPiece;

    private boolean stageOne;
    //assumes this piece has already been set to opacity 0 and been initialized
    public PromoteAnimator(Piece piece, Piece newPiece, int endX, int endY){
        super(piece,endX,endY);
        this.newPiece = newPiece;
        stageOne = true;
        Platform.runLater(()->piece.getNode().toFront());
    }

    @Override
    void tick(double percent) {
        if(stageOne){
            super.tick(percent);
        }else{
            newPiece.getNode().setOpacity(percent);
            piece.getNode().setOpacity(1-percent);
        }
    }

    @Override
    void onEnd() {
        if(stageOne){
            Platform.runLater(()->piece.moveGraphicNode(endX,endY));
            startTime = System.nanoTime(); //reset time for stage 2
            stageOne = false;
        }else{
            super.onEnd();
            Platform.runLater(()-> PieceGroup.getInstance().getChildren().remove(piece.getNode()));
            stop();
        }
    }

    public static void startInNewThread(Piece p, Piece newP,int endX, int endY){
        new Thread(()->
                new PromoteAnimator(p,newP,endX,endY).start()
        ).start();
    }

}
