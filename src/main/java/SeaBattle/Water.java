package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

public class Water extends Pane {

    Water(int x, int y) {
        for (int i = 0; i <= 10; i++) {
            addHorizontallyLine(x, y + SeaBattle.SIZE * i);
        }
        for (int i = 0; i <= 10; i++) {
            addVerticallyLine(x + SeaBattle.SIZE * i, y);
        }
    }

    void addHorizontallyLine(int x, int y) {
        Line line = new Line(x, y, x + SeaBattle.SIZE * 10, y);
        line.setStroke(Color.BLACK);
        SeaBattle.group.getChildren().add(line);
    }

    void addVerticallyLine(int x, int y) {
        Line line = new Line(x, y, x, y + SeaBattle.SIZE * 10);
        line.setStroke(Color.BLACK);
        SeaBattle.group.getChildren().add(line);
    }
}
