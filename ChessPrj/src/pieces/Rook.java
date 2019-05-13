package pieces;
import chess.Player;
import chess.Move;
import java.util.ArrayList;
/******************************************************************
 * Rook class that implements all functionality of a
 * Rook chess piece.
 *
 * @author Kyle Russcher
 */
public class Rook extends ChessPiece {
    /* true if rook has moved at least one time*/
    private boolean hasMoved = false;

    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public Rook(Player player) {
        super(player);
    }
    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "Rook"
     */
    public String type() {
        return "Rook";
    }

    /******************************************************************
     * Returns if the Rook has move at least one time
     * @return true if Rook has moved, false otherwise
     */
    public boolean hasMoved(){
        return hasMoved;
    }

    /******************************************************************
     * Helper method for valid move to verify that a move has not
     * jumped over and pieces
     * @param fromCol initial column
     * @param toCol ending column row position
     * @param fromRow initial row
     * @param toRow ending row position
     * @param board chess board containing all other piece positions
     * @return true if the move does not pass over any pieces false
     * otherwise
     */
    public boolean noJumps(int fromCol, int toCol,
                           int fromRow, int toRow,
                           IChessPiece[][] board){
        int x, y;
        if (fromCol > toCol) {
            x = -1;
            y = 0;
        } else if (fromCol < toCol){
            x = 1;
            y = 0;
        } else if (fromRow < toRow){
            x = 0;
            y = 1;
        } else{
            x = 0;
            y = -1;
        }
        fromCol += x;
        fromRow += y;

        while (toCol != fromCol || toRow != fromRow){
            if (board[fromRow][fromCol] != null){
                return false;
            }
            fromCol += x;
            fromRow += y;
        }
        return true;
    }

    /******************************************************************
     * Determiens if the move is valid for a Rook
     * @param move the move in question for a Rook
     * @param board the board that is being player
     * @return true if move is valid for a Rook, false otherwise
     */
    public boolean isValidMove(Move move,
                               IChessPiece[][] board) {
        if (!super.isValidMove(move, board)) {
            return false;
        }
        if ((move.toRow - move.fromRow == 0 ||
                move.fromColumn - move.toColumn == 0)
                && noJumps(move.fromColumn, move.toColumn,
                move.fromRow, move.toRow, board)){
            hasMoved = true;
            return true;
        }
        return false;

    }

    /******************************************************************
     * Returns a list of all possible move that the Rook
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the Rook in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol,
                                         IChessPiece[][] board) {
        ArrayList<Move> moves = new ArrayList<>();
        //check all horizontal and vertical moves
        for(int i = 0; i < board.length; i++){
            Move checkMoveHorizontal = new Move(currentRow,
                    currentCol, currentRow, i);
            Move checkMoveVertical = new Move(currentRow,
                    currentCol, i, currentCol);
            if(isValidMove(checkMoveHorizontal, board)){
                moves.add(checkMoveHorizontal);
            }
            if(isValidMove(checkMoveVertical, board)){
                moves.add(checkMoveVertical);
            }
        }


        return moves;
    }

}