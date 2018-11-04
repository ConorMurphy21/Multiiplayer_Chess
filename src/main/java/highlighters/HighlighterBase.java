package highlighters;

import board.Board;
import highlighters.graphics.Highlight;
import highlighters.graphics.HighlightGroup;
import pieces.Piece;
import utils.IterationObj;
import utils.Vec;
import utils.IterationObj.*;

import java.util.ArrayList;
import java.util.List;


public abstract class HighlighterBase implements Highlighter {


    private static HighlightGroup group;

    protected static Piece[][] pieces;

    /*** finds the moves that any piece can make if it is protecting the king***/

    //this returns null if there is no aggressor to protect from, and empty if there is but there are no moves
    List<Vec> findAggressorMoves(Piece p){
        Vec king = (p.isWhite()) ? Board.getInstance().getW_king() : Board.getInstance().getB_king();
        IterationObj obj = IterationObj.create(king.getX(),king.getY(),p.getX(),p.getY());
        if (!(Math.abs(obj.getSlope()) >= 1 || obj.getSlope() == 0)) return null;

        ArrayList<Vec> moves = new ArrayList<>();

        PieceBreak br = (x,y)-> {

            moves.add(new Vec(x,y));

            //break if it's the piece we were going to
            if(x == p.getX() && y == p.getY())return true;

            //can return here if there is something inbetween the king and the piece
            return (pieces[x][y] != null);
        };
                                        //returns whether the next step should happen or not
        PieceReturn<Boolean> ret = (x,y)-> (x == p.getX() && y == p.getY());

        //iterate once so not on the king
        obj.iterateStartLoc();

        if(obj.iterate(br,ret)){

            boolean straight = Math.abs(obj.getSlope())!= 1;

            PieceBreak br2 = (x,y)-> {
                moves.add(new Vec(x,y));
                return(pieces[x][y] != null);
            };

            PieceReturn<List<Vec>> ret2 = (x,y)-> {
                //if it makes it through the loop to the edge of the board without reaching anything
                if(x == -1)return null;

                Piece piece = pieces[x][y];
                if(piece.isWhite() == p.isWhite()){
                    return null;
                } else if(isMatchHighlighter(piece.highlighter(),straight)){

                        //if the piece in question can move along the same line as
                        if(isMatchHighlighter(p.highlighter(),straight)) {
                            moves.add(new Vec(x, y));
                            return moves;

                        }else if(p.highlighter() instanceof PondHL){
                            return PondHL.getInstance().findAggressorSpecial(p,piece,straight,obj.getSlope());
                        }

                        //returns empty list because the piece at this point cannot move
                        //whereas returning null would imply it does not have to protect
                        moves.clear();
                        return moves;
                }else {
                    return null;
                }

            };

            obj.ressetStartLoc(p.getX(),p.getY());
            obj.iterateStartLoc();
            return obj.iterate(br2,ret2);
        }
        return null;
    }

    //returns if the highlighter is same type as boolean
    private boolean isMatchHighlighter(Highlighter highlighter,boolean straight){
        if(highlighter instanceof QueenHL)return true;
        if(straight) return highlighter instanceof StraightHL;
        return highlighter instanceof DiagonalHL;
    }


    /***Abstract methods that must be highlighted***/
    //this method is called if the last move was a check
    //this will return all the moves that can protect the king
    //this implies, block the attackers move, or take the attacker
    abstract List<Vec> protectKing(Piece p);

    //this method will return all the possible moves, assuming no check, or sacrifice
    abstract List<Vec> regularHighlight(Piece p);



    /****METHODS FOR SUBCLASSES TO USE***/

    List<Vec> highlightAllOptions(Piece p, int[][] options) {
        List<Vec> points = new ArrayList<>();
        for (int[] opt : options) {
            int x = p.getX() + opt[0], y = p.getY() + opt[1];
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


    /***DOES ALL OF THE GENERIC WORK, BASED OFF OF OVERRIDEN METHODS***/

    public void highlight(Piece p) {
        if (pieces == null) pieces = Board.getInstance().getPieces();
        if (group == null) group = HighlightGroup.getInstance();
        //there's a sizing node in here so we must be careful to just remove the highlights and not it
        group.getChildren().removeIf(o -> o instanceof Highlight);

        List<Vec> aggressorMoves, protectKing = null, finalMoves;

        aggressorMoves = findAggressorMoves(p);

        if (Board.getInstance().isCheck()) {
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
