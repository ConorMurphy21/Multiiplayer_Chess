package highlighters;

import board.Board;
import highlighters.graphics.Highlight;
import highlighters.graphics.HighlightGroup;
import pieces.King;
import pieces.Piece;
import utils.CheckChecker;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;


public abstract class HighlighterBase implements Highlighter {


    private static HighlightGroup group;

    protected static Piece[][] pieces;


    //this method should find the piece from which the x,y coordinates protects the king from
    //if the given piece is not protecting this should return null
    Piece findAggressor(Piece p){
        Vec king = (p.isWhite()) ? Board.getInstance().getW_king() : Board.getInstance().getB_king();

        double slope = (p.getY()-king.getY())/(double)(p.getX() - king.getX());

        System.out.println(slope);
        return null;
    }

    //find all moves that can occur while still protecting the king
    abstract List<Vec> attackAggressorOrStillProtect(Piece p, Piece a);

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

        Piece aggressor = findAggressor(p);
        List<Vec> aggressorMoves = null, protectKing = null, finalMoves = null;
        if (aggressor != null) {
            aggressorMoves = attackAggressorOrStillProtect(p, aggressor);
        }
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

        p.setMoved();
    }
}
