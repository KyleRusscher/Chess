package chess;

import pieces.Pawn;
import pieces.Bishop;
import pieces.IChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/******************************************************************
 * Model for the chess game
 *
 * @author Kyle Russcher
 */
public class ChessModel implements IChessModel {
    /* Hold teh board positions */
    private IChessPiece[][] board;

    public boolean AI = false;

    /* Holds the player whose turn it currently is */
    private Player player;

    /* After every move the piece that moved is stored for
        retrieval in undo method */
    private ArrayList<IChessPiece> pieceMove = new ArrayList<>();

    /* After every move the position moved to piece
    (piece to be taken) is stored for retrieval in undo method*/
    private ArrayList<IChessPiece> deadPieces = new ArrayList<>();

    /* After every move the move that was made is stored for
        retrieval in undo method */
    private ArrayList<int[]> positionMove = new ArrayList<>();

    /******************************************************************
     * Default constructor.  Sets standard piece positions
     *
     */
    public ChessModel() {
        board = new IChessPiece[8][8];
        player = Player.WHITE;
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Player.WHITE);
            board[1][i] = new Pawn(Player.BLACK);
        }
        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);

        board[0][0] = new Rook(Player.BLACK);
        board[0][1] = new Knight(Player.BLACK);
        board[0][2] = new Bishop(Player.BLACK);
        board[0][3] = new Queen(Player.BLACK);
        board[0][4] = new King(Player.BLACK);
        board[0][5] = new Bishop(Player.BLACK);
        board[0][6] = new Knight(Player.BLACK);
        board[0][7] = new Rook(Player.BLACK);
    }

    /******************************************************************
     * After a game ends this method is called and the board gets
     * reset to standard positions
     *
     */
    public void resetBoard(){
        pieceMove = new ArrayList<>();
        deadPieces = new ArrayList<>();
        positionMove = new ArrayList<>();
        board = new IChessPiece[8][8];
        player = Player.WHITE;
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Player.WHITE);
            board[1][i] = new Pawn(Player.BLACK);
        }
        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);

        board[0][0] = new Rook(Player.BLACK);
        board[0][1] = new Knight(Player.BLACK);
        board[0][2] = new Bishop(Player.BLACK);
        board[0][3] = new Queen(Player.BLACK);
        board[0][4] = new King(Player.BLACK);
        board[0][5] = new Bishop(Player.BLACK);
        board[0][6] = new Knight(Player.BLACK);
        board[0][7] = new Rook(Player.BLACK);
    }





    /******************************************************************
     * Establishes rules that apply to every chess piece. Returns true
     * if specified move is valid for any piece type and returns false
     * if the move is not valid.
     *
     *
     * @param move The move in question, from and to positions
     * @return True if move is valid, and false if non-valid move
     */
    public boolean isValidMove(Move move) {
        boolean valid = false;

        if (board[move.fromRow][move.fromColumn] != null)
            if (board[move.fromRow][move.fromColumn].isValidMove(move,
                    board) == true)
                return true;

        return valid;
    }

    /******************************************************************
     * Undo's the most recent turn by accessing past board position
     * arrays
     *
     */
    public void undo() {
        int arrLength = positionMove.size() - 1;
        if (arrLength >= 0) {
            int[] pos = positionMove.get(arrLength);
            if (positionMove.get(arrLength)[2] == -1) {
                if (positionMove.get(arrLength)[3] == -2) {
                    setNextPlayer();
                    setPiece(pos[0], pos[1],
                            new King(currentPlayer()));
                    board[pos[0]][pos[1] + 1] = null;
                    board[pos[0]][pos[1] + 2] = null;
                    setPiece(pos[0], pos[1] + 3,
                            new Rook(currentPlayer()));
                    setNextPlayer();
                } else {
                    setNextPlayer();
                    setPiece(pos[0], pos[1],
                            new King(currentPlayer()));
                    board[pos[0]][pos[1] - 1] = null;
                    board[pos[0]][pos[1] - 2] = null;
                    board[pos[0]][pos[1] - 3] = null;
                    setPiece(pos[0], pos[1] - 4,
                            new Rook(currentPlayer()));
                    setNextPlayer();
                }
            } else {
                setPiece(pos[0], pos[1], pieceMove.get(arrLength));
                board[pos[2]][pos[3]] = deadPieces.get(arrLength);
            }
            positionMove.remove(arrLength);
            pieceMove.remove(arrLength);
            deadPieces.remove(arrLength);
            setNextPlayer();
        }
    }

    /******************************************************************
     * When a pawn makes it to the other side of the chess board, it
     * is able to "promote" to a piece of the players choice
     *
     * @param move the move that may lead to a promotion
     * @return True if the move leads to a promotion, false if the
     * move does not result in a promotion.
     *
     */
    public boolean promote(Move move) {
        if (currentPlayer() == Player.WHITE &&
                board[move.fromRow]
                        [move.fromColumn].type().equals("Pawn")
                && move.toRow == 0)
            return true;
        if (currentPlayer() == Player.BLACK &&
                board[move.fromRow]
                        [move.fromColumn].type().equals("Pawn")
                && move.toRow == board.length - 1)
            return true;
        return false;
    }

    /******************************************************************
     * If the user tries to castle, this method will check to see if
     * all the requirements are meet to castle.  If move is able to
     * castle, the move will be made.
     *
     * @param move The move that may be trying to castle
     * @return True if castle is possible, false if castle is not
     * possible
     *
     */
    public boolean castle(Move move) {
        // trying to castle?
        if ((move.toRow != board.length - 1 && currentPlayer()
                != Player.BLACK) || (move.toRow != 0) &&
                currentPlayer() != Player.WHITE)
            return false;
        else if (move.toColumn != 2 && move.toColumn != 6)
            return false;
        // not running over any pieces
        int xDir = 1;
        int tempFromCol = move.fromColumn;
        if (move.fromColumn > move.toColumn)
            xDir = -1;
        tempFromCol += xDir;
        while (tempFromCol != 0 && tempFromCol != board.length - 1) {
            if (board[move.fromRow][tempFromCol] != null)
                return false;
            tempFromCol += xDir;
        }
        // King has not moved and is not in check
        if ((board[move.fromRow][move.fromColumn].type().equals("King")
                && !((King) board[move.fromRow]
                [move.fromColumn]).hasMoved())
                && !inCheck(currentPlayer())) {
            // rook on appropriate side has not moved
            if (xDir == -1 && board[move.fromRow][0] != null
                    && board[move.fromRow][0].type().equals("Rook")
                    && !((Rook) board[move.fromRow][0]).hasMoved()) {
                deadPieces.add(board[move.toRow][move.toColumn]);
                board[move.toRow][move.toColumn] =
                        board[move.fromRow][move.fromColumn];
                pieceMove.add(board[move.fromRow][move.fromColumn]);
                positionMove.add(new int[]{move.fromRow,
                        move.fromColumn, -1, -1});
                board[move.fromRow][move.fromColumn] = null;
                board[move.toRow][move.toColumn - xDir] =
                        new Rook(currentPlayer());
                board[move.toRow][0] = null;
                return true;
            } else if (xDir == 1 && board[move.fromRow]
                    [board.length - 1]
                    != null && board[move.fromRow]
                    [board.length - 1].type().equals("Rook")
                    && !((Rook) board[move.fromRow]
                    [board.length - 1]).hasMoved()) {
                deadPieces.add(board[move.toRow]
                        [move.toColumn + xDir]);
                board[move.toRow][move.toColumn] = board[move.fromRow]
                        [move.fromColumn];
                pieceMove.add(board[move.fromRow][move.fromColumn]);
                positionMove.add(new int[]
                        {move.fromRow, move.fromColumn, -1, -2});
                board[move.fromRow][move.fromColumn] = null;
                board[move.toRow][move.toColumn - xDir] =
                        new Rook(currentPlayer());
                board[move.toRow][board.length - 1] = null;
                return true;
            }
        }
        return false;
    }

    /******************************************************************
     * Updates the board to reflect the move input
     *
     * @param move The specified move to be made
     * @param n if n is -1, the move was a castle which requires
     *          special consideration
     */
    public void move(Move move, int n) {

        deadPieces.add(board[move.toRow][move.toColumn]);
        if (n >= 0 && currentPlayer() == Player.WHITE) {
            if (n == 0)
                board[move.toRow][move.toColumn] =
                        new Queen(Player.WHITE);
            if (n == 1)
                board[move.toRow][move.toColumn] =
                        new Bishop(Player.WHITE);
            if (n == 2)
                board[move.toRow][move.toColumn] =
                        new Knight(Player.WHITE);
            if (n == 3)
                board[move.toRow][move.toColumn] =
                        new Rook(Player.WHITE);
        } else if (n >= 0 && currentPlayer() == Player.BLACK) {
            if (n == 0)
                board[move.toRow][move.toColumn] =
                        new Queen(Player.BLACK);
            if (n == 1)
                board[move.toRow][move.toColumn] =
                        new Bishop(Player.BLACK);
            if (n == 2)
                board[move.toRow][move.toColumn] =
                        new Knight(Player.BLACK);
            if (n == 3)
                board[move.toRow][move.toColumn] =
                        new Rook(Player.BLACK);
        } else {
            board[move.toRow][move.toColumn] =
                    board[move.fromRow][move.fromColumn];
        }
        pieceMove.add(board[move.fromRow][move.fromColumn]);
        positionMove.add(new int[]{
                move.fromRow, move.fromColumn,
                move.toRow, move.toColumn});
        board[move.fromRow][move.fromColumn] = null;
    }

    /******************************************************************
     * Helper AI method so that test moves are not added to move list
     * arrays
     *
     * @param move AI's test move
     * @param temp The recreated board for each AI instance
     *
     */
    public void testMove(Move move, IChessPiece[][] temp) {
        temp[move.toRow][move.toColumn] =
                temp[move.fromRow][move.fromColumn];
        temp[move.fromRow][move.fromColumn] = null;
    }

    /******************************************************************
     * Checks if the specified player has lost their king
     *
     * @param p The player in question
     * @return True if the palyer has no king on the board, false if
     * they do still have a king
     */
    public boolean noKing(Player p ){
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null &&
                        board[row][col].type().equals("King") &&
                        board[row][col].player() == p) {
                    return false;
                }

            }
        }
        return  true;
    }
    /******************************************************************
     * Checks for checkmate after check is established.  Does so by
     * returning the possible moves for the king and seeign if any of
     * those move gets you out of check.
     *
     * @param p The player in question
     * @return true if in checkmate, false if can get out of check.
     *
     */
    public boolean isComplete(Player p){
//        ArrayList<Move> moves = new ArrayList<>();
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                if(board[row][col] != null && board[row][col].player() == p){
//                    moves.addAll(board[row][col].possibleMoves(row,col,board));
//                }
//            }
//        }
//        for(Move move: moves){
//            testMove(move, board);
//            if(!inCheck(p)){
//                undo();
//                return false;
//            }
//        }
//        undo();
//        return true;
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null &&
                        board[row][col].type().equals("King") &&
                        board[row][col].player() == p) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }

            }
        }
        ArrayList<Move> moves = board[kingRow]
                [kingCol].possibleMoves(kingRow,kingCol, board);
        for (Move move: moves) {
            move(move, -1);
            setNextPlayer();
            if(!inCheck(p)){
                undo();
                return false;
            }
        }
        undo();
        return true;
    }

    /******************************************************************
     *
     * @param  p the Player being checked
     * @return true if in check, false if not in check
     */
    public boolean inCheck(Player p) {
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null &&
                        board[row][col].type().equals("King")
                        && board[row][col].player() == p) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        for (int row = 0; row < numRows(); row++)
            for (int col = 0; col < numColumns(); col++)
                if (board[row][col] != null && board[row]
                        [col].player() != p) {
                    if (board[row][col].isValidMove(
                            new Move(row, col, kingRow, kingCol),
                            board))
                        return true;
                }
        return false;
    }

    /******************************************************************
     * Getter method for the player whose turn it is
     * @return the player whos turn it is
     */
    public Player currentPlayer() {
        return player;
    }

    /******************************************************************
     *
     * @return the number of rows on the chess board
     */
    public int numRows() {
        return 8;
    }

    /******************************************************************
     * returns the number of columns on the chee board
     * @return
     */
    public int numColumns() {
        return 8;
    }

    /******************************************************************
     * returns the chess piece at a specific position on the board
     * @param row row on the board in question
     * @param column column on the board in question
     * @return the chess piece at the row and column on the board
     */
    public IChessPiece pieceAt(int row, int column) {
        return board[row][column];
    }

    /******************************************************************
     * sets the next player after a move has been made
     */
    public void setNextPlayer() {
        player = player.next();
    }

    /******************************************************************
     * sets a brand new piece ont he board at a specified row and
     * column
     * @param row row to put the piece in
     * @param column column to put the piece in
     * @param piece what king of piece to be placed at specified
     *              position
     */
    public void setPiece(int row, int column, IChessPiece piece) {
        board[row][column] = piece;
    }

    /******************************************************************
     * Rates a potential move based on what piece will be taken by
     * the landing row and column.  Higher number if taking a more
     * valuable enemy piece.
     * @param moves the move to be rated
     * @param temp the board that is used to determine other pieces
     *             positions
     * @return Higher number is a better move, lower number is
     * a worse move
     */
    public int rateMove(Move moves, IChessPiece[][] temp) {
        if (temp[moves.toRow][moves.toColumn] == null) {
            return 0;
        } else if (temp[moves.toRow]
                [moves.toColumn].type().equals("Pawn")) {
            return 2;
        } else if ((temp[moves.toRow]
                [moves.toColumn].type().equals("Knight") ||
                temp[moves.toRow]
                        [moves.toColumn].type().equals("Bishop"))) {
            return 3;
        } else if (temp[moves.toRow]
                [moves.toColumn].type().equals("Rook")) {
            return 5;

        } else if (temp[moves.toRow]
                [moves.toColumn].type().equals("Queen")) {
            return 9;
        } else {
            return 15;
        }
    }

    /******************************************************************
     * AI uses to deep copy the board and all of its pieces
     *
     * @return copy of current chess board
     */
    public IChessPiece[][] copyBoard() {
        IChessPiece[][] temp = new IChessPiece[8][8];
        for (int row = 0; row < numRows(); row++) {
            for (int col = 0; col < numColumns(); col++) {
                if (board[row][col] != null &&
                        board[row][col].player() == Player.WHITE) {
                    if (board[row][col].type().equals("Pawn"))
                        temp[row][col] = new Pawn(Player.WHITE);
                    if (board[row][col].type().equals("Bishop"))
                        temp[row][col] = new Bishop(Player.WHITE);
                    if (board[row][col].type().equals("King"))
                        temp[row][col] = new King(Player.WHITE);
                    if (board[row][col].type().equals("Knight"))
                        temp[row][col] = new Knight(Player.WHITE);
                    if (board[row][col].type().equals("Queen"))
                        temp[row][col] = new Queen(Player.WHITE);
                    if (board[row][col].type().equals("Rook"))
                        temp[row][col] = new Rook(Player.WHITE);
                } else if (board[row][col] != null &&
                        board[row][col].player() == Player.BLACK) {
                    if (board[row][col].type().equals("Pawn"))
                        temp[row][col] = new Pawn(Player.BLACK);
                    if (board[row][col].type().equals("Bishop"))
                        temp[row][col] = new Bishop(Player.BLACK);
                    if (board[row][col].type().equals("King"))
                        temp[row][col] = new King(Player.BLACK);
                    if (board[row][col].type().equals("Knight"))
                        temp[row][col] = new Knight(Player.BLACK);
                    if (board[row][col].type().equals("Queen"))
                        temp[row][col] = new Queen(Player.BLACK);
                    if (board[row][col].type().equals("Rook"))
                        temp[row][col] = new Rook(Player.BLACK);
                }
            }
        }
        return temp;
    }

    /******************************************************************
     * AI to play against the user
     */
    public void AI() {
        int bestMoveScore;
        Move bestMove = new Move(0, 0, 5, 5);
        ArrayList<Move> roundOneMoves = new ArrayList<>();
        ArrayList<Integer> roundOneMoveRating = new ArrayList<>();
        ArrayList<Integer> bestCounter = new ArrayList<>();

        ArrayList<Integer> roundTwoMoveRating = new ArrayList<>();
        // gets all AI's possible moves
        for (int row = 0; row < numRows(); row++) {
            for (int col = 0; col < numColumns(); col++) {
                if (board[row][col] != null &&
                        board[row][col].player() == currentPlayer()) {
                    roundOneMoves.addAll(board[row]
                            [col].possibleMoves(row, col, board));
                }
            }
        }
        // rate AI moves
        for (int i = 0; i < roundOneMoves.size(); i++) {
            int max = -1;
            ArrayList<Move> roundTwoMoves = new ArrayList<>();
            roundOneMoveRating.add(rateMove(roundOneMoves.get(i),
                    board));
            move(roundOneMoves.get(i), -1);
            setNextPlayer();
            for (int row = 0; row < numRows(); row++) {
                for (int col = 0; col < numColumns(); col++) {
                    if (board[row][col] != null &&
                            board[row][col].player() ==
                                    currentPlayer()) {
                        roundTwoMoves.addAll(board[row]
                                [col].possibleMoves(row, col, board));
                    }
                }
            }
            for(int h = 0; h < roundTwoMoves.size();h++){
                int rated = rateMove(roundTwoMoves.get(h), board);
                if (rated > max){
                    max = rated;
                }
            }
            bestCounter.add(max);
            undo();
        }
        bestMoveScore = roundOneMoveRating.get(0) - bestCounter.get(0);
        for(int p = 0; p < roundOneMoveRating.size();p++){
            System.out.print(roundOneMoveRating.get(p) -
                    bestCounter.get(p));
            System.out.print("_");
            if(roundOneMoveRating.get(p) - bestCounter.get(p) >=
                    bestMoveScore){
                bestMoveScore = roundOneMoveRating.get(p) -
                        bestCounter.get(p);
                bestMove = roundOneMoves.get(p);
            }
        }
        System.out.println();
        System.out.println(bestMoveScore);
        move(bestMove,-1);
   }
}