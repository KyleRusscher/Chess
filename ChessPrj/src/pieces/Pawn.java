package pieces;

import chess.Player;
import chess.Move;
import java.util.ArrayList;
/******************************************************************
 * Pawn class that implements all functionality of a
 * Pawn chess piece.
 *
 * @author Kyle Russcher
 */
public class Pawn extends ChessPiece{
    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public Pawn(Player player) {
        super(player);
    }

    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "Pawn"
     */
    public String type() {
        return "Pawn";
    }

    /******************************************************************
     * Determiens if the move is valid for a Pawn
     * @param move the move in question for a Pawn
     * @param board the board that is being player
     * @return true if move is valid for a Pawn, false otherwise
     */
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if (!super.isValidMove(move, board)) {
            return false;
        }
        if (player() == Player.WHITE) {
            // if there is an enemy piece in front of you
            if ((board[move.toRow][move.toColumn] != null &&
                    board[move.toRow][move.toColumn].player() !=
                            player())
                    && (move.fromRow - move.toRow == 1 &&
                    Math.abs(move.fromColumn - move.toColumn) ==
                            1)) {
                return true;
            } else if((move.fromRow == 6 && board[move.toRow]
                    [move.toColumn] == null) && Math.abs(move.toColumn
                    - move.fromColumn) == 0 && move.toRow - move.fromRow
                    == -2) {
                return true;
            } else if (board[move.toRow][move.toColumn] == null &&
                    Math.abs(move.toColumn - move.fromColumn) == 0
                    && move.toRow - move.fromRow == -1) {
                return true;
            }
        } else {
            if (((board[move.toRow][move.toColumn] != null &&
                    board[move.toRow][move.toColumn].player()
                            != player())
                    && (move.fromRow - move.toRow == -1 &&
                    Math.abs(move.fromColumn - move.toColumn)
                            == 1))) {
                return true;
            } else if((move.fromRow == 1 && board[move.toRow]
                    [move.toColumn] == null) && Math.abs(move.toColumn
                    - move.fromColumn) == 0 && move.toRow -
                    move.fromRow == 2) {
                return true;
            }else if (board[move.toRow][move.toColumn] == null
                    && Math.abs(move.toColumn - move.fromColumn)
                    == 0 && move.toRow - move.fromRow == 1) {
                return true;
            }
        }
        return false;
    }

    /******************************************************************
     * Returns a list of all possible move that the Pawn
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the Pawn in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol,
                                         IChessPiece[][] board){
        ArrayList<Move> moves = new ArrayList<>();
        int[][] changes = {{1,1},{-1,-1},{1,0},{-1,0},
                {1,-1},{-1,1}};

        for(int i = 0; i < changes.length; i++){
            Move checkMove = new Move(currentRow, currentCol,
                    currentRow + changes[i][0],
                    currentCol + changes[i][1]);
            if(isValidMove(checkMove, board)){
                moves.add(checkMove);
            }
        }
        return moves;
    }

}
