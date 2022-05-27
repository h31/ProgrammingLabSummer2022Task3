package enCheckers.UI;

import javafx.scene.layout.StackPane;

public class Checker {
    private StackPane pane;
    public Checker(byte row) {
        pane = new StackPane();
        pane.setMinSize(50, 50);
        if (row == 1 || row == 2 || row == 0){


        }

    }

    public StackPane getStackPane() {
        return pane;
    }

}
