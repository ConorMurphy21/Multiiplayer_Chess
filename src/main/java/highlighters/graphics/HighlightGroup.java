package highlighters.graphics;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import utils.SizeUtil;

public class HighlightGroup extends Group {
    private static HighlightGroup ourInstance = new HighlightGroup();

    public static HighlightGroup getInstance() {
        return ourInstance;
    }

    private HighlightGroup() {
        Rectangle sizer = new Rectangle();
        SizeUtil.getInstance().sizeSizingRect(sizer);
        getChildren().add(sizer);
        //setPickOnBounds(false);
    }
}
