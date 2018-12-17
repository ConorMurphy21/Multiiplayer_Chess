package board;

import cache.*;

public class BoardManager {

    private static BoardManager ourInstance = new BoardManager();

    public static BoardManager getInstance() {
        return ourInstance;
    }

    private BoardManager() {
        MoveCache.getInstance().addListener( l -> {
            if(l.getAddedSize() == 1)
                updateBoard(l.getAddedSubList().get(0));
        });
    }

    private void updateBoard(Move m){
        switch (m.getChar()){
            case 'n':
                break;
            case 'c':
                break;
            case 'e':
                break;
            case 'p':
                break;
        }
    }

    private void normal(NormalMove move){

    }

    private void castle(CastleMove move){

    }

    private void enPassant(EnPassant move){

    }

    private void promotion(Promotion move){
    }


}
