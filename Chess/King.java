package Chess;


public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public boolean canMove(ChessBoard board, Cell start, Cell end) {

        int rowDiff = Math.abs(start.getRow() - end.getRow());
        int colDiff = Math.abs(start.getColumn() - end.getColumn());

        return rowDiff <= 1 && colDiff <= 1;
    }
}
