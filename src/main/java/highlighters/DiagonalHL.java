package highlighters;

import pieces.Piece;

public class DiagonalHL extends HighlighterBase{
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
