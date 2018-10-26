package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class KingHL extends HighlighterBase {
    private static KingHL ourInstance = new KingHL();

    public static KingHL getInstance() {
        return ourInstance;
    }

    private KingHL() {
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
