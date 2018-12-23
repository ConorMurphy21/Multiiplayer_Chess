package board;

import log.MoveLog;
import pieces.Piece;

public class Draw {


    public static MoveLog log = MoveLog.getInstance();

    public static void checkForDraw(Board board){

    }

    private static boolean fiftyMoveDraw(){
        return log.getNumMovesSinceIrr() >= 50;
    }


    private static void threeFoldRep(){

    }

    private static void insufficientMaterial(){

    }

    private static boolean stalemate(Board board){
        if(Check.getInstance().isCheck())return false;
        for(Piece[] row : board.getPieces()){
            for(Piece p : row){
                if(p == null)continue;
                if(p.isWhite() == log.getLastMove().getPiece().isWhite())continue;
                if(!p.highlighter().highlights(p).isEmpty())return false;
            }
        }
        return true;
    }
}
