package log;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class MoveLog {


    private final ObservableList<Move> moves = FXCollections.observableArrayList(new ArrayList<>());

    private static final MoveLog ourInstance = new MoveLog();

    private Turn turn;

    public static MoveLog getInstance(){
        return ourInstance;
    }

    private MoveLog(){
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
        if(moves.size() == 0)return null;
        return moves.get(moves.size()-1);
    }
}
