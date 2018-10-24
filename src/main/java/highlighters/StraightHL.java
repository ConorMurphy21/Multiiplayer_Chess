package highlighters;

import pieces.Piece;

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
