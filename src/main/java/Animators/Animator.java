package Animators;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;

public abstract class Animator extends AnimationTimer {

    private static final double ENDTIME = 550000000;

    long startTime;

    private BooleanProperty turn;

    private boolean set;

    Animator(BooleanProperty turn, boolean set){
        this.turn = turn;
        this.set = set;
        startTime = System.nanoTime();
    }

    @Override
    public void handle(long l){
        double percent = percentPast(l);
        if(percent >= 1)
            onEnd();
        else
            tick(percent);
    }

    double percentPast(long l){
         return (l - startTime)/ENDTIME;
    }

    void onEnd(){
        turn.setValue(set);
    }

    abstract void tick(double percent);

}
