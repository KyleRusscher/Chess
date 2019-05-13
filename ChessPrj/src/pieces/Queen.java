package pieces;
import chess.Player;
import chess.Move;
import java.util.ArrayList;
/******************************************************************
 * Queen class that implements all functionality of a
 * Queen chess piece.
 *
 * @author Kyle Russcher
 */
public class Queen extends ChessPiece {
    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public Queen(Player player) {
        super(player);

    }

    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "Queen"
     */
    public String type() {
        return "Queen";

    }

    /******************************************************************
     * Determiens if the move is valid for a Queen
     * @param move the move in question for a Queen
     * @param board the board that is being player
     * @return true if move is valid for a Queen, false otherwise
     */
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if(!super.isValidMove(move, board))
            return false;

        Bishop move1 = new Bishop(board[move.fromRow]
                [move.fromColumn].player());
        Rook move2 = new Rook(board[move.fromRow]
                [move.fromColumn].player());
        return (move1.isValidMove(move, board) ||
                move2.isValidMove(move, board));
    }
    /******************************************************************
     * Returns a list of all possible move that the Queen
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the Queen in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol, IChessPiece[][] board) {
        ArrayList<Move> moves = new ArrayList<>();
        Rook tempRook = new Rook(board[currentRow]
                [currentCol].player());
        Bishop tempBishop = new Bishop(board[currentRow]
                [currentCol].player());

        moves.addAll(tempRook.possibleMoves(currentRow,
                currentCol,board));
        moves.addAll(tempBishop.possibleMoves(currentRow,
                currentCol,board));
        return moves;
    }
}