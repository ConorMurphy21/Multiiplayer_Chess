package board;

import highlighters.HighlighterBase;
import pieces.Piece;

import utils.IterationObj;
import utils.IterationObj.PieceBreak;
import utils.IterationObj.PieceReturn;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;

public class Check {
    private static Check ourInstance = new Check();

    public static Check getInstance() {
        return ourInstance;
    }

    private boolean check;

    private static Board board;

    //not always the last moved piece
    private List<Piece> checkers = new ArrayList<>();

    private Check() {
        check = false;
        board = Board.getInstance();
    }

    public void checkCheck(){

        //clear checkers from last check
        checkers.clear();

        Piece p = board.getLastMoved();
                    //check already equals false so don't need to reset it
        if(p == null)return;

        //get opposite king of just moved
        Vec king = (p.isWhite()) ? Board.getInstance().getB_king() : Board.getInstance().getW_king();
                                                //make an object that will iterate from the king to the piece


        check = p.highlighter().canAttack(p,king);

        //add piece to the checkers
        if(check)checkers.add(p);

        Vec place = board.getLastMovedLocation();
        if(place == null)return;


        //for iterating from the king to the previous location that the piece moved held
        //to see if it was blocking an attacking piece from hitting the king
        IterationObj obj = IterationObj.create(king.getX(),king.getY(),place.getX(),place.getY());
        obj.iterateStartLoc();
        if(!obj.isNormalSlope())return;


        PieceBreak br = (x,y)->board.getPieces()[x][y] != null;


        PieceReturn<Piece> ret = (x,y)->{
            //if there is nothing along the line it is not a check
            if(x == -1)return null;

            Piece piece = board.getPieces()[x][y];

                //if the piece attacking the king is the same colour as the piece found, it is not a check
            if(piece.isWhite() != p.isWhite())return null;

                    //if the piece goes in the same direction as the path to the king, it is check
            return HighlighterBase.isMatchHighlighter(piece.highlighter(),obj.isStraight()) ? piece : null;

        };

        Piece checker = obj.iterate(br,ret);

        if(checker != null){
            checkers.add(checker);
            check = true;
        }

        if(check){

            System.out.println("it is a check rn");
            boolean checkMate = isCheckMate(p);

        }

    }

    private boolean isCheckMate(Piece lastMoved){
        for(Piece[] row : board.getPieces()){
            for(Piece p : row){
                if(p == null)continue;
                if(p.isWhite() == lastMoved.isWhite())continue;
                if(!p.highlighter().highlights(p).isEmpty())return false;
            }
        }
        return true;
    }

    public List<Piece> getCheckers(){
        return checkers;
    }

    public boolean isCheck(){
        return check;
    }
}
