package highlighters;

import pieces.Piece;

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
    int[][] attackAggressorOrStillProtect(Piece p,Piece a) {
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
