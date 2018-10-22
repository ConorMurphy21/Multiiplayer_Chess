package board.graphics;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.SizeUtil;

public class BoardGroup extends Group {
    private static BoardGroup ourInstance = new BoardGroup();

    public static BoardGroup getInstance() {
        return ourInstance;
    }

    private BoardGroup() {
        initializeBoard();
    }

    private void initializeBoard(){
        //this needs to change with which player it is but for now constant

        SizeUtil su = SizeUtil.getInstance();

        //changes just switch i values for the reverse board
        int i = 0;

        for( ; i < 8; i+=2){
            for(int j = 0; j < 8; j+=2){
                initializeBlock(su,i,j);
            }
        }

        i = 1;
        for( ; i < 8; i+=2){
            for(int j = 1; j < 8; j+=2){
                initializeBlock(su,i,j);
            }
        }
    }

    private void initializeBlock(SizeUtil su,int x, int y){
        Rectangle rect = new Rectangle();
        rect.setStroke(Color.BLACK);
        su.sizeRect(rect,x,y);
        getChildren().add(rect);
    }
}

