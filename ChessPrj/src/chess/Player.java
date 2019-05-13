package chess;

/******************************************************************
 * Player class that holds the current player color
 *
 * @author Kyle Russcher
 */
public enum Player {

    /* What color the current player is */
    BLACK, WHITE;

    /******************************************************************
     * Return the {@code Player} whose turn is next.
     *
     * @return the {@code Player} whose turn is next
     */
    public Player next() {
        if (this == BLACK)
            return WHITE;
        else
            return BLACK;
    }
}