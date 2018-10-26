package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class DiagonalHL extends HighlighterBase {
    private static DiagonalHL ourInstance = new DiagonalHL();

    public static DiagonalHL getInstance() {
        return ourInstance;
    }

    private DiagonalHL() {
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
