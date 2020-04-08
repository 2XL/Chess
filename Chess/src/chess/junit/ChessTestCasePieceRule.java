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
 * testing chess board piece rules
 */
@RunWith(Parameterized.class)
public class ChessTestCasePieceRule {


	@Parameters(name = "InputPrarmName")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ 4, 4, 4, 4 }
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
	AbstractPiece pKing; 
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
	public ChessTestCasePieceRule(int posInitX, int posInitY, int posX, int posY){

		posInit = new Point(posInitX, posInitY);
		pos = new Point(posX, posY);

		pBishop = (new Bishop(id, posInit, pos, visible, firstMove, iconLength, isPromote)); 

		pKing = (new King(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pKnight = (new Knight(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pPawn = (new Pawn(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pQueen = (new Queen(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

		pRook = (new Rook(id++, posInit, pos, visible, firstMove, iconLength, isPromote));

	}

	/**
	 * testing the chess game piece rule logic, for each piece.
	 */
	@Test
	public void test() { 

		System.out.println("can bishop diagonal move one length?");
		targetPos.setLocation(5*50, 5*50);
		pBishop.setColor(PieceColor.WHITE);
		assertTrue("i am a Bishop can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
				pBishop.pieceRules(targetPos.x, targetPos.y, false, null));

		System.out.println("can black pawn forward one length?");
		targetPos.setLocation(3*50, 4*50);
		pPawn.setColor(PieceColor.BLACK);
		assertTrue("i am a Pawn can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
				pPawn.pieceRules(targetPos.x, targetPos.y, false, null));

		System.out.println("can white king move one length?");
		targetPos.setLocation(3*50, 3*50);
		pKing.setColor(PieceColor.BLACK);
		//		assertTrue("i am a King can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
		//			pKing.pieceRules(targetPos.x, targetPos.y, false, null));

		System.out.println("can white queen move diagonal 2 length?"); 
		targetPos.setLocation(5*50, 5*50);
		pQueen.setColor(PieceColor.WHITE);
		assertTrue("i am a King can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
				pQueen.pieceRules(targetPos.x, targetPos.y, false, null));

		System.out.println("can white knight move diagonal 2 length?"); 
		targetPos.setLocation(1*50, 2*50);
		pKnight.setColor(PieceColor.WHITE);
		assertTrue("i am a Knight can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
				pKnight.pieceRules(targetPos.x, targetPos.y, false, null));

		System.out.println("can white rook move diagonal 2 length?"); 
		targetPos.setLocation(3*50, 6*50);
		pRook.setColor(PieceColor.WHITE);
		assertTrue("i am a Rook can move "+pos.x+":"+pos.y+" target position "+targetPos.x+":"+targetPos.y+" ", 
				pRook.pieceRules(targetPos.x, targetPos.y, false, null));
	}


}
