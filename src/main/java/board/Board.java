package board;

import javafx.beans.property.BooleanProperty;
import main.Main;
import networking.Client;
import pieces.King;
import pieces.Piece;
import pieces.graphics.PieceGroup;
import utils.PieceAnimator;
import utils.TakeAnimator;
import utils.Vec;

public class Board {
    private static Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Vec b_king = new Vec(4,0), w_king = new Vec(4,7);
    private Piece lastMoved;
    private Vec lastMovedLocation;
    private BooleanProperty turn = Main.getThisTurn();

    private Piece[][] pieces = new Piece[8][8];

    private Board() {
    }

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
    }


    public void takeFromClient(Piece take){
        Client.getInstance().sendTake(take.getX(),take.getY());
        take(take.getX(),take.getY());
    }
    public void takeFromServer(int x, int y){
        take(x,y);
    }

    private void take(int x, int y){

        Piece take = pieces[x][y];
        new Thread(()->
                new TakeAnimator(take).start()
        ).start();
        Board.getInstance().getPieces()[take.getX()][take.getY()] = null;
    }

    public void movePieceFromServer(int x, int y, int newX, int newY){

        //from the server it means it is now this players turn
        turn.setValue(true);

        //set p outside of new thread
        Piece p = pieces[x][y];

        //run in new thread so that moves at the same time (castling) can occur
        new Thread(()->
                new PieceAnimator(p,newX,newY).start()
        ).start();

        //set take outside of new thread
        Piece take = pieces[newX][newY];
        if(take != null){
            new Thread(()->
                    new TakeAnimator(take).start()
            ).start();
        }

        movePiece(p,newX,newY);

        Check.getInstance().checkCheck();
    }

    public void movePieceFromClient(Piece piece, int x, int y){

        turn.setValue(false);

        new Thread(()->
            new PieceAnimator(piece,x,y).start()
        ).start();

        Piece take = pieces[x][y];

        if(take != null){
            new Thread(()->
                new TakeAnimator(take).start()
            ).start();
        }

        Client.getInstance().sendMove(piece.getX(),piece.getY(),x,y);

        movePiece(piece,x,y);
    }

    private void movePiece(Piece piece, int x, int y){

        lastMovedLocation = new Vec(piece.getX(),piece.getY());

        pieces[piece.getX()][piece.getY()] = null;
        //piece.movePiece(x,y);
        pieces[x][y] = piece;
        if(piece instanceof King){
            if(piece.isWhite())w_king = new Vec(x,y);
            else b_king = new Vec(x,y);
        }

        piece.setMoved();
        lastMoved = piece;
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

    public Piece getLastMoved() {
        return lastMoved;
    }

    public Vec getLastMovedLocation(){return lastMovedLocation; }
}
