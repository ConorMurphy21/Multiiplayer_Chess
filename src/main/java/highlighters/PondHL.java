package highlighters;

import board.Board;
import highlighters.graphics.Highlight;
import highlighters.graphics.PromoteHighlight;
import highlighters.graphics.SlideHighlight;
import pieces.Piece;
import utils.Vec;
import java.util.ArrayList;
import java.util.List;

public class PondHL extends HighlighterBase {
    private static PondHL ourInstance = new PondHL();

    public static PondHL getInstance() {
        return ourInstance;
    }

    private PondHL() {
    }

    //ponds are wierd, and it's special logic for what moves it can make is here
    List<Vec> findAggressorSpecial (Piece p, Piece aggressor, boolean straight, double slope){
        if(Double.isInfinite(slope)){
            return normalMoves(p,dir(p));
        }else if(!straight){
            List<Vec> moves = attackMoves(p,dir(p));
            moves.removeIf(v -> v.getX() != aggressor.getX() || v.getY() != aggressor.getY());
            return moves;
        }
        else return new ArrayList<>();
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        List<Vec> list = new ArrayList<>();
        int dir = dir(p);
        list.addAll(normalMoves(p,dir));
        list.addAll(attackMoves(p,dir));
        return list;
    }

    private int dir(Piece p){
        return (p.isWhite()) ? -1 : 1;
    }

    Highlight highlight(Piece p, int x, int y){
        if(Math.abs(p.getX()-x) == 1 && pieces[x][y] == null){
            return new SlideHighlight(p,x,y,pieces[x][y-dir(p)]);
        }
        if(y == 7 || y == 0){
            return new PromoteHighlight(p,x,y);
        }
        return new Highlight(p,x,y);
    }

    private List<Vec> normalMoves(Piece p, int dir){

        List<Vec> list = new ArrayList<>();
        if(pieces[p.getX()][p.getY()+dir] == null){
            list.add(new Vec(p.getX(),p.getY()+dir));
            if(!p.hasMoved() && pieces[p.getX()][p.getY()+ 2*dir] == null)
                list.add(new Vec(p.getX(),p.getY()+2*dir));
        }
        return list;
    }

    private List<Vec> attackMoves(Piece p, int dir){
        List<Vec> list = new ArrayList<>();
        if(p.getX() < 7) {
            if (pieces[p.getX() + 1][p.getY() + dir] != null &&
                    pieces[p.getX() + 1][p.getY() + dir].isWhite() != p.isWhite())
                list.add(new Vec(p.getX() + 1, p.getY() + dir));
        }
        if(p.getX() > 0) {
            if (pieces[p.getX() - 1][p.getY() + dir] != null &&
                    pieces[p.getX() - 1][p.getY() + dir].isWhite() != p.isWhite())
                list.add(new Vec(p.getX() - 1, p.getY() + dir));
        }

        Board board = Board.getInstance();
        Piece lm = board.getLastMoved();
        Vec lastLoc = board.getLastMovedLocation();
        if(lm == null)return list;

        if((lm.highlighter() instanceof PondHL)){
            if(lm.getY() == p.getY() && Math.abs(lm.getX()-p.getX())==1){
                if(Math.abs(lastLoc.getY()-lm.getY()) == 2){
                    list.add(new Vec(lm.getX(),lm.getY()+dir));
                }
            }
        }

        return list;
    }

    @Override
    public boolean canAttack(Piece p, int x, int y) {
        return (p.getY() + dir(p) == y && Math.abs(x - p.getX()) == 1);
    }

    @Override public boolean canMove(Piece p, int x, int y){
        return (normalMoves(p,dir(p)).contains(new Vec(x,y)));
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
