package board;

import pieces.Piece;

import utils.Vec;

public class Check {
    private static Check ourInstance = new Check();

    public static Check getInstance() {
        return ourInstance;
    }

    private boolean check;

    private static Board board;

    private Check() {
        check = false;
        board = Board.getInstance();
    }

    void checkCheck(){

        Piece p = board.getLastMoved();
                    //check already equals false so don't need to reset it
        if(p == null)return;
        //get opposite king of just moved
        Vec king = (p.isWhite()) ? Board.getInstance().getB_king() : Board.getInstance().getW_king();
                                                //make an object that will iterate from the king to the piece

        check = p.highlighter().canAttack(p,king);

        System.out.println(check);
    }

    public boolean isCheck(){
        return check;
    }
}
