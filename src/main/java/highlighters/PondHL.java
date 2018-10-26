package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class PondHL extends HighlighterBase {
    private static PondHL ourInstance = new PondHL();

    public static PondHL getInstance() {
        return ourInstance;
    }

    private PondHL() {
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
