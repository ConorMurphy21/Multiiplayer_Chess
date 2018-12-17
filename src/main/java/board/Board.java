package board;

import Animators.PromoteAnimator;
import javafx.beans.property.BooleanProperty;
import main.Main;
import networking.Client;
import pieces.King;
import pieces.Piece;
import Animators.PieceAnimator;
import Animators.TakeAnimator;
import pieces.PieceFactory;
import utils.Vec;

public class Board {
    private static Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Vec b_king = new Vec(4,0), w_king = new Vec(4,7);

    private Client client = Client.getInstance();
    private Piece[][] pieces = new Piece[8][8];

    private Board(){}

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
    }

    public void takeFromClient(Piece take){
        client.sendTake(take.getX(),take.getY());
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
        //set p outside of new thread
        Piece p = pieces[x][y];

        //run in new thread so that moves at the same time (castling) can occur
        PieceAnimator.startInNewThread(p,x,y);

        //set take outside of new thread
        Piece take = pieces[newX][newY];
        if(take != null)
            TakeAnimator.startInNewThread(take);

        movePiece(p,newX,newY);

    }

    public void movePieceFromClient(Piece piece, int x, int y){

        PieceAnimator.startInNewThread(piece,x,y);

        Piece take = pieces[x][y];

        if(take != null) TakeAnimator.startInNewThread(take);

        client.sendMove(piece.getX(),piece.getY(),x,y);

        movePiece(piece,x,y);
    }

    private void movePiece(Piece piece, int x, int y){

        pieces[piece.getX()][piece.getY()] = null;

        pieces[x][y] = piece;
        if(piece instanceof King){
            if(piece.isWhite())w_king = new Vec(x,y);
            else b_king = new Vec(x,y);
        }

        piece.setMoved();
    }

    public void promoteFromClient(Piece p, Piece newPiece, int x, int y){
        promote(p,newPiece,x,y);
        client.sendPromote(p.getX(),p.getY(),x,y,newPiece.getChar());
    }

    public void promoteFromServer(int x1, int y1, int x2, int y2, char c, boolean isWhite){
        Piece p = pieces[x1][y1];
        Piece newPiece = PieceFactory.create(x2,y2,c,!isWhite);
        promote(p,newPiece,x2,y2);
    }

    //note does not just promote, also moves as the turn does not change until finished promotion
    private void promote(Piece p, Piece newPiece, int x, int y){


        pieces[p.getX()][p.getY()] = null;

        new Thread(()->
            new PromoteAnimator(p,newPiece,x,y).start()
        ).start();

        Piece take;
        if((take = pieces[x][y]) != null) TakeAnimator.startInNewThread(take);


        newPiece.ini();
        newPiece.getNode().setOpacity(0);

        p.setMoved();

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
