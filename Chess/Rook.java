package Chess;

public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public boolean canMove(ChessBoard board, Cell start, Cell end) {
        // Rook moves horizontally or vertically
        if(start.getRow()!=end.getRow()&&start.getColumn()!=end.getColumn()) {
            return false;
        }
        // cheking whether the path is clear or not
        return board.isPathClear(start, end);
    }
}
