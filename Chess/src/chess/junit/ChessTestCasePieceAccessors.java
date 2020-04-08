package chess.junit;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Arrays; 

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;





import chess.model.AbstractPiece;
import chess.model.Bishop; 
import chess.model.King;
import chess.model.Knight;
import chess.model.Pawn;
import chess.model.PieceColor;
import chess.model.PieceName;
import chess.model.Queen;
import chess.model.Rook;

/**
 * Junit Test Case 
 * @author Xiang
 * testing chess board piece accessors
 */
@RunWith(Parameterized.class)
public class ChessTestCasePieceAccessors {


	@Parameters(name = "InputPrarmName")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ 0, 0 }
		});
	}

	AbstractPiece pBishop; 
	AbstractPiece pKing; 
	AbstractPiece pKnight; 
	AbstractPiece pPawn; 
	AbstractPiece pQueen; 
	AbstractPiece pRook;  

	int id = 0; 
	Point posInit = new Point(0, 0);
	Point pos = new Point(0, 0);
	boolean firstMove = true;
	boolean visible = true;
	int iconLength = 80;
	boolean isPromote = true;

	/**
	 * initialize all possible piece object
	 * @param input - unused
	 * @param expected - unused
	 */
	public ChessTestCasePieceAccessors(int input, int expected){



		pBishop = (new Bishop(id, posInit, pos, visible, firstMove, iconLength, isPromote)); 

		pKing = (new King(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pKnight = (new Knight(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pPawn = (new Pawn(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pQueen = (new Queen(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pRook = (new Rook(id++, posInit, pos, visible, firstMove, iconLength, isPromote));


	}


	/**
	 * testing the getter and setters from the piece class instances
	 */
	@Test
	public void test() { 
		System.out.println("Am i a Rook?");
		assertEquals(pRook.getName(), PieceName.ROOK); 
		System.out.println("Am i a Knight?");
		assertEquals(pKnight.getName(), PieceName.KNIGHT);

		System.out.println("Am i a Pawn?");
		assertEquals(pPawn.getName(), PieceName.PAWN); 
		System.out.println("Am i a King?");
		assertEquals(pKing.getName(), PieceName.KING);


		System.out.println("Am i a Queen?");
		assertEquals(pQueen.getName(), PieceName.QUEEN); 
		System.out.println("Am i a Knight?");
		assertEquals(pBishop.getName(), PieceName.BISHOP);

		System.out.println("I'm not a Bishop?");
		pBishop.setName(PieceName.KING);
		assertNotEquals(pBishop.getName(), PieceName.BISHOP);


		System.out.println("ClassContaining specific piece?");
		assertTrue("does the class contain the piece?", AbstractPiece.getPieces().contains(pBishop));

		System.out.println("ClassNotContaining specfic piece because it was a promotion piece");
		AbstractPiece pPiece = new King(id, posInit, pos, visible, firstMove, iconLength, false);
		assertFalse("the class does not contain", AbstractPiece.getPieces().contains(pPiece));


		System.out.println("Check piece move-specific accessors");
		assertTrue(pPiece.isFirstMove());
		pPiece.setFirstMove(false);
		assertFalse(pPiece.isFirstMove());


		System.out.println("Check piece visibility accessors");
		assertTrue(pPiece.isVisible());
		pPiece.setVisible(false);
		assertFalse(pPiece.isVisible());


		System.out.println("Check piece position accessors");
		assertTrue(pPiece.getPosition().equals(pos));
		pPiece.setPosition(new Point(pos.x+1, pos.y+1));
		assertFalse(pPiece.getPosition().equals(pos));

		System.out.println("Check piece column row accessors");
		assertTrue(pPiece.getColumnRow().equals(posInit));
		pPiece.setColumnRow(new Point(posInit.x+1, posInit.y+1));
		assertFalse(pPiece.getColumnRow().equals(posInit));


		System.out.println("There is a blackKing and also whiteKing");
		assertNotNull(pKing);
		assertNotEquals(pKing.getColor(), PieceColor.BLACK);
		pPiece.setColor(PieceColor.WHITE); 
		pKing.setColor(PieceColor.BLACK);
		assertNotNull(pKing.getColor().equals(PieceColor.BLACK));
		pKing.setColor(PieceColor.WHITE);
		assertNotNull(pKing.getColor().equals(PieceColor.WHITE));
	}







}
