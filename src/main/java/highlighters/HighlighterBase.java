package highlighters;

import board.Board;
import highlighters.graphics.Highlight;
import highlighters.graphics.HighlightGroup;
import pieces.Piece;
import utils.CheckChecker;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;


public abstract class HighlighterBase implements Highlighter {


    private static HighlightGroup group;

    protected static Piece[][] pieces;


    @FunctionalInterface
    private interface Iterator{
        int iterate(int i);
    }

    //this method should find the piece from which the x,y coordinates protects the king from
    //if the given piece is not protecting this should return null
    List<Vec> findAggressorMoves(Piece p) {
        Vec king = (p.isWhite()) ? Board.getInstance().getW_king() : Board.getInstance().getB_king();

        double slope = (p.getY() - king.getY()) / (double) (p.getX() - king.getX());
        if (!(Math.abs(slope) >= 1 || slope == 0)) return null;

        Iterator xIt = pickxIt(slope,p.getX(), king.getX());
        Iterator yIt = pickyIt(slope,p.getY(), king.getY());

        boolean straight = Math.abs(slope)!= 1;

        List<Vec> moves = new ArrayList<>();
        boolean isPastPiece = false;
        for(int i = king.getX(), j = king.getY(); i < 8 && i >= 0 && j < 8 && j >= 0; i = xIt.iterate(i), j = yIt.iterate(j)){
            //easier to start on king and skip it then try to ini for every case
            if(i == king.getX() && j == king.getY())continue;


            //there are two things we are looking for:
            //1: if there is a piece the piece and the king
            //if so then we can stop because the piece is not protecting the king
            //2: if the first condition is not true:
            // we need to check for whether or not there is a piece on further on the line that can attack it
            if(i == p.getX() && j == p.getY()) {
                isPastPiece = true;
                continue;
            }

            //check for 1
            if(!isPastPiece){

                if(pieces[i][j] != null)return null;

            }else{
                //add move to possible highlights
                moves.add(new Vec(i,j));

                //if the piece at the current position does not equal null
                Piece pp;
                if((pp = pieces[i][j]) != null){

                    if(pp.isWhite() == p.isWhite()){
                        //if of the same color return null
                        return null;

                        //if the piece can attack along the same line the king and og piece form
                    } else if(isMatchHighlighter(pp.highlighter(),straight)){

                        //if the piece in question can move along the same line as
                        if(isMatchHighlighter(p.highlighter(),straight)) {
                            moves.add(new Vec(i, j));
                            return moves;

                        }else if(p.highlighter() instanceof PondHL && Double.isInfinite(slope)){
                            return regularHighlight(p);
                        }
                        //returns empty list because the piece at this point cannot move
                        //whereas returning null would imply it does not have to protect
                        moves.clear();
                        return moves;
                    }
                }

            }
        }
        return null;
    }

    private Iterator pickxIt(double slope, int x, int kx){
        if(Double.isInfinite(slope)){
           return i->i;
        }else{ //slope = 1 or -1 or 0
            return (x > kx) ? i->i+1: i-> i-1;
        }
    }
    private Iterator pickyIt(double slope, int y, int ky){
        if(slope == 0){
            return i->i;
        }else{ //slope = infinity 1 or -1
            return (y > ky) ? i->i+1: i-> i-1;
        }
    }



    //returns if the highlighter is same type as boolean
    boolean isMatchHighlighter(Highlighter highlighter,boolean straight){
        if(highlighter instanceof QueenHL)return true;
        if(straight) return highlighter instanceof StraightHL;
        return highlighter instanceof DiagonalHL;
    }

    //this method is called if the last move was a check
    //this will return all the moves that can protect the king
    //this implies, block the attackers move, or take the attacker
    abstract List<Vec> protectKing(Piece p);

    //this method will return all the possible moves, assuming no check, or sacrifice
    abstract List<Vec> regularHighlight(Piece p);


    List<Vec> highlightAllOptions(Piece p, int[][] options) {
        List<Vec> points = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            int x = p.getX() + options[i][0], y = p.getY() + options[i][1];
            if (x < 0 || y < 0 || x >= 8 || y >= 8) continue;
            if (pieces[x][y] != null && pieces[x][y].isWhite() == p.isWhite()) continue;
            points.add(new Vec(x, y));
        }
        return points;
    }

    //this method is for methods that require a breakpoint at the enemy piece, or before a piece of the same team
    boolean addAndBreakIfEnd(List<Vec> points, Piece p, int x, int y) {

        if (pieces[x][y] == null) {
            points.add(new Vec(x, y));
            return false;
        } else if (pieces[x][y].isWhite() != p.isWhite()) {
            points.add(new Vec(x, y));
        }
        return true;
    }


    public void highlight(Piece p) {
        if (pieces == null) pieces = Board.getInstance().getPieces();
        if (group == null) group = HighlightGroup.getInstance();
        //there's a sizing node in here so we must be careful to just remove the highlights and not it
        group.getChildren().removeIf(o -> o instanceof Highlight);

        List<Vec> aggressorMoves, protectKing = null, finalMoves;

        aggressorMoves = findAggressorMoves(p);

        if (CheckChecker.isCheck()) {
            protectKing = protectKing(p);
        }

        if (aggressorMoves != null && protectKing != null) {
            return;
        } else if (aggressorMoves != null) {
            finalMoves = aggressorMoves;
        } else if (protectKing != null) {
            finalMoves = protectKing;
        } else {
            finalMoves = regularHighlight(p);
        }

        //adds the list of final moves to the highlightGroup
        for (Vec finalMove : finalMoves) {
            Highlight hl = new Highlight(finalMove.getX(), finalMove.getY(), p);
            group.getChildren().add(hl);
        }

    }
}
