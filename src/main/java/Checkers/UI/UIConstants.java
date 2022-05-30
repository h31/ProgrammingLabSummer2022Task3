package Checkers.UI;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UIConstants {
    public static final int APP_WIDTH = 600;
    public static final int APP_HEIGHT = 700;

    public static final int INFO_CENTER_HEIGHT = 100;
    public static final int TILE_BOARD_HEIGHT = 600;

    public static final Background backWhite= new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background backBlack= new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background backNo= new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY));
    public static final Background backGo= new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY));
}
