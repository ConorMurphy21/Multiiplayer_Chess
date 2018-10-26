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

        for (int i = p.getX(); i < 8 - p.getX(); i++) {
            points.add(new Vec(i, p.getY()));
        }

        return points;
    }
}
