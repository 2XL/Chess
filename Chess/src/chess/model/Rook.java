package chess.model;

import java.awt.Point;



/**
 * piece family specific piece rook
 * @author Xiang
 *
 */
public class Rook extends AbstractPiece {


	/**
	 * this generates a new rook piece
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param iconLength
	 * @param isPromote : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public Rook(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength, boolean isPromote) {
		super(id, posInit, pos, visible, firstMove, iconLength, isPromote); 
		this.setName(PieceName.ROOK);
	}

	@Override
	public boolean pieceRules(int y, int x, boolean pieceFound, AbstractPiece targetPiece) { 

		boolean canMove = false;

		if (this.getName().equals(PieceName.ROOK)){ 
			canMove = sideMove(x, y, pieceFound, targetPiece);

			if (canMove)
				this.setFirstMove(false); 
		}
		return canMove; 
	}



	@Override
	public boolean handlePieceCheckMate() {


		boolean checked = false;

		AbstractPiece blackKing = getKing(PieceColor.BLACK); // 4 black
		AbstractPiece whiteKing = getKing(PieceColor.WHITE); // 28 white 


		int bKingPosX = blackKing.getColumnRow().x;
		int bKingPosY = blackKing.getColumnRow().y;

		int wKingPosX = whiteKing.getColumnRow().x;
		int wKingPosY = whiteKing.getColumnRow().y;

		int x  = this.getColumnRow().x ;
		int y  = this.getColumnRow().y;


		if (this.getColor().equals(PieceColor.WHITE)) {
			if (y == bKingPosY) {
				// up
				if (x - bKingPosX > 0)
					checked = sideCheck(0, this, blackKing);
				// down
				if (bKingPosX - x> 0)
					checked = sideCheck(2, this, blackKing);
			}
			if (x == bKingPosX) {
				// right
				if (bKingPosY - y > 0)
					checked = sideCheck(1, this, blackKing);
				// left
				if (y - bKingPosY > 0)
					checked = sideCheck(3, this, blackKing);
			}
		}


		if (this.getColor().equals(PieceColor.BLACK)){
			if (y == wKingPosY) {
				// up
				if (x - wKingPosX > 0)
					checked = sideCheck(0, this, whiteKing);
				// down
				if (wKingPosX - x > 0)
					checked = sideCheck(2, this, whiteKing);
			}
			if (x == wKingPosX) {
				// right
				if (wKingPosY - y > 0)
					checked = sideCheck(1, this, whiteKing);
				// left
				if (y - wKingPosY > 0)
					checked = sideCheck(3, this, whiteKing);
			}
		} 

		return checked;
	}


	@Override
	public void squaresAttacked(int[][] squares) {


		int tempCoor[] = new int[2];
		int oldX, oldY; 

		tempCoor[0] = this.getColumnRow().x;
		tempCoor[1] = this.getColumnRow().y;

		tempCoor[0]--;
		tempCoor[1]--;
		oldX = tempCoor[0];
		oldY = tempCoor[1];
		if (this.isVisible())
			sideSquareAttack(tempCoor, oldX, oldY, squares); 



	}


}
