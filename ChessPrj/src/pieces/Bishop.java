package pieces;

import chess.Player;
import chess.Move;

import java.util.ArrayList;

/******************************************************************
 * Bishop class that implements all functionality of a
 * bishop chess piece.
 *
 * @author Kyle Russcher
 */
public class Bishop extends ChessPiece {

    /******************************************************************
     * Constructor that calls the ChessPiece constructor
     * @param player Player that owns the piece
     */
    public Bishop(Player player) {
        super(player);
    }

    /******************************************************************
     * returns that the piece is a bishop. Used in a polymorphic way
     * @return "Bishop"
     */
    public String type() {
        return "Bishop";
    }


    /******************************************************************
     * Helper method for valid move to verify that a move has not
     * jumped over and pieces
     * @param x initial column
     * @param x2 ending column row position
     * @param y initial row
     * @param y2 ending row poition
     * @param board chess board containing all other piece positions
     * @return true if the move does not pass over any pieces false
     * otherwise
     */
    private boolean noJumps(int x,int x2, int y,
                            int y2, IChessPiece[][] board){
        int dirX;
        int dirY;
        if(y > y2 && x < x2){  // Q1
            dirX = 1;
            dirY = -1;
        } else if(y > y2 && x > x2){  // Q2
            dirX = -1;
            dirY = -1;
        } else if (y < y2 && x > x2){  // Q3
            dirX = -1;
            dirY = 1;
        } else {  // Q4
            dirX = 1;
            dirY = 1;
        }
        x += dirX;
        y += dirY;
        while(x != x2 && y != y2){

            if(board[y][x] != null){
                return false;
            } else {
                x += dirX;
                y += dirY;
            }
        }
        return true;
    }

    /******************************************************************
     * Determiens if the move is valid for a bishop
     * @param move the move in question for a bishop
     * @param board the board that is being player
     * @return true if move is valid for a bishop, false otherwise
     */
    public boolean isValidMove(Move move, IChessPiece[][] board) {
        if(!super.isValidMove(move, board))
            return false;
        int x = move.fromColumn;
        int x2 = move.toColumn;
        int y = move.fromRow;
        int y2 = move.toRow;

        if((Math.abs(x - x2) == Math.abs(y - y2))
                && noJumps(x,x2,y,y2, board)){
            return true;
        }

        return false;

    }

    /******************************************************************
     * Returns a list of all possible move that the bishop
     * can make given the board and other piece positions
     * @param currentRow row where the piece in question is located
     * @param currentCol row where the piece in question is located
     * @param board board with other piece information
     * @return an array of all possible move of the bishop in question
     */
    public ArrayList<Move> possibleMoves(int currentRow,
                                         int currentCol,
                                         IChessPiece[][] board) {
        ArrayList<Move> moves = new ArrayList<>();

        int tempX = currentCol;
        int tempY = currentRow;
        // up / left
        while(tempX != 0 && tempY != 0){
            tempX--;
            tempY--;
            Move checkMove = new Move(currentRow,
                    currentCol, tempY, tempX);
            if(isValidMove(checkMove, board)){
                moves.add(checkMove);
            }
        }
        tempX = currentCol;
        tempY = currentRow;
        // up / right
        while(tempX != board.length && tempY != 0){
            tempX++;
            tempY--;
            Move checkMove = new Move(currentRow,
                    currentCol, tempY, tempX);
            if(isValidMove(checkMove, board)){
                moves.add(checkMove);
            }
        }
        tempX = currentCol;
        tempY = currentRow;
        // down / left
        while(tempX != 0 && tempY != board.length){
            tempX--;
            tempY++;
            Move checkMove = new Move(currentRow,
                    currentCol, tempY, tempX);
            if(isValidMove(checkMove, board)){
                moves.add(checkMove);
            }
        }
        tempX = currentCol;
        tempY = currentRow;
        // down / right
        while(tempX != board.length && tempY
                != board.length){
            tempX++;
            tempY++;
            Move checkMove = new Move(currentRow,
                    currentCol, tempY, tempX);
            if(isValidMove(checkMove, board)){
                moves.add(checkMove);
            }
        }

        return moves;
    }
}

