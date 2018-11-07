package main;

import board.graphics.BoardGroup;
import highlighters.graphics.HighlightGroup;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import networking.Client;
import pieces.graphics.PieceGroup;
import utils.SizeUtil;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    private static BooleanProperty thisTurn = new SimpleBooleanProperty(false);

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;

        Client client = new Client();
        client.start();


    }

    public static void ini(boolean isWhite){
                     //ini simple borderpane as root
        BorderPane root = new BorderPane();
                            //starting size of 600 x 600
        Scene scene = new Scene(root,600,600);
                            //create size util because it is necessary for the group initializers
        SizeUtil.createInstance(scene.widthProperty(),scene.heightProperty(), isWhite);

        thisTurn.setValue(isWhite);

        //initialize the 3 layers of the scene
        Group boardGroup = BoardGroup.getInstance();
        Group highlightGroup = HighlightGroup.getInstance();
        PieceGroup pieceGroup = PieceGroup.getInstance();

        pieceGroup.initializeGame();

        //add them to a stack pane
        StackPane stack = new StackPane(boardGroup,highlightGroup,pieceGroup);

        //add to root, set scene and show
        root.setCenter(stack);
        stage.setScene(scene);
        stage.show();


    }

    public static BooleanProperty getThisTurn(){
        return thisTurn;
    }

}