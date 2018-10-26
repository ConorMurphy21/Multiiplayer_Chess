package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class KnightHL extends HighlighterBase {
    private static KnightHL ourInstance = new KnightHL();

    public static KnightHL getInstance() {
        return ourInstance;
    }

    private KnightHL() {
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
        return null;
    }

}
