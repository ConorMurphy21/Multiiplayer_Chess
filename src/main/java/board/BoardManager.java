package board;

import Animators.PieceAnimator;
import Animators.PromoteAnimator;
import Animators.TakeAnimator;
import cache.*;
import pieces.Piece;
import pieces.PieceFactory;

class BoardManager {

    void updateBoard(Piece[][] board,Move m){
        switch (m.getChar()){
            case 'n':
                normal(board,(NormalMove) m);
                break;
            case 'c':
                castle(board,(CastleMove) m);
                break;
            case 'e':
                enPassant(board, (EnPassant) m);
                break;
            case 'p':
                promotion(board, (Promotion) m);
                break;
        }
    }

    private void normal(Piece[][] board,NormalMove move){

        move.getPiece().movePiece(move.getToX(),move.getToY());

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        Piece take = board[move.getToX()][move.getToY()];
        if(take != null)
            TakeAnimator.startInNewThread(take);

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();

    }

    private void castle(Piece[][] board, CastleMove move){

        move.getPiece().movePiece(move.getToX(),move.getToY());

        move.getRook().movePiece(move.getrToX(),move.getrToY());

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        PieceAnimator.startInNewThread(move.getRook(),move.getrToX(),move.getrToY());

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();

        board[move.getrFromX()][move.getrFromY()] = null;

        board[move.getrToX()][move.getrToY()] = move.getRook();

    }

    private void enPassant(Piece[][] board, EnPassant move){

        move.getPiece().movePiece(move.getToX(),move.getToY());

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        //the x coordinate of the taken piece is always the same
        //the y coordinate is either 4 or 5 depending on which side is attacking
        int y = (move.getPiece().isWhite())? 4 : 5;
        Piece take = board[move.getToX()][y];

        TakeAnimator.startInNewThread(take);

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();
    }

    private void promotion(Piece[][] board, Promotion move){

        Piece newPiece = PieceFactory.create(move.getToX(),move.getToY());

        if(newPiece == null)return;

        board[move.getFromX()][move.getFromY()] = null;

        PromoteAnimator.startInNewThread(move.getPiece(),newPiece,move.getToX(),move.getToY());

        Piece take = board[move.getToX()][move.getFromY()];
        if(take != null) TakeAnimator.startInNewThread(take);

        newPiece.ini();

        //make sure after initialized it does not have a graphic node at first
        newPiece.getNode().setOpacity(0);

    }


}
