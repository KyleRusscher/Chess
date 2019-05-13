package pieces;

import chess.Move;
import chess.Player;

import java.util.ArrayList;

/******************************************************************
 * Class that holds info that all chess pieces will need
 *
 * @author Kyle Russcher
 */
public abstract class ChessPiece implements IChessPiece {
    /* who owns the piece. */
    private Player owner;

    /******************************************************************
     * Constructor for the chess piece and sets who owns the piece
     * @param player
     */
    protected ChessPiece(Player player) {
        this.owner = player;
    }

    /******************************************************************
     * overridden type method per piece
     */
    public abstract String type();

    /******************************************************************
     * Returns the player who owns the piece
     * @return player who owns piece
     */
    public Player player() {
        return owner;
    }

    /******************************************************************
     * overridden possible moves method per piece
     */
    public abstract ArrayList<Move> possibleMoves(int currentRow,
                                                  int currentCol,
                                                  IChessPiece[][] board);

    /******************************************************************
     * Sets rules tha govern every piece's valid moves
     * @param move  a {@link W18project3.Move} object describing the move to be made.
     * @param board the {@link W18project3.IChessBoard} in which this piece resides.
     * @return
     */
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if (((move.fromRow == move.toRow) && (move.fromColumn ==
                move.toColumn))) {
            return false;
        }
        if(move.toColumn < 0 || move.toRow < 0 || move.toColumn >
                board.length -1 || move.toRow > board.length - 1)
            return false;
        if (board[move.toRow][move.toColumn] != null &&
                board[move.toRow][move.toColumn].player() == player()){
            return false;
        }
        return true;
    }
}