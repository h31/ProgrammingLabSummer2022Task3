package checkers.ui;

import javafx.scene.layout.BorderPane;

public class GameStage {
    private final InfoCenter infoCenter;

    public GameStage(BorderPane root, InfoCenter infoCenter) {
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
        CheckersBoard checkersBoard = new CheckersBoard(infoCenter, false);
        root.getChildren().add(checkersBoard.getStackPane());
    }
}
