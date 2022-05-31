package checkers.UI;

import javafx.scene.layout.BorderPane;

public class PrimaryStage {
    private final InfoCenter infoCenter;

    public PrimaryStage(BorderPane root, InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        initLayout(root);
    }

    private void initLayout(BorderPane root) {
        initTileBoard(root);
        initCheckers(root);
    }

    private void initTileBoard(BorderPane root) {
        TileBoard tileBoard = new TileBoard();
        root.getChildren().add(tileBoard.getStackPane());
    }

    private void initCheckers(BorderPane root) {
        CheckersBoard checkersBoard = new CheckersBoard(infoCenter);
        root.getChildren().add(checkersBoard.getStackPane());
    }
}
