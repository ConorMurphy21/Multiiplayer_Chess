package cache;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import networking.Client;


public class MoveCache {

    /*
     * turn is true if it is client's turn
     * false if it is the server's turn
     */

    private BooleanProperty turn = new SimpleBooleanProperty();

    private ObservableList<Move> moves = FXCollections.observableArrayList();

    private static MoveCache ourInstance = new MoveCache();

    public static MoveCache getInstance(){
        return ourInstance;
    }

    private MoveCache(){}

    public void addMove(Move move, boolean fromServer){

            //from server
        if(fromServer){
            if(turn.get()){
                //would be a good idea to send back an error packet later on
                return;
            }
        }else{  //from client
            if(!turn.get()){
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

    public void ini(boolean startTurn){
        turn.setValue(startTurn);
    }

    public BooleanProperty getTurn(){
        return turn;
    }
}
