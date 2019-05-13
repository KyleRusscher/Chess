package pieces;

import chess.Player;
import chess.Move;

import java.util.ArrayList;
/******************************************************************
 * Knight class that implements all functionality of a
 * Knight chess piece.
 *
 * @author Kyle Russcher
 */
public class Knight extends ChessPiece {
    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public Knight(Player player) {
        super(player);
    }
    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "Knight"
     */
    public String type() {
        return "Knight";
    }

    /******************************************************************
     * Determiens if the move is valid for a Knight
     * @param move the move in question for a Knight
     * @param board the board that is beiing player
     * @return true if move is valid for a Knight, false otherwise
     */
    public boolean isValidMove(Move move, IChessPiece[][] board){
        if(!super.isValidMove(move, board))
            return false;
        int x = move.fromColumn;
        int x2 = move.toColumn;
        int y = move.fromRow;
        int y2 = move.toRow;

        if(Math.abs(x - x2) == 2 && Math.abs(y - y2) == 1)
            return true;
        else if(Math.abs(x - x2) == 1 && Math.abs(y - y2) == 2)
            return true;
        return false;

    }
    /******************************************************************
     * Returns a list of all possible move that the Knight
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the Knight in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol,
                                         IChessPiece[][] board) {
        ArrayList<Move> moves = new ArrayList<>();
        int[][] changes = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},
                {1,-2},{-1,2},{-1,-2}};
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