package Chess;


public class Knight extends ChessPiece {
    public Knight(String color) {
        super(color);
    }
    @Override
    public boolean canMove(ChessBoard board, Cell start, Cell end) {
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        int colDiff = Math.abs(start.getColumn() - end.getColumn());
        return (rowDiff == 2 && colDiff == 1)||(rowDiff == 1 && colDiff == 2);
    }
}
