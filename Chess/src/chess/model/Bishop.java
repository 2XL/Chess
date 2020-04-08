package chess.model;

import java.awt.Point; 

/**
 * piece family specific piece bishop
 * @author Xiang
 *
 */
public class Bishop extends AbstractPiece{

	/**
	 * this generates a new bishop piece
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param iconLength
	 * @param isPromote : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public Bishop(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength, boolean isPromote) {
		super(id, posInit, pos, visible, firstMove, iconLength, isPromote);
		this.setName(PieceName.BISHOP);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pieceRules(int y, int x, boolean pieceFound, AbstractPiece targetPiece) {

		return diagonalMove(x, y, pieceFound , targetPiece);

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


		int x  = this.getColumnRow().x;
		int y  = this.getColumnRow().y;

		if (this.getColor().equals(PieceColor.WHITE)
				&& this.isVisible()) {
			// case 0 up to the left
			if (bKingPosX - x == bKingPosY - y)
				if (bKingPosX - x > 0 && bKingPosY - y > 0)
					checked = diagonalCheck(0, this, blackKing);
			// case 1 up to the right
			if (bKingPosX - x == y - bKingPosY)
				if (bKingPosX - x > 0 && y - bKingPosY > 0)
					checked = diagonalCheck(1, this, blackKing);
			// case 2 down to the right
			if (x - bKingPosX == y - bKingPosY)
				if (x - bKingPosX > 0 && y - bKingPosY > 0)
					checked = diagonalCheck(2, this, blackKing);
			// case 3 down to the left
			if (x - bKingPosX == bKingPosY - y)
				if (x - bKingPosX > 0 && bKingPosY - y > 0)
					checked = diagonalCheck(3, this, blackKing);
		}
		if (this.getColor().equals(PieceColor.BLACK) && this.isVisible()) {
			// case 0 up to the left
			if (wKingPosX - x == wKingPosY - y)
				if (wKingPosX - x > 0 && wKingPosY - y > 0)
					checked = diagonalCheck(0, this, whiteKing);
			// case 1 up to the right
			if (wKingPosX - x == y - wKingPosY)
				if (wKingPosX - x > 0 && y - wKingPosY > 0)
					checked = diagonalCheck(1, this, whiteKing);
			// case 2 down to the right
			if (x - wKingPosX == y - wKingPosY)
				if (x - wKingPosX > 0 && y - wKingPosY > 0)
					checked = diagonalCheck(2, this, whiteKing);
			// case 3 down to the left
			if (x - wKingPosX == wKingPosY
					- y)
				if (x - wKingPosX > 0 && wKingPosY - y > 0)
					checked = diagonalCheck(3, this, whiteKing);
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
			diagonalSquareAttack(tempCoor, oldX, oldY, squares);  
	}
}
