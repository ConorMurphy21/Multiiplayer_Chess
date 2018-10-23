package highlighters;

import pieces.Piece;

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
    int[][] attackAggressorOrStillProtect(Piece p) {
        return new int[0][];
    }

    @Override
    int[][] protectKing(Piece p) {
        return new int[0][];
    }

    @Override
    int[][] regularHighlight(Piece p) {
        return new int[0][];
    }
}
