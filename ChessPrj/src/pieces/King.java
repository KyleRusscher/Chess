package pieces;
import chess.Player;
import chess.Move;
import java.util.ArrayList;
/******************************************************************
 * King class that implements all functionality of a
 * King chess piece.
 *
 * @author Kyle Russcher
 */
public class King extends ChessPiece {
    /* True if king has moved at least one time */
    boolean hasMoved = false;

    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public King(Player player) {
        super(player);
    }


    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "King"
     */
    public String type() {
        return "King";
    }

    /******************************************************************
     * Returns a list of all possible move that the King
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the King in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol,
                                         IChessPiece[][] board){
        ArrayList<Move> moves = new ArrayList<>();
        int[][] changes = {{1,1},{-1,-1},{1,0},
                {-1,0},{0,1},{0,-1},{1,-1},{-1,1}};

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

    /******************************************************************
     * Returns if the King has move at least one time
     * @return true if king has moved, false otherwise
     */
    public boolean hasMoved(){
        return hasMoved;
    }

    /******************************************************************
     * Determiens if the move is valid for a King
     * @param move the move in question for a King
     * @param board the board that is being player
     * @return true if move is valid for a King, false otherwise
     */
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if (!super.isValidMove(move, board)){
            return false;
        }
        if(Math.abs(move.fromColumn - move.toColumn) <=1 &&
                Math.abs(move.fromRow - move.toRow) <=1){
            hasMoved = true;
            return true;
        }

        return false;
    }
}