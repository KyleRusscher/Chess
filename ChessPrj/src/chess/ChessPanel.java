package chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/******************************************************************
 * Panel for the chess game
 */
public class ChessPanel extends JPanel {

    /* holds the buttons for each piece on the board */
    private JButton[][] board;

    /* models the chess game.  Holds info about possible
        actions to be made in chess match */
    private ChessModel model;


    JButton undo = new JButton("undo");

    /* image icon for white rook */
    private ImageIcon wRook;

    /* image icon for white bishop */
    private ImageIcon wBishop;

    /* image icon for white queen*/
    private ImageIcon wQueen;

    /* image icon for white king*/
    private ImageIcon wKing;

    /* image icon for white pawn */
    private ImageIcon wPawn;

    /* image icon for white knight */
    private ImageIcon wKnight;

    /* image icon for black rook*/
    private ImageIcon bRook;

    /* image icon for black bishop*/
    private ImageIcon bBishop;

    /* image icon for black queen */
    private ImageIcon bQueen;

    /* image icon for black king */
    private ImageIcon bKing;

    /* image icon for black pawn*/
    private ImageIcon bPawn;

    /* image icon for black knight */
    private ImageIcon bKnight;

    /* true if first click on board has been made */
    private boolean firstTurnFlag;

    /* original row of piece */
    private int fromRow;

    /* destination row of piece */
    private int toRow;

    /* original column of piece */
    private int fromCol;

    /* destination column of piece */
    private int toCol;

    /* Holds functionality of buttons */
    private listener listener;

