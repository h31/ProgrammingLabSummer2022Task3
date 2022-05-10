package checkers;

public class MoveResult {

    private final MoveType moveType;
    private Piece piece;

    public MoveType getMoveType() {
        return moveType;
    }

    public Piece getPiece() {
        return piece;
    }

    public MoveResult (MoveType moveType){
        this.moveType = moveType;
    }

    public MoveResult(MoveType moveType, Piece piece){
        this.moveType = moveType;
        this.piece = piece;
    }
}
