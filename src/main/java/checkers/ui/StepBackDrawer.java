package checkers.ui;

import checkers.logic.MoveResult;

import static checkers.logic.Logic.*;

public class StepBackDrawer {


    public static void normalMove(int x, int y, Piece piece){
        //Очищаем клетку
        getBoard()[toBoard(piece.getOldX())][toBoard(piece.getOldY())].setPiece(null);

        piece.move(x,y);
        turn = !turn;
        changingTurn();

        setKillNeed(false);
    }

    public static void killMove(int x, int y, MoveResult moveResult){

        Piece killedPiece = moveResult.getPiece();
        //Восстанавливаем убитого
        getBoard()[toBoard(killedPiece.getOldX())][toBoard(killedPiece.getOldY())].setPiece(killedPiece);
        //Очищаем клетку занимаемую убившим
        getBoard()[toBoard(getLastKiller().getOldX())][toBoard(getLastKiller().getOldY())].setPiece(null);
        //Возвращаем шашку в предыдущую клетку
        getBoard()[x][y].setPiece(getLastKiller());


        getLastKiller().move(x,y);



        //Подгоняем воскресшую клетку на позицию
        killedPiece.move(toBoard(killedPiece.getOldX()),toBoard(killedPiece.getOldY()));
        ContentCreator.getPieceGroup().getChildren().add(killedPiece);
        if (killedPiece.getPieceType() == Piece.PieceType.BLACK){
            ContentCreator.getLeft().getChildren().remove(killedPiece);
        } else{
            ContentCreator.getRight().getChildren().remove(killedPiece);
        }
        turn = ((getKillCount() > 0) == turn); //Смена хода, если шашка ела подряд и не завершила,
        // то не меняем ход
        setKillNeed(true);
        changingTurn();
        setKillCount(getKillCount()>0 ? getKillCount() -1 : 0);


    }
}
