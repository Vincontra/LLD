package Chess;


public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean canMove(ChessBoard board, Cell start, Cell end) {

        int direction = getColor().equals("White") ? -1 : 1;

        int rowDiff = end.getRow() - start.getRow();
        int colDiff = Math.abs(end.getColumn() - start.getColumn());

        // Normal forward movement
        if (colDiff == 0) {
            if (rowDiff == direction && end.isEmpty()) {
                return true;
            }
        }

        // Diagonal capture
        if (colDiff == 1 && rowDiff == direction) {
            return !end.isEmpty();
        }

        return false;
    }
}
