package Chess;


public class Queen extends ChessPiece {

    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean canMove(ChessBoard board, Cell start, Cell end){
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        int colDiff = Math.abs(start.getColumn() - end.getColumn());
        boolean straight=start.getRow()==end.getRow()||start.getColumn() == end.getColumn();
        boolean diagonal=rowDiff==colDiff;
        return (straight||diagonal)&& board.isPathClear(start, end);
    }
}

