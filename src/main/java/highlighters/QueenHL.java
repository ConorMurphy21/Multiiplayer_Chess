package highlighters;

import pieces.Piece;

public class QueenHL extends HighlighterBase{
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
