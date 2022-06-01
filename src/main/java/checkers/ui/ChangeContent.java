package checkers.ui;

import static checkers.logic.Logic.isKillNeed;
import static checkers.logic.Logic.turn;
import static checkers.ui.ContentCreator.*;

public class ChangeContent {

    public static void eatAlarm() {
        if (isKillNeed()) {
            getUnderTopText().setText(turn== Piece.PieceType.BLACK ? getBlackEat().getText() : getWhiteEat().getText());
        } else {
            getUnderTopText().setText("");
        }
    }

    public static void changingTurn() {
        getTopText().setText(turn== Piece.PieceType.BLACK ? "Чёрные ходят" : "Белые ходят");

    }
}
