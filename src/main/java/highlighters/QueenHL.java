package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class QueenHL extends HighlighterBase {
    private static QueenHL ourInstance = new QueenHL();

    public static QueenHL getInstance() {
        return ourInstance;
    }

    private QueenHL() {
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
