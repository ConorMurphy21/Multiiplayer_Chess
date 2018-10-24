package highlighters;

import board.Board;
import highlighters.graphics.Highlight;
import highlighters.graphics.HighlightGroup;
import pieces.Piece;
import utils.CheckChecker;


public abstract class HighlighterBase implements Highlighter{


    private HighlightGroup group;

    protected Piece[][] pieces;

                    //this method should find the piece from which the x,y coordinates protects the king from
                    //if the given piece is not protecting this should return null
    abstract Piece findAggressor(Piece p);

                     //find all moves that can occur while still protecting the king
    abstract int[][] attackAggressorOrStillProtect(Piece p,Piece a);

                    //this method is called if the last move was a check
                    //this will return all the moves that can protect the king
                    //this implies, block the attackers move, or take the attacker
    abstract int[][] protectKing(Piece p);

                    //this method will return all the possible moves, assuming no check, or sacrifice
    abstract int[][] regularHighlight(Piece p);


    public void highlight(Piece p){
        if(pieces == null)pieces = Board.getInstance().getPieces();
        if(group == null)group = HighlightGroup.getInstance();
        //there's a sizing node in here so we must be careful to just remove the highlights and not it
        group.getChildren().removeIf(o -> o instanceof Highlight);

        Piece aggressor = findAggressor(p);
        int[][] aggressorMoves = null, protectKing = null, finalMoves = null;
        if(aggressor != null){
            aggressorMoves = attackAggressorOrStillProtect(p,aggressor);
        }
        if(CheckChecker.isCheck()){
            protectKing = protectKing(p);
        }

        if(aggressorMoves != null && protectKing != null){
            //TODO: take only the intersection of the two sets

        }else if(aggressorMoves != null){
            finalMoves = aggressorMoves;
        }else if(protectKing != null){
            finalMoves = protectKing;
        }else{
            finalMoves = regularHighlight(p);
        }

        //adds the list of final moves to the highlightGroup
        for (int[] finalMove : finalMoves) {
            Highlight hl = new Highlight(finalMove[0], finalMove[1]);
            group.getChildren().add(hl);

        }

    }


}
