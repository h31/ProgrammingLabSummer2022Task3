package checkers.ui;

import checkers.logic.MoveResult;

import static checkers.logic.Logic.*;

public class StepBackDrawer {


    public static void normalMove(int x, int y, Piece piece){
        piece.move(x,y);
        changingTurn();

    }

    public static void killMove(int x, int y, MoveResult moveResult){
        Piece killedPiece = moveResult.getPiece();

        getLastKiller().move(x,y);

        //Подгоняем воскресшую клетку на позицию
        killedPiece.move(toBoard(killedPiece.getOldX()),toBoard(killedPiece.getOldY()));
        ContentCreator.getPieceGroup().getChildren().add(killedPiece);
        if (killedPiece.getPieceType() == Piece.PieceType.BLACK){
            ContentCreator.getLeft().getChildren().remove(killedPiece);
        } else{
            ContentCreator.getRight().getChildren().remove(killedPiece);
        }
        changingTurn();
    }
}
