package Chess;


public class ChessGame {

    private Player whitePlayer;
    private Player blackPlayer;

    private ChessBoard board;

    private Player currentPlayer;

    public ChessGame(String whiteName, String blackName) {

        whitePlayer = new Player(whiteName, "White");
        blackPlayer = new Player(blackName, "Black");

        board = new ChessBoard();

        // White always starts
        currentPlayer = whitePlayer;
    }

    public boolean makeMove(int startRow, int startCol,
                            int endRow, int endCol) {

        Cell start = board.getCell(startRow, startCol);
        Cell end = board.getCell(endRow, endCol);

        // Check invalid positions
        if (start == null || end == null) {
            return false;
        }

        // Check if starting cell is empty
        if (start.isEmpty()) {
            return false;
        }

        ChessPiece piece = start.getPiece();

        // Check whether piece belongs to current player
        if (!piece.getColor().equals(currentPlayer.getColor())) {
            return false;
        }

        // Cannot capture your own piece
        if (!end.isEmpty() &&
                end.getPiece().getColor().equals(currentPlayer.getColor())) {
            return false;
        }

        // Validate piece movement
        if (!piece.canMove(board, start, end)) {
            return false;
        }

        // Create move object
        Move move = new Move(start, end);

        // Mark captured piece as killed
        if (move.getCapturedPiece() != null) {
            move.getCapturedPiece().setKilled(true);
        }

        // Move the piece
        end.setPiece(move.getMovedPiece());
        start.setPiece(null);

        // Switch player
        switchTurn();

        return true;
    }

    private void switchTurn() {

        if (currentPlayer == whitePlayer) {
            currentPlayer = blackPlayer;
        }
        else {
            currentPlayer = whitePlayer;
        }
    }
    public ChessBoard getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
