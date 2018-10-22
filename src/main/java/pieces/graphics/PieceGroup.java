package pieces.graphics;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import pieces.Piece;
import pieces.PieceFactory;
import utils.SizeUtil;

public class PieceGroup extends Group {
    private static PieceGroup ourInstance = new PieceGroup();

    public static PieceGroup getInstance() {
        return ourInstance;
    }

    private PieceGroup() {
        Rectangle sizer = new Rectangle();
        SizeUtil.getInstance().sizeSizingRect(sizer);
    }

    public void initializeGame(){
        for(int i = 0; i < 8; i++){
            if(i == 2) i = 6;
            for(int j = 0; j < 8; j++){
                Piece p  = PieceFactory.create(j,i);
                if(p != null)p.ini();
            }
        }

    }
}
