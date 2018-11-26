package stackRoots;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public abstract class StackRoot extends BorderPane {

    private static StackPane stack;

    public StackRoot(){
        stack.getChildren().add(this);
    }

    public static void setStack(StackPane stack){
        StackRoot.stack = stack;
    }

    public void pop(){
        stack.getChildren().remove(this);
    }
}
