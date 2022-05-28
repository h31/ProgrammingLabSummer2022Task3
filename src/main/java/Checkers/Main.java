package Checkers;

import Checkers.UI.CheckBoard;
import Checkers.UI.InfoCenter;
import Checkers.UI.TileBoard;
import Checkers.UI.UIConstants;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private InfoCenter infoCenter;
    private TileBoard tileBoard;
    private CheckBoard checkBoard;


    public void start(Stage stage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
            initLayout(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
        initCheckBoard(root);
    }

    private void initTileBoard(BorderPane root) {
        tileBoard = new TileBoard(infoCenter);
        root.getChildren().add(tileBoard.getStackPane());
    }

    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        root.getChildren().add(infoCenter.getStackPane());
    }

    private void initCheckBoard(BorderPane root) {
        checkBoard = new CheckBoard(infoCenter);
        root.getChildren().add(checkBoard.getStackPane());
    }
}
