package board;

import pieces.Piece;

public class Board {
    private static Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Piece[][] piece = new Piece[8][8];

    private Board() {
    }

    private void initializeBoard(){

    }
}
