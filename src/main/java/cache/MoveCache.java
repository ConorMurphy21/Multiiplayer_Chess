package cache;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class MoveCache {


    private final ObservableList<Move> moves = FXCollections.observableArrayList(new ArrayList<>());

    private static final MoveCache ourInstance = new MoveCache();

    private Turn turn;

    public static MoveCache getInstance(){
        return ourInstance;
    }

    private MoveCache(){
    }

    public void addMove(Move move, boolean fromServer){
        if(turn == null)turn = Turn.getInstance();
            //from server
        if(fromServer){
            if(turn.getTurn()){
                //would be a good idea to send back an error packet later on
                return;
            }
        }else{  //from client
            if(!turn.getTurn()){
                //would be a good idea to display an error message at some point
                return;
            }
        }
        //after checking if it is the correct turn add move to moves
        moves.add(move);
    }

    public void addListener(ListChangeListener<? super Move> listChangeListener){
        moves.addListener(listChangeListener);
    }

    public Move getLastMove(){
        return moves.get(moves.size()-1);
    }
}
