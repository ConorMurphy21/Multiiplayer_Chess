import board.graphics.BoardGroup;
import highlighters.graphics.HighlightGroup;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pieces.graphics.PieceGroup;
import utils.SizeUtil;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
                    //ini simple borderpane as root
        BorderPane root = new BorderPane();
                            //starting size of 600 x 600
        Scene scene = new Scene(root,600,600);
                            //create size util because it is necessary for the group initializers
        SizeUtil.createInstance(scene.widthProperty(),scene.heightProperty());

        //initialize the 3 layers of the scene
        Group boardGroup = BoardGroup.getInstance();
        Group highlightGroup = HighlightGroup.getInstance();
        Group pieceGroup = PieceGroup.getInstance();

        ((PieceGroup) pieceGroup).initializeGame();
        //add them to a stack pane
        StackPane stack = new StackPane(boardGroup,highlightGroup,pieceGroup);

        //add to root, set scene and show
        root.setCenter(stack);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}