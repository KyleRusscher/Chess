package chess;

import org.junit.Test;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import pieces.King;

public class ChessModelTest extends TestCase {
    ChessModel test = new ChessModel();

    public void testResetBoard() {
        test.move(new Move(0,0,5,5), -1);
        test.resetBoard();
        assertEquals("Rook", test.pieceAt(0,0).type());
    }

    public void testIsValidMoveBlackPawn() {
        assertTrue(test.isValidMove(new Move(1,1,2,1)));
        test.move(new Move(1,1,2,1), -1);
        assertEquals("Pawn", test.pieceAt(2,1).type());
    }

    public void testIsValidMoveBlackRook() {
        test.move(new Move(1,0,4,0), -1);
        assertTrue(test.isValidMove(new Move(0,0,1,0)));
        test.move(new Move(0,0,1,0), -1);
        assertEquals("Rook", test.pieceAt(1,0).type());
    }

    public void testIsValidMoveBlackKnight() {
        assertTrue(test.isValidMove(new Move(0,1,2,0)));
        test.move(new Move(0,1,2,0),-1);
        assertEquals("Knight", test.pieceAt(2,0).type());
    }

    public void testIsValidMoveBlackBishop() {
        test.move(new Move(1,1,3,0), -1);
        assertTrue(test.isValidMove(new Move(0,2,1,1)));
        test.move(new Move(0,2,1,1), -1);
        assertEquals("Bishop", test.pieceAt(1,1).type());
    }

    public void testIsValidMoveBlackQueen() {
        test.move(new Move(1,2,3,1), -1);
        assertTrue(test.isValidMove(new Move(0,3,1,2)));
        test.move(new Move(0,3,1,2), -1);
        assertEquals("Queen", test.pieceAt(1,2).type());
    }

    public void testIsValidMoveBlackKing() {
        test.move(new Move(1,3,3,3), -1);
        assertTrue(test.isValidMove(new Move(0,4,1,3)));
        test.move(new Move(0,4,1,3), -1);
        assertEquals("King", test.pieceAt(1,3).type());
    }

    // white

    public void testIsValidMoveWhitePawn() {
        assertTrue(test.isValidMove(new Move(6,1,5,1)));
        test.move(new Move(6,1,5,1), -1);
        assertEquals("Pawn", test.pieceAt(5,1).type());
    }

    public void testIsValidMoveWhiteRook() {
        test.move(new Move(6,0,5,0), -1);
        assertTrue(test.isValidMove(new Move(7,0,6,0)));
        test.move(new Move(7,0,6,0), -1);
        assertEquals("Rook", test.pieceAt(6,0).type());
    }

    public void testIsValidMoveWhiteKnight() {
        assertTrue(test.isValidMove(new Move(7,1,5,0)));
        test.move(new Move(7,1,5,0),-1);
        assertEquals("Knight", test.pieceAt(5,0).type());
    }

    public void testIsValidMoveWhiteBishop() {
        test.move(new Move(6,1,5,0), -1);
        assertTrue(test.isValidMove(new Move(7,2,6,1)));
        test.move(new Move(7,2,6,1), -1);
        assertEquals("Bishop", test.pieceAt(6,1).type());
    }

    public void testIsValidMoveWhiteQueen() {
        test.move(new Move(6,2,4,2), -1);
        assertTrue(test.isValidMove(new Move(7,3,6,2)));
        test.move(new Move(7,3,6,2), -1);
        assertEquals("Queen", test.pieceAt(6,2).type());
    }

    public void testIsValidMoveWhiteKing() {
        test.move(new Move(6,3,5,3), -1);
        assertTrue(test.isValidMove(new Move(7,4,6,3)));
        test.move(new Move(7,4,6,3), -1);
        assertEquals("King", test.pieceAt(6,3).type());
    }


    public void testUndo() {
        String beforeMove = test.pieceAt(6,3).type();
        test.move(new Move(6,3,5,3), -1);
        test.undo();
        assertEquals(beforeMove, test.pieceAt(6,3).type());
    }

    public void testPromote() {
        assertTrue(test.promote(new Move(6,3,0,3)));
    }

    public void testCastle() {
        test.move(new Move(7,1,0,0), -1);
        test.move(new Move(7,2,0,0), -1);
        test.move(new Move(7,3,0,0), -1);
        assertTrue(test.castle(new Move(7,4,7,2)));
    }


    public void testNoKing() {
        test.move(new Move(0,0,7,4), -1);
        assertTrue(test.noKing(Player.WHITE));
    }

    public void testIsCompleteAndInCheck() {
        test.move(new Move(6,3,0,0),-1);
        test.move(new Move(0,3,3,0), -1);
        assertTrue(test.isComplete(Player.WHITE));
        assertTrue(test.inCheck(Player.WHITE));
    }

    public void testSetPiece() {
        test.setPiece(5,5,new King(Player.WHITE));
        assertEquals("King", test.pieceAt(5,5).type());
    }

    public void testMoveToString() {
        assertEquals("Move [fromRow=1, fromColumn=1, toRow=1, toColumn=1]",new Move(1,1,1,1).toString());
    }

}