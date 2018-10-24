package highlighters;

import pieces.Piece;

public class PondHL extends HighlighterBase{
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
    int[][] attackAggressorOrStillProtect(Piece p,Piece a) {
        return new int[0][];
    }

    @Override
    int[][] protectKing(Piece p) {
        return new int[0][];
    }

    @Override
    int[][] regularHighlight(Piece p) {
        int[][] r = {{4,5},{4,4},{4,6}};
        return r;
    }
}
