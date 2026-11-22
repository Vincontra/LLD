# Chess LLD --- 1--2 Hour Machine Coding Round

A simple, interview-focused Low-Level Design (LLD) implementation of a
two-player Chess game in Java.

The goal of this version is to demonstrate clean object-oriented design,
separation of responsibilities, and basic move handling within the time
limit of a typical 1--2 hour machine-coding round. It is a foundation,
not a complete production-ready chess engine.

## 1. Requirements covered

-   Two players: White and Black.
-   An 8 × 8 chess board.
-   The six chess piece types: King, Queen, Rook, Bishop, Knight, and
    Pawn.
-   Each piece has its own basic movement logic.
-   Players take turns.
-   A move is rejected when:
    -   The source or destination is outside the board.
    -   The source cell is empty.
    -   The piece does not belong to the current player.
    -   The destination contains the current player's own piece.
    -   The piece's basic movement rule does not allow the move.
-   A successful move transfers the piece to the destination.
-   A captured piece is marked as killed.

## 2. Class design

The design uses small classes, each with one clear responsibility.

  -----------------------------------------------------------------------
  Class                   Responsibility          Why it exists
  ----------------------- ----------------------- -----------------------
  `Player`                Stores a player's name  Keeps player
                          and colour.             information separate
                                                  from game logic.

  `ChessBoard`            Creates the 8 × 8       Keeps board setup and
                          board, places the       board-related
                          pieces, and provides    operations in one
                          board cells.            place.

  `Cell`                  Represents one square   A cell can be empty or
                          using row, column, and  contain one chess
                          an optional piece.      piece.

  `ChessPiece`            Abstract parent class   Avoids repeating common
                          containing common piece fields and requires
                          data and the            every piece to define
                          `canMove()` contract.   its movement rule.

  `King`, `Queen`,        Implement the movement  Uses inheritance and
  `Rook`, `Bishop`,       rules for their         polymorphism to keep
  `Knight`, `Pawn`        respective pieces.      piece-specific logic
                                                  separate.

  `Move`                  Stores the source cell, Represents a move as
                          destination cell, moved one object and
                          piece, and captured     preserves the
                          piece.                  information needed to
                                                  perform it.

  `ChessGame`             Coordinates players,    Acts as the main
                          turns, move validation, controller so game flow
                          captures, and board     is not mixed into the
                          updates.                board or piece classes.

  `Main`                  Creates a game and runs Provides the program
                          a small example.        entry point and a quick
                                                  way to test the design.
  -----------------------------------------------------------------------

## 3. How the classes work together

For a move such as White moving a pawn from E2 to E4:

1.  `Main` creates a `ChessGame`.
2.  `ChessGame` creates both `Player` objects and a `ChessBoard`.
3.  The board creates 64 `Cell` objects and places the pieces in their
    starting positions.
4.  `ChessGame.makeMove()` receives the source and destination
    coordinates.
5.  It checks that both cells exist, the source is occupied, and the
    piece belongs to the current player.
6.  It prevents a player from capturing their own piece.
7.  It calls the piece's `canMove()` method. Java selects the correct
    implementation based on the actual piece type.
8.  If the move is valid, a `Move` object records the move details.
9.  If the destination contains an opponent's piece, that piece is
    marked as captured (`killed`).
10. The piece is moved to the destination, the source is cleared, and
    the turn changes.

This flow keeps responsibilities clear: - `ChessPiece` decides whether
the piece's movement pattern allows the move. - `ChessBoard` knows the
board layout and whether a sliding piece's path is clear. - `ChessGame`
checks whose turn it is and coordinates the move.

## 4. Important design decisions

### Inheritance and polymorphism

`ChessPiece` is abstract because a generic chess piece does not have one
movement rule. Each concrete piece overrides `canMove()`.

For example: - Rook: moves horizontally or vertically. - Bishop: moves
diagonally. - Knight: moves in an L-shape and can jump over pieces. -
Queen: combines rook and bishop movement. - King: moves one square in
any direction. - Pawn: in this basic version, moves one square forward
or captures diagonally.