    /******************************************************************
     * Constructor for the chess panel. Uses chess model to place
     * pieces in correct location
     *
     * @author Kyle Russcher
     */
    public ChessPanel() {
        model = new ChessModel();
        board = new JButton[model.numRows()][model.numColumns()];
        listener = new listener();
        undo.addActionListener(listener);
        createIcons();

        JPanel boardpanel = new JPanel();
        JPanel buttonpanel = new JPanel();
        boardpanel.setLayout(new GridLayout(model.numRows(),
                model.numColumns(), 1, 1));

        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {
                if (model.pieceAt(r, c) == null) {
                    board[r][c] = new JButton("", null);
                    board[r][c].addActionListener(listener);
                } else if (model.pieceAt(r, c).player() == Player.WHITE)
                    placeWhitePieces(r, c);
                else if (model.pieceAt(r, c).player() == Player.BLACK)
                    placeBlackPieces(r,c);
                setBackGroundColor(r, c);
                boardpanel.add(board[r][c]);
            }
        }
        buttonpanel.add(undo);
        add(boardpanel, BorderLayout.WEST);
        boardpanel.setPreferredSize(new Dimension(600, 600));
        add(buttonpanel);
        firstTurnFlag = true;
    }

    /******************************************************************
     * Alernates the background color of each square on a chess board
     * @param r square row
     * @param c square column
     */
    private void setBackGroundColor(int r, int c) {
        if ((c % 2 == 1 && r % 2 == 0) || (c % 2 == 0 && r % 2 == 1)) {
            board[r][c].setBackground(Color.LIGHT_GRAY);
        } else if ((c % 2 == 0 && r % 2 == 0) ||
                (c % 2 == 1 && r % 2 == 1)) {
            board[r][c].setBackground(Color.WHITE);
        }
    }

    /******************************************************************
     * Places white pieces on the correct square and adds listener
     * @param r row to place white piece
     * @param c column to place the white piece
     */
    private void placeWhitePieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, wPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, wRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, wKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, wBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, wQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, wKing);
            board[r][c].addActionListener(listener);
        }
    }

    /******************************************************************
     * Places black pieces on the correct square and adds listener
     * @param r row to place black piece
     * @param c column to place the black piece
     */
    private void placeBlackPieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, bPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, bRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, bKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, bBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, bQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, bKing);
            board[r][c].addActionListener(listener);
        }
    }

    /******************************************************************
     * Adds images to the chess positions
     */
    private void createIcons() {
        // Sets the Image for white player pieces
        wRook = new ImageIcon("./src/images/wRook.png");
        wBishop = new ImageIcon("./src/images/wBishop.png");
        wQueen = new ImageIcon("./src/images/wQueen.png");
        wKing = new ImageIcon("./src/images/wKing.png");
        wPawn = new ImageIcon("./src/images/wPawn.png");
        wKnight = new ImageIcon("./src/images/wKnight.png");

        bRook = new ImageIcon("./src/images/bRook.png");
        bBishop = new ImageIcon("./src/images/bBishop.png");
        bQueen = new ImageIcon("./src/images/bQueen.png");
        bKing = new ImageIcon("./src/images/bKing.png");
        bPawn = new ImageIcon("./src/images/bPawn.png");
        bKnight = new ImageIcon("./src/images/bKnight.png");
    }

    /******************************************************************
     * Updates the board based on the model piece positions
     */
    private void displayBoard() {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++)
                if (model.pieceAt(r, c) == null)
                    board[r][c].setIcon(null);
                else
                if (model.pieceAt(r, c).player() == Player.WHITE) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(wPawn);

                    if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(wRook);

                    if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(wKnight);

                    if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(wBishop);

                    if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(wQueen);

                    if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(wKing);

                } else if (model.pieceAt(r, c).player() ==
                        Player.BLACK) {
                if (model.pieceAt(r, c).type().equals("Pawn"))
                    board[r][c].setIcon(bPawn);

                if (model.pieceAt(r, c).type().equals("Rook"))
                    board[r][c].setIcon(bRook);

                if (model.pieceAt(r, c).type().equals("Knight"))
                    board[r][c].setIcon(bKnight);

                if (model.pieceAt(r, c).type().equals("Bishop"))
                    board[r][c].setIcon(bBishop);

                if (model.pieceAt(r, c).type().equals("Queen"))
                    board[r][c].setIcon(bQueen);

                if (model.pieceAt(r, c).type().equals("King"))
                    board[r][c].setIcon(bKing);

            }
        }

        repaint();

    }

    /******************************************************************
     * Adds click functionality to each square on the board
     */
    private class listener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if(undo == event.getSource()){
                model.undo();
                if(model.AI){
                    model.undo();
                }
                displayBoard();
            }
            for (int r = 0; r < model.numRows(); r++)
                for (int c = 0; c < model.numColumns(); c++)
                    if (board[r][c] == event.getSource())
                        if (firstTurnFlag == true && model.pieceAt(r,c)
                                != null && model.currentPlayer() ==
                                model.pieceAt(r,c).player()) {
                            fromRow = r;
                            fromCol = c;
                            firstTurnFlag = false;
                        } else if(firstTurnFlag == false) {
                            toRow = r;
                            toCol = c;
                            firstTurnFlag = true;
                            Move m = new Move(fromRow,
                                    fromCol, toRow, toCol);
                            if(model.castle(m)) {
                                model.setNextPlayer();
                                displayBoard();
                                model.AI();
                                model.AI = true;
                                model.setNextPlayer();
                                displayBoard();
                            }
                            if ((model.isValidMove(m)) == true) {
                                int n = -1;
                                if(model.promote(m)) {
                                    Object[] options =
                                            {"Queen", "Bishop",
                                                    "Knight", "Rook"};
                                    n = JOptionPane.showOptionDialog(
                                            null,
                                            "What piece to you want " +
                                                    "to promote pawn to?",
                                            "Promotion",
                                            JOptionPane.YES_NO_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            options,
                                            options[2]);

                                }
                                model.move(m, n);
                                model.setNextPlayer();
                                displayBoard();
                                model.AI();
                                model.AI = true;
                                model.setNextPlayer();
                                displayBoard();
                                if(model.noKing(model.currentPlayer())){
                                    JOptionPane.showMessageDialog(
                                            null,
                                            model.currentPlayer() +
                                                    " has lost their" +
                                                    " king. Game over");
                                    model.resetBoard();
                                    displayBoard();
                                    return;
                                }
                                if(model.inCheck(model.currentPlayer())){
//                                    if(model.isComplete(
//                                            model.currentPlayer())){
//                                        System.out.print("mated");
//                                        JOptionPane.showMessageDialog(
//                                                null,
//                                                "Game over. " +
//                                                        model.currentPlayer()
//                                                        + " has lost");
//                                        model.resetBoard();
//                                        displayBoard();
//                                        return;
//                                    }
                                    System.out.print("checked");
                                    JOptionPane.showMessageDialog(
                                            null,
                                            model.currentPlayer()
                                                    + " is in check");
                                }

                            }
                        }
        }
    }
}