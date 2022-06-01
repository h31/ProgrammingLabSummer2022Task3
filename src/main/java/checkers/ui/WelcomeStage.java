package checkers.ui;

import javafx.scene.layout.BorderPane;

public class WelcomeStage {
    static BorderPane root;

    public WelcomeStage(BorderPane root) {
        WelcomeStage.root = root;
        initInfoCenter(root);
    }


     void initInfoCenter(BorderPane root) {
        InfoCenter infoCenter = new InfoCenter();
        root.getChildren().add(infoCenter.getStackPane());
    }
}
