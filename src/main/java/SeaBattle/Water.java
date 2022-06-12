package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

public class Water extends Pane {
    Water(int x, int y) {
        for (int i = 0; i <= 10; i++) {
            addHorizontallyLine(x, y + 25 * i);
        }
        for (int i = 0; i <= 10; i++) {
            addVerticallyLine(x + 25 * i, y);
        }
    }

    void addHorizontallyLine(int x, int y) {
        Line line = new Line(x, y, x + 250, y);
        line.setStroke(Color.BLACK);
        SeaBattle.group.getChildren().add(line);
    }

    void addVerticallyLine(int x, int y) {
        Line line = new Line(x, y, x, y + 250);
        line.setStroke(Color.BLACK);
        SeaBattle.group.getChildren().add(line);
    }
}
