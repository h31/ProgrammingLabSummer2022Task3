package checkers.UI;

import javafx.scene.layout.BorderPane;

public class FirstStage {
    static BorderPane root;

    public FirstStage(BorderPane root) {
        FirstStage.root = root;
        initInfoCenter(root);
    }


     void initInfoCenter(BorderPane root) {
        InfoCenter infoCenter = new InfoCenter();
        root.getChildren().add(infoCenter.getStackPane());
    }
}
