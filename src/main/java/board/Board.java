package board;

import pieces.Piece;

public class Board {
    private static Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Piece[][] pieces = new Piece[8][8];

    private Board() {
    }

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
    }

    public Piece[][] getPieces(){
        return pieces;
    }
}
