package chess;

import java.awt.Dimension;

import javax.swing.JFrame;

/******************************************************************
 * class for the GUI of a chess game
 * @author Kyle Russcher
 */
public class ChessGUI {

    /******************************************************************
     * Main method to start the program and build the GUI.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessPanel panel = new ChessPanel();
        frame.getContentPane().add(panel);

        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 650));
        frame.pack();
        frame.setVisible(true);
    }
}
