package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;

public class StraightHL extends HighlighterBase{
    private static StraightHL ourInstance = new StraightHL();

    public static StraightHL getInstance() {
        return ourInstance;
    }

    private StraightHL() {
    }

    @Override
    Piece findAggressor(Piece p) {
        return null;
    }

    @Override
    List<Vec> attackAggressorOrStillProtect(Piece p, Piece a) {
        return null;
    }

    @Override
    List<Vec> protectKing(Piece p) {
        return null;
    }

    @Override
    List<Vec> regularHighlight(Piece p) {

        ArrayList<Vec> points = new ArrayList<>();

        for(int i = p.getX()+1; i < 8; i++){
            if(addAndBreakIfEnd(points,p,i,p.getY()))break;
        }
        for(int i = p.getX()-1; i >= 0; i--){
            if(addAndBreakIfEnd(points,p,i,p.getY()))break;
        }
        for(int i = p.getY()+1; i < 8; i++){
            if(addAndBreakIfEnd(points,p,p.getX(),i))break;
        }
        for(int i = p.getY()-1; i >= 0; i--){
            if(addAndBreakIfEnd(points,p,p.getX(),i))break;
        }

        return points;
    }

    private boolean addAndBreakIfEnd(List<Vec> points,Piece p, int x, int y){
        if(pieces[x][y] == null){
            points.add(new Vec(x,y));
            return false;
        }else if(pieces[x][y].isWhite() != p.isWhite()){
            points.add(new Vec(x,y));
        }
        return true;
    }
}
