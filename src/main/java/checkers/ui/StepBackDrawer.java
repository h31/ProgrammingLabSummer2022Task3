package checkers.ui;


import checkers.logic.Step;

import static checkers.logic.Logic.*;
import static checkers.ui.ContentCreator.getUnderTopText;

public class StepBackDrawer {


    public static void normalMove(Step step){
        changingTurn();
        getUnderTopText().setText("");
        step.getPiece().move(step.getX(),step.getY());
        if(step.getMoveResult().WasCrowned()) {
            step.getPiece().getCrownImgView().setVisible(false);
        }
    }

    public static void killMove(Step step){
        changingTurn();
        getUnderTopText().setText("");
        Piece killedPiece = step.getMoveResult().getPiece();
        Piece killerPiece = step.getPiece();

        killerPiece.move(step.getX(), step.getY());

        //Подгоняем воскресшую клетку на позицию
        killedPiece.move(toBoard(killedPiece.getOldX()),toBoard(killedPiece.getOldY()));
        ContentCreator.getPieceGroup().getChildren().add(killedPiece);
        if (killedPiece.getPieceType() == Piece.PieceType.BLACK){
            ContentCreator.getLeft().getChildren().remove(killedPiece);
        } else{
            ContentCreator.getRight().getChildren().remove(killedPiece);
        }
        if(step.getMoveResult().WasCrowned()) {
            killerPiece.getCrownImgView().setVisible(false);
        }

    }
}
