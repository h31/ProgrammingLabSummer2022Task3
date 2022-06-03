package SeaBattle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;

public class Water extends Pane{
    public Water(int x, int y) {
        Line line1 = new Line(x, y, x + 250, y);
        line1.setStroke(Color.BLACK);

        Line line2 = new Line(x, y + 25, x + 250, y + 25);
        line2.setStroke(Color.BLACK);

        Line line3 = new Line(x, y + 50, x + 250, y + 50);
        line3.setStroke(Color.BLACK);

        Line line4 = new Line(x, y + 75, x + 250, y + 75);
        line4.setStroke(Color.BLACK);

        Line line6 = new Line(x, y + 100, x + 250, y + 100);
        line6.setStroke(Color.BLACK);

        Line line7 = new Line(x, y + 125, x + 250, y + 125);
        line7.setStroke(Color.BLACK);

        Line line8 = new Line(x, y + 150, x + 250, y + 150);
        line8.setStroke(Color.BLACK);

        Line line9 = new Line(x, y + 175, x + 250, y + 175);
        line9.setStroke(Color.BLACK);

        Line line10 = new Line(x, y + 200, x + 250, y + 200);
        line10.setStroke(Color.BLACK);

        Line line11 = new Line(x, y + 225, x + 250, y + 225);
        line11.setStroke(Color.BLACK);

        Line line12 = new Line(x, y + 250, x + 250, y + 250);
        line12.setStroke(Color.BLACK);

        getChildren().addAll(line1, line2, line3, line4, line6, line7, line8, line9, line10, line11, line12);



        Line lineY1 = new Line(x, y, x, y + 250);
        lineY1.setStroke(Color.BLACK);

        Line lineY2 = new Line(x + 25, y, x + 25, y + 250);
        lineY2.setStroke(Color.BLACK);

        Line lineY3 = new Line(x + 50, y, x + 50, y + 250);
        lineY3.setStroke(Color.BLACK);

        Line lineY4 = new Line(x + 75, y, x + 75, y + 250);
        lineY4.setStroke(Color.BLACK);

        Line lineY5 = new Line(x + 100, y, x + 100, y + 250);
        lineY5.setStroke(Color.BLACK);

        Line lineY6 = new Line(x + 125, y, x + 125, y + 250);
        lineY6.setStroke(Color.BLACK);

        Line lineY7 = new Line(x + 150, y, x + 150, y + 250);
        lineY7.setStroke(Color.BLACK);

        Line lineY8 = new Line(x + 175, y, x + 175, y + 250);
        lineY8.setStroke(Color.BLACK);

        Line lineY9 = new Line(x + 200, y, x + 200, y + 250);
        lineY9.setStroke(Color.BLACK);

        Line lineY10 = new Line(x + 225, y, x + 225, y + 250);
        lineY10.setStroke(Color.BLACK);

        Line lineY11 = new Line(x + 250, y, x + 250, y + 250);
        lineY11.setStroke(Color.BLACK);


        getChildren().addAll(lineY1, lineY2, lineY3, lineY4, lineY5, lineY6, lineY7, lineY8, lineY9, lineY10, lineY11);
    }
}
