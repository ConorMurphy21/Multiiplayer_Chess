package Animators;

import javafx.animation.AnimationTimer;

public abstract class Animator extends AnimationTimer {

    private static final double ENDTIME = 550000000;

    long startTime;

    Animator(){
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

    abstract void onEnd();

    abstract void tick(double percent);

}
