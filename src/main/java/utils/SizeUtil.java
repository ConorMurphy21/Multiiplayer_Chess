package utils;

import highlighters.graphics.Highlight;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.graphics.PieceNode;

public class SizeUtil {
    private static SizeUtil ourInstance;

    private NumberBinding size,minDim;
    //the board is flipped when so both players on their own screen see theirs at the bottom
    //but the indexes in the actual board are the same
    private boolean flipped;

    private SizeUtil(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
        //size of a single node

        minDim = Bindings.min(width,height);
        size = minDim.divide(8);

        flipped = true;

    }


    //binds rectangle to grid x, y
    public void sizeRect(Rectangle node, int x, int y){
        node.widthProperty().bind(size);
        node.heightProperty().bind(size);
        node.xProperty().bind(size.multiply(x));
        node.yProperty().bind(size.multiply(y));
    }

    public void sizeHighlight(Highlight rect, int x, int y){
        rect.widthProperty().bind(size);
        rect.heightProperty().bind(size);
         if(flipped){
            rect.xProperty().bind(new SimpleIntegerProperty(7).subtract(x).multiply(size));
            rect.yProperty().bind(new SimpleIntegerProperty(7).subtract(y).multiply(size));
        }else{
            rect.xProperty().bind(size.multiply(x));
            rect.yProperty().bind(size.multiply(y));
        }
    }

    private void sizeNode(Node node, int x, int y, boolean flipped){

    }
    public void sizeSizingRect(Rectangle node){
        node.widthProperty().bind(minDim);
        node.heightProperty().bind(minDim);
        node.setX(0);
        node.setY(0);
        node.setFill(Color.rgb(0,0,0,0));
    }
    public void sizePieceNode(PieceNode node, IntegerProperty x, IntegerProperty y){
        node.fitWidthProperty().bind(size);
        node.fitHeightProperty().bind(size);
        if(flipped){
            node.xProperty().bind(new SimpleIntegerProperty(7).subtract(x).multiply(size));
            node.yProperty().bind(new SimpleIntegerProperty(7).subtract(y).multiply(size));
        }else{
            node.xProperty().bind(size.multiply(x));
            node.yProperty().bind(size.multiply(y));
        }
    }


    public boolean getFlipped(){
        return flipped;
    }


    /** INSTANCE MANAGEMENT **/
    public static SizeUtil createInstance(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        if(ourInstance != null)return ourInstance;
        else ourInstance = new SizeUtil(width, height);
        return ourInstance;
    }

    public static SizeUtil getInstance() {
        return ourInstance;
    }
}
