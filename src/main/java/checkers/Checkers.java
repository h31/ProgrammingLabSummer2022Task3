package checkers;


import checkers.ui.Media;
import checkers.ui.WelcomePage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static checkers.ui.Media.getIcon;


public class Checkers extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        Scene scene = new Scene(WelcomePage.startPage(window));
        window.setResizable(false);
        window.getIcons().add(getIcon());
        window.setTitle("Checkers");
        window.setScene(scene);
        window.show();
        window.centerOnScreen();
    }
}