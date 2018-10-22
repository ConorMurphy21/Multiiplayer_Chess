package highlighters.graphics;

import javafx.scene.Group;

public class HighlightGroup extends Group {
    private static HighlightGroup ourInstance = new HighlightGroup();

    public static HighlightGroup getInstance() {
        return ourInstance;
    }

    private HighlightGroup() {
    }
}
