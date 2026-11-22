package Chess;

public abstract class ChessPiece {
    private String color;
    private boolean killed;

    public ChessPiece(String color) {
        this.color = color;
        this.killed = false;
    }

    public String getColor() {
        return color;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }
    public abstract boolean canMove(ChessBoard board, Cell start, Cell end);
}
