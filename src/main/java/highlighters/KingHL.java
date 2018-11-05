package highlighters;

import pieces.Piece;
import utils.IterationObj;
import utils.IterationObj.PieceReturn;
import utils.IterationObj.PieceBreak;
import utils.Vec;

import java.util.List;

public class KingHL extends HighlighterBase {

    private static int[][] options = {
            {-1,-1},{0,-1},{1,-1},
            {-1,0}, /*x,y*/{1,0},
            {-1,1}, {0,1}, {1,1}
    };

    private static KingHL ourInstance = new KingHL();

    public static KingHL getInstance() {
        return ourInstance;
    }

    private KingHL() {
    }

    List<Vec> protectKing(Piece p, Piece agr){
        return regularHighlight(p);
    }

    @Override
    List<Vec> findAggressorMoves(Piece p) {
        return null;
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        //narrow it down to all the possible moves that aren't taken
        List<Vec> moves = highlightAllOptions(p,options);

        //remove if the king can not attack
        moves.removeIf(m -> !canAttack(p,m));

        return moves;
    }

    @Override
    public boolean canAttack(Piece p, int x, int y) {

        IterationObj obj;


        for(int[] opt : options){
            //go through each possible attacker and see if it can be attacked
            obj = IterationObj.create(x,y,x+opt[0],y+opt[1]);
            obj.iterateStartLoc();
            boolean straight = Math.abs(obj.getSlope())!= 1;

            PieceBreak br = (xx,yy)->pieces[xx][yy] != null;

            PieceReturn<Boolean> ret = (xx,yy)->{
                if(xx == -1)return false;
                Piece piece = pieces[xx][yy];

                if(piece.isWhite() == p.isWhite())return false;

                if(isMatchHighlighter(piece.highlighter(),straight)) return true;

                return (piece.highlighter() instanceof PondHL && piece.highlighter().canAttack(piece,x,y));
            };

            if(obj.iterate(br,ret))return false;
        }
        //TODO: need to check for knight attacks

        return true;
    }

    @Override
    public boolean isStraight() {
        return false;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public boolean isStoppable() {
        return false;
    }
}
