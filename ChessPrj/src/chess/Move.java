package chess;

/******************************************************************
 * Class to move pieces to and from
 */
public class Move {
    /* holds original position and destination position of the
        move */
    public int fromRow, fromColumn, toRow, toColumn;

    /**
     * default move constructor
     *
     * @author Kyle Russcher
     */
//    public Move() {
//    }

    /******************************************************************
     * Stores to and from move positions
     * @param fromRow row that the move originates
     * @param fromColumn column that the move originates
     * @param toRow row that the move intends to go to
     * @param toColumn column that the move intends to go to
     */
    public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;

    }


    /******************************************************************
     * To string method to easily see what happened on a move
     */
    public String toString() {
        return "Move [fromRow=" + fromRow + ", fromColumn=" +
                fromColumn + ", toRow=" + toRow + ", toColumn="
                + toColumn
                + "]";
    }


}