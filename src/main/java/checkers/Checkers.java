package checkers;

import checkers.ui.ContentCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Checkers extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        Scene scene = new Scene( new ContentCreator().createContent());
        window.getIcons().add(ContentCreator.getIcon());
        window.setTitle("Checkers");
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }
}