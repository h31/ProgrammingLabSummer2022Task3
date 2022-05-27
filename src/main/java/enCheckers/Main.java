package enCheckers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private InfoCenter infoCenter;

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
            initLayout(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
    }

    private void initTileBoard(BorderPane root) {
    }

    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();

        root.getChildren().add(infoCenter.getStackPane());
    }

    public static void main(String[] args) {
        launch(args);
    }

}