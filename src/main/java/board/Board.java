package board;

import cache.MoveCache;
import pieces.Piece;
import utils.Vec;

public class Board {
    private static final Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private final Vec b_king = new Vec(4,0);
    private final Vec w_king = new Vec(4,7);

    private final Piece[][] pieces = new Piece[8][8];

    private Board(){
        BoardManager bm = new BoardManager();
        MoveCache.getInstance().addListener( l -> {
            if(l.getAddedSize() == 1)
                bm.updateBoard(pieces,l.getAddedSubList().get(0));
        });
    }

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
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