This avoids writing a large `if-else` or `switch` statement for every
piece inside `ChessGame`.

### Why `Cell` instead of storing pieces directly in a 2D array?

The board is represented as `Cell[8][8]`. Each cell knows its own
coordinates and the piece it contains. This makes it easier to pass a
source and destination to movement methods and to represent empty
squares.

### Why `Move` is a separate class

A move involves more than two coordinates. It can also involve the moved
piece and a captured piece. A separate `Move` object groups that
information and gives us a natural place to add move history or undo
support later.

### Why `ChessGame` controls the turn

Turn management is part of the overall game flow, not a responsibility
of an individual piece or cell. Keeping it in `ChessGame` makes the
design easier to understand and extend.

### Why `isPathClear()` is needed

Rook, Bishop, and Queen cannot pass through pieces.
`ChessBoard.isPathClear()` checks the cells between the source and
destination. The destination itself is excluded because it may contain
an opponent's piece that can be captured.

Knight does not use this method because it can jump over other pieces.

## 5. Board coordinates

The board uses zero-based array indexes:

-   Row `0`: Black's back rank.
-   Row `1`: Black's pawns.
-   Rows `2` to `5`: initially empty.
-   Row `6`: White's pawns.
-   Row `7`: White's back rank.
-   Column `0` is file A and column `7` is file H.

Examples:

  Chess square   Java board position
  -------------- -----------------------
  E2             `board.getCell(6, 4)`
  E4             `board.getCell(4, 4)`
  A1             `board.getCell(7, 0)`
  A5             `board.getCell(3, 0)`

White moves toward decreasing row numbers; Black moves toward increasing
row numbers.

## 6. Example usage

``` java
public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame("Vinay", "Rahul");

        // White moves the pawn from E2 to E4.
        boolean result = game.makeMove(6, 4, 4, 4);

        System.out.println("Move successful: " + result);
        System.out.println("Current turn: " + game.getCurrentPlayer().getName());
    }
}
```

Expected output:

``` text
Move successful: true
Current turn: Rahul
```

## 7. What is intentionally not implemented

To keep the solution achievable in a 1--2 hour round, this version
focuses on the core object model and basic move flow. It is **not yet a
complete chess implementation**.

The following rules and features are not implemented or are only
partially implemented:

-   Pawn's initial two-square move.
-   Pawn promotion.
-   En passant.
-   Castling.
-   Check detection.
-   Preventing a move that leaves the current player's king in check.
-   Checkmate and stalemate detection.
-   Draw conditions and repetition rules.
-   Full validation of every chess rule and edge case.

The basic King movement check does not verify whether the destination
square is under attack. Similarly, the basic Pawn implementation does
not cover every pawn rule. Do not describe this version as a fully
playable or rule-complete chess game.

## 8. How to explain this in an LLD interview

A concise explanation could be:

> I started by identifying the main entities: Player, Board, Cell,
> Piece, and Move. I used an abstract ChessPiece class because all
> pieces share common properties, but each piece has a different
> movement rule. I implemented those rules in separate subclasses using
> polymorphism. ChessBoard owns the 8 × 8 cells and handles board setup
> and path checking. ChessGame coordinates turns, validates the basic
> conditions for a move, handles captures, and updates the board. I kept
> Move as a separate class so move information is grouped together and
> can later support history or undo. Given the time limit, I prioritised
> the core design and basic gameplay flow, and would add advanced rules
> such as check, checkmate, castling, and promotion next.

## 9. Suggested implementation order in a 1--2 hour round

1.  Clarify requirements and agree on the scope.
2.  Create `Cell` and `ChessBoard`.
3.  Create `ChessPiece` and the six piece subclasses.
4.  Add `Player` and `Move`.
5.  Implement `ChessGame.makeMove()` and turn switching.
6.  Test a valid move, an invalid move, a wrong-player move, and a
    capture.
7.  If time remains, add pawn's two-square opening move, check
    detection, or other rules.

**Interview tip:** Explain the responsibility of each class while
coding. A clean, working core with clearly stated limitations is better
than rushing to implement every chess rule without a sound design.
