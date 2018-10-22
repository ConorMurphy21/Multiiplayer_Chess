package pieces.graphics;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

public class PieceNode extends ImageView {

    static HashMap<String, Image> images = new HashMap<>();
    public PieceNode(String path){
        if(images.get(path) == null){
            Image image = new Image(getClass().getClassLoader().getResourceAsStream(path));
            images.put(path,image);
        }
        setImage(images.get(path));
    }

}
