package board;

import pieces.King;
import pieces.Piece;
import pieces.graphics.PieceGroup;
import utils.Vec;

public class Board {
    private static Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Vec b_king = new Vec(4,0), w_king = new Vec(4,7);

    private Piece[][] pieces = new Piece[8][8];

    private Board() {
    }

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
    }

    public void movePiece(Piece piece, int x, int y){
        Piece p = pieces[x][y];
        if(p != null)
            PieceGroup.getInstance().getChildren().remove(p.getNode());


        pieces[piece.getX()][piece.getY()] = null;
        piece.movePiece(x,y);
        pieces[x][y] = piece;
        if(piece instanceof King){
            if(piece.isWhite())w_king = new Vec(x,y);
            else b_king = new Vec(x,y);
        }
        piece.setMoved();
    }

    public Piece[][] getPieces(){
        return pieces;
    }

    public Vec getB_king() {
        return b_king;
    }

    public Vec getW_king() {
        return w_king;
    }
}
