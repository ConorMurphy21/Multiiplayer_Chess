package board;

import pieces.Piece;
import utils.IterationObj;
import utils.IterationObj.PieceBreak;
import utils.IterationObj.PieceReturn;

import utils.Vec;

public class Check {
    private static Check ourInstance = new Check();

    public static Check getInstance() {
        return ourInstance;
    }

    private boolean check;

    private static Board board;

    private Check() {
        check = false;
        board = Board.getInstance();
    }

    void checkCheck(){

        Piece p = board.getLastMoved();
        //get opposite king of just moved
        Vec king = (p.isWhite()) ? Board.getInstance().getW_king() : Board.getInstance().getB_king();
                                                //make an object that will iterate from the king to the piece
        IterationObj obj = IterationObj.create(king.getX(),king.getY(),p.getX(),p.getY());

        if (!(Math.abs(obj.getSlope()) >= 1 || obj.getSlope() == 0)) {
            check = false;
            return;
        }
        //don't include the king in the iteration
        obj.iterateStartLoc();

        PieceBreak br = (x,y)->{
            if(board.getPieces()[x][y] != null)return true;

            if(x == p.getX() && y == p.getY())return true;

            return true;
        };

        PieceReturn<Boolean> ret = (x,y)->(x == p.getX() && y == p.getY());

        check = obj.iterate(br,ret);
    }

    public boolean isCheck(){
        return check;
    }
}
