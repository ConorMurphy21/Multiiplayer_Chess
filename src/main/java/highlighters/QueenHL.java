package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;

public class QueenHL extends HighlighterBase {
    private static QueenHL ourInstance = new QueenHL();

    public static QueenHL getInstance() {
        return ourInstance;
    }

    private StraightHL straightHL = StraightHL.getInstance();
    private DiagonalHL diagonalHL = DiagonalHL.getInstance();

    private QueenHL() {
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        List<Vec> points = new ArrayList<>();
        points.addAll(straightHL.regularHighlight(p));
        points.addAll(diagonalHL.regularHighlight(p));
        return points;
    }

    @Override
    public boolean isStraight() {
        return true;
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

    @Override
    public boolean isStoppable() {
        return true;
    }
}
