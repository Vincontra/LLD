package Chess;
public class ChessBoard {
    private Cell[][] board;  // ChessBoard has a Cell matlab composition
    public ChessBoard() {
        board = new Cell[8][8];
        // Create all 64 cells
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = new Cell(row, col);
            }
        }
        // Place chess pieces
        initializeBoard();
    }
    private void initializeBoard() {

        // Black pieces
        board[0][0].setPiece(new Rook("Black"));
        board[0][1].setPiece(new Knight("Black"));
        board[0][2].setPiece(new Bishop("Black"));
        board[0][3].setPiece(new Queen("Black"));
        board[0][4].setPiece(new King("Black"));
        board[0][5].setPiece(new Bishop("Black"));
        board[0][6].setPiece(new Knight("Black"));
        board[0][7].setPiece(new Rook("Black"));

        // Black pawns
        for (int col = 0; col < 8; col++) {
            board[1][col].setPiece(new Pawn("Black"));
        }

        // White pawns
        for (int col = 0; col < 8; col++) {
            board[6][col].setPiece(new Pawn("White"));
        }

        // White pieces
        board[7][0].setPiece(new Rook("White"));
        board[7][1].setPiece(new Knight("White"));
        board[7][2].setPiece(new Bishop("White"));
        board[7][3].setPiece(new Queen("White"));
        board[7][4].setPiece(new King("White"));
        board[7][5].setPiece(new Bishop("White"));
        board[7][6].setPiece(new Knight("White"));
        board[7][7].setPiece(new Rook("White"));
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= 8 ||
                col < 0 || col >= 8) {
            return null;
        }
        return board[row][col];
    }
    public boolean isPathClear(Cell start, Cell end) {
        int rowDiff = Integer.compare(end.getRow(), start.getRow());
        int colDiff = Integer.compare(end.getColumn(),start.getColumn());
        int row = start.getRow() + rowDiff;
        int col = start.getColumn() + colDiff;
        while (row!=end.getRow()||col!=end.getColumn()){
            if (!board[row][col].isEmpty()) {
                return false;
            }
            row += rowDiff;
            col += colDiff;
        }
        return true;
    }
}
