package checkers.logic;

import checkers.ui.Piece;

public class MoveResult {

    private final MoveType moveType;
    private Piece piece;
    private boolean Crowned;

    public void setWasCrowned(boolean wasCrowned) {
        this.Crowned = wasCrowned;
    }

    public boolean WasCrowned() {
        return Crowned;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Piece getPiece() {
        return piece;
    }

    public MoveResult (MoveType moveType){ //Используется для обычного и бракованного шага
        this.moveType = moveType;
    }



    public MoveResult(MoveType moveType, Piece piece){ //Используется для KILL - шага. Передает съеденную фигуру
        this.moveType = moveType;
        this.piece = piece;
        this.Crowned = false;

    }
}
