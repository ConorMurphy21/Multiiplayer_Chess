package highlighters.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.SizeUtil;

public class Highlight extends Rectangle {

    private static SizeUtil su;

    private static final Color color = Color.rgb(24,200,200,0.6);

    private static final Color select = Color.rgb(0,0,140,0.6);

    public Highlight(int x, int y){
        if(su == null)su = SizeUtil.getInstance();
        su.sizeHighlight(this,x,y);
        this.setFill(color);
        setOnMouseEntered(e -> {
            setFill(select);
        });
        setOnMouseExited(e -> setFill(color));
    }
}
