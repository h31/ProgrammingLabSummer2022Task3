package checkers;

public enum PieceType {
    BLACK(1), WHITE(-1);

    final int moveDir;

    public int getMoveDir() {
        return moveDir;
    }
    PieceType(int moveDir){
        this.moveDir = moveDir;
    }


}
