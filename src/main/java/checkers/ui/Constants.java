package checkers.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Constants {
    public static final int APP_WIDTH = 480;
    public static final int APP_HEIGHT = 580;

    public static final int INFO_CENTER_HEIGHT = 100;
    public static final int TILE_BOARD_HEIGHT = 480;

    public static final int SIZE = 8;
    public enum SIDES{
        white,
        black,
        no
    }

    public enum WAYTOMOVE {
        no,
        move,
        eat
    }

    public static final Background WHITE_CHECKER =
            new Background(new BackgroundFill(Color.WHITE, new CornerRadii(360), Insets.EMPTY));
    public static final Background BLACK_CHECKER =
            new Background(new BackgroundFill(Color.RED, new CornerRadii(360), Insets.EMPTY));
    public static final Background NO_CHECKER =
            new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(360), Insets.EMPTY));
    public static final Background CHOOSEN_CHECKER =
            new Background(new BackgroundFill(Color.YELLOW, new CornerRadii(360), Insets.EMPTY));
    public static final Background BLACK_BACK =
            new Background(new BackgroundFill(Color.BLACK, new CornerRadii(360), Insets.EMPTY));
    public static final Background KING =
            new Background(new BackgroundFill(Color.LIGHTGREEN, new CornerRadii(360), Insets.EMPTY));


}
