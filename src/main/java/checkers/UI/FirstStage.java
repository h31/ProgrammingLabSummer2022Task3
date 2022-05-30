package checkers.UI;

import javafx.scene.layout.BorderPane;

public class FirstStage {
    static BorderPane root;
    static BorderPane copyOfRoot;

    public FirstStage(BorderPane root) {
        FirstStage.root = root;
        copyOfRoot = root;
        initInfoCenter(root);
    }




    private void initInfoCenter(BorderPane root) {
        InfoCenter infoCenter = new InfoCenter();
        root.getChildren().add(infoCenter.getStackPane());
    }
}
