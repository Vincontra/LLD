package Chess;


public class Move {

    private Cell start;
    private Cell end;

    private ChessPiece movedPiece;
    private ChessPiece capturedPiece;

    public Move(Cell start, Cell end) {

        this.start = start;
        this.end = end;

        this.movedPiece = start.getPiece();
        this.capturedPiece = end.getPiece();
    }

    public Cell getStart() {
        return start;
    }

    public Cell getEnd() {
        return end;
    }

    public ChessPiece getMovedPiece() {
        return movedPiece;
    }

    public ChessPiece getCapturedPiece() {
        return capturedPiece;
    }
}
