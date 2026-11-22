package Chess;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame("Vinay", "Rahul");
        // White moves Pawn from E2 to E4
        boolean result=game.makeMove(6, 4, 4, 4);
        System.out.println("Move successful: " + result);
        System.out.println("Current turn: "+game.getCurrentPlayer().getName());
    }
}

