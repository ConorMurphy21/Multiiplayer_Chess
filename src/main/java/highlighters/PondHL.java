package highlighters;

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
        List<Vec> list = new ArrayList<Vec>();
        if(p.isWhite()){
            if(pieces[p.getX()][p.getY()-1] == null){
                list.add(new Vec(p.getX(),p.getY()-1));
                if(!p.hasMoved() && pieces[p.getX()][p.getY()-2] == null)
                    list.add(new Vec(p.getX(),p.getY()-2));
            }
            if(p.getX() < 7) {
                if (pieces[p.getX() + 1][p.getY() - 1] != null &&
                        pieces[p.getX() + 1][p.getY() - 1].isWhite() != p.isWhite())
                    list.add(new Vec(p.getX() + 1, p.getY() - 1));
            }
            if(p.getX() > 0) {
                if (pieces[p.getX() - 1][p.getY() - 1] != null &&
                        pieces[p.getX() - 1][p.getY() - 1].isWhite() != p.isWhite())
                    list.add(new Vec(p.getX() - 1, p.getY() - 1));
            }
        }else{
            if(pieces[p.getX()][p.getY()+1] == null){
                list.add(new Vec(p.getX(),p.getY()+1));
                if(!p.hasMoved() && pieces[p.getX()][p.getY()+2] == null)
                    list.add(new Vec(p.getX(),p.getY()+2));
            }

            if(p.getX() < 7) {
                if (pieces[p.getX() + 1][p.getY() + 1] != null &&
                        pieces[p.getX() + 1][p.getY() + 1].isWhite() != p.isWhite())
                    list.add(new Vec(p.getX() + 1, p.getY() + 1));
            }
            if(p.getX() > 0) {
                if (pieces[p.getX() - 1][p.getY() + 1] != null &&
                        pieces[p.getX() - 1][p.getY() + 1].isWhite() != p.isWhite())
                    list.add(new Vec(p.getX() - 1, p.getY() + 1));
            }
        }
        return list;
    }

}
