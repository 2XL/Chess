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
import chess.model.Queen;
import chess.model.Rook;



/**
 * Junit Test Case 
 * @author Xiang
 * testing chess board piece checkmate
 */
@RunWith(Parameterized.class)
public class ChessTestCasePieceCM {

	@Parameters(name = "InputPrarmName")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ 11, 11, 11, 11 }
		});
	}

	int posX;
	int posY;
	int targetX;
	int targetY;
	int id = 0; 

	boolean firstMove = true;
	boolean visible = true;
	int iconLength = 50;
	boolean isPromote = true;

	Point targetPos = new Point();
	Point posInit;
	Point pos;

	AbstractPiece pBishop; 
	AbstractPiece pBlackKing; 
	AbstractPiece pWhiteKing;
	AbstractPiece pKnight; 
	AbstractPiece pPawn; 
	AbstractPiece pQueen; 
	AbstractPiece pRook; 

	/**
	 * initialize all possible piece types with its initial position   
	 * @param posInitX - initial on Chess Board row position on applet graphic
	 * @param posInitY - initial on Chess Board column position on applet graphic
	 * @param posX - initial on Chess Board row position on display matrix
	 * @param posY - initial on Chess Board column position on display matrix
	 */
	public ChessTestCasePieceCM(int posInitX, int posInitY, int posX, int posY){

		posInit = new Point(posInitX, posInitY);
		pos = new Point(posX, posY);

		pBlackKing = (new King(id++, posInit, pos, visible, firstMove, iconLength, isPromote));
		pBlackKing.setColor(PieceColor.BLACK);

		pWhiteKing = (new King(id++, posInit, pos, visible, firstMove, iconLength, isPromote));
		pWhiteKing.setColor(PieceColor.WHITE);


		// all pieces are outside the chess board

		pBishop = (new Bishop(id, posInit, pos, visible, firstMove, iconLength, isPromote));  
		pKnight = (new Knight(id++, posInit, pos, visible, firstMove, iconLength, isPromote)); 
		pPawn = (new Pawn(id++, posInit, pos, visible, firstMove, iconLength, isPromote)); 
		pQueen = (new Queen(id++, posInit, pos, visible, firstMove, iconLength, isPromote)); 
		pRook = (new Rook(id++, posInit, pos, visible, firstMove, iconLength, isPromote));



	}

	/**
	 * testing the chess game checkmate logic, for each oposite piece against target king
	 */
	@Test
	public void test() {

		// king	
		pWhiteKing.setColumnRow(new Point(3, 3));
		pWhiteKing.setPosition(new Point(3, 3));



		// test queen   	
		pQueen.setColor(PieceColor.BLACK); 
		pQueen.setColumnRow(new Point(4,4)); 


		System.out.println("queen checking white king?");
		assertTrue("you are checked!", pQueen.handlePieceCheckMate());

		pQueen.setColumnRow(new Point(0, 11));

		System.out.println("queen still checking white king?");
		assertFalse("you are not checked!", pQueen.handlePieceCheckMate());

		// test bishop
		pBishop.setColor(PieceColor.BLACK); 
		pBishop.setColumnRow(new Point(6,6)); 


		System.out.println("Bishop checking white king?");
		assertTrue("you are checked!", pBishop.handlePieceCheckMate());

		pBishop.setColumnRow(new Point(0, 11));

		System.out.println("Bishop still checking white king?");
		assertFalse("you are not checked!", pBishop.handlePieceCheckMate());

		// test knight
		pKnight.setColor(PieceColor.BLACK); 
		// en el applet funciona aqui no ho detecta
		pKnight.setColumnRow(new Point(5,4)); 	
		System.out.println("Knight checking white king?");
		assertTrue("you are checked!", pKnight.handlePieceCheckMate());

		pKnight.setColumnRow(new Point(11, 11));

		System.out.println("Knight still checking white king?");
		assertFalse("you are not checked!", pKnight.handlePieceCheckMate());

		// test pawn
		pPawn.setColor(PieceColor.BLACK); 

		pPawn.setColumnRow(new Point(2,2)); 
		System.out.println("Pawn checking white king?");
		assertTrue("you are checked!", pPawn.handlePieceCheckMate());

		pPawn.setColumnRow(new Point(11, 11));

		System.out.println("Pawn still checking white king?");
		assertFalse("you are not checked!", pPawn.handlePieceCheckMate());


		// test rook
		pRook.setColor(PieceColor.BLACK); 
		pRook.setColumnRow(new Point(1,3)); 
		pRook.setPosition(new Point(3,1)); 

		System.out.println("Rook checking white king?");
		assertTrue("you are checked!", pRook.handlePieceCheckMate());

		pRook.setColumnRow(new Point(11, 11));

		System.out.println("Rook still checking white king?");
		assertFalse("you are not checked!", pRook.handlePieceCheckMate());
















	}







}
