package pieces.graphics;

import javafx.scene.Group;

public class PieceGroup extends Group {
    private static PieceGroup ourInstance = new PieceGroup();

    public static PieceGroup getInstance() {
        return ourInstance;
    }

    private PieceGroup() {
    }
}
