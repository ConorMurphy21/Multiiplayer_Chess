package utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class IterationObj {

    @FunctionalInterface
    public interface Iterator{
        int iterate(int i);
    }

    @FunctionalInterface
    public interface PieceBreak{
        boolean breakLoop(int i, int j);
    }

    @FunctionalInterface
    public interface PieceReturn<T>{
        T ret(int i, int j);
    }

    private int x, y;
    private Iterator xIt,yIt;
    private double slope;
    private IterationObj(int x, int y, Iterator xIt, Iterator yIt, double slope){
        this.x = x;
        this.y = y;
        this.xIt = xIt;
        this.yIt = yIt;
        this.slope = slope;
    }

    /***Methods for abstract piece traversal***/
    //note will not stop at toX, will just go in that direction
    public static IterationObj create(int fromX, int fromY, int toX, int toY){
        double slope = slope(fromX,fromY,toX,toY);
        Iterator xIt = pickxIt(slope,toX,fromX);
        Iterator yIt = pickyIt(slope,toY,fromY);
        return new IterationObj(fromX,fromY,xIt,yIt,slope);
    }

    private static double slope(int x1, int y1, int x2, int y2){
        return (y2-y1)/(double)(x2-x1);
    }

    @NotNull
    @Contract(pure = true)
    private static Iterator pickxIt(double slope, int x, int kx){
        if(Double.isInfinite(slope)){
            return i->i;
        }else{ //slope = 1 or -1 or 0
            return (x > kx) ? i->i+1: i-> i-1;
        }
    }

    @NotNull
    @Contract(pure = true)
    private static Iterator pickyIt(double slope, int y, int ky){
        if(slope == 0){
            return i->i;
        }else{ //slope = infinity 1 or -1
            return (y > ky) ? i->i+1: i-> i-1;
        }
    }

    //start the start location one farther from original start point
    public void iterateStartLoc(){
        x = xIt.iterate(x);
        y = yIt.iterate(y);
    }

    //for when the iterators are the same but the starting location is not
    public void ressetStartLoc(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getSlope() {
        return slope;
    }

    public <T> T iterate(PieceBreak br , PieceReturn<T> ret){
        for(int i = x, j = y ; i < 8 && i >= 0 && j < 8 && j >= 0; i = xIt.iterate(i), j = yIt.iterate(j)){
            if(br.breakLoop(i,j))return ret.ret(i,j);
        }
        //alllow custom return if the for loop makes it all the way through
        return ret.ret(-1,-1);
    }



}
