package utils;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.shape.Rectangle;

public class SizeUtil {
    private static SizeUtil ourInstance;

    private ReadOnlyDoubleProperty width,height;
    private NumberBinding size;

    private SizeUtil(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
        this.width = width;
        this.height = height;
        //size of a single node
        size = Bindings.min(width,height).divide(8);
    }



    public void sizeRect(Rectangle node, int x, int y){
        node.widthProperty().bind(size);
        node.heightProperty().bind(size);
        node.xProperty().bind(size.multiply(x));
        node.yProperty().bind(size.multiply(y));

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
