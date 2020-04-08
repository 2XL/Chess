package chess.model;

import java.awt.Point;


/**
 * piece family specific piece pawn
 * @author Xiang
 *
 */
public class Pawn extends AbstractPiece{

	boolean promotion;

	/**
	 * this generates a new pawn piece
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param iconLength
	 * @param isPromote : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public Pawn(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength, boolean isPromote) {
		super(id, posInit, pos, visible, firstMove, iconLength, isPromote);
		this.setName(PieceName.PAWN);
		this.promotion = false;
	}

	/**
	 * check the boolean parameter of pawn
	 * @return promotion
	 */
	public boolean isPromotion() {
		return promotion;
	}


	@Override
	public boolean pieceRules(int y, int x, boolean pieceFound, AbstractPiece targetPiece) { 
		boolean canMove = false;

		int pieceNum = targetPiece == null ? 40 : targetPiece.getPieceID();
		// *************************************
		// logic for pawn
		// doesn't handle last row

		if (this.getName().equals(PieceName.PAWN)) {
			y = y / this.getPieceIconLength() + 1 ;
			x = x / this.getPieceIconLength() + 1;

			int thisX = this.getColumnRow().x;
			int thisY = this.getColumnRow().y;


			if (this.getColor().equals(PieceColor.WHITE)) {
				if (thisY == y) {
					// generic white pawn move
					if (thisX == x + 1) {
						// detects if there isn't a piece
						if (!pieceFound) // can move
						{
							this.setFirstMove(false);
							canMove = true;  
						}
					}
					// exception for the first move to move two places
					if (thisX == x + 2
							&& this.isFirstMove()) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true; 
						}
					}
				}
				// handles white pawn taking pieces diagonally
				else if (thisY == y + 1
						|| thisY == y - 1) {
					if (thisX == x + 1) {
						if (pieceNum < 16) {
							if (pieceFound) {
								this.setFirstMove(false);
								canMove = true; 
							}
						}
					}
				}
				if (canMove && x == 1)
					promotion = true;
			} 


			if (this.getColor().equals(PieceColor.BLACK)) {
				if (thisY == y) {
					if (thisX == x - 1) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true; 
						}
					}
					if (thisX == x - 2
							&& this.isFirstMove()) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true; 
						}
					}
				}
				// handles black pawn taking pieces diagonally
				else if (thisY == y + 1
						|| thisY == y - 1) {
					if (thisX == x - 1) {
						if (pieceNum < 32 && pieceNum > 15) {
							if (pieceFound) {
								this.setFirstMove(false);
								canMove = true; 
							}
						}
					}
				}
				if (canMove && x == 8)
					this.promotion = true;
			} 

		}

		// ************************************
		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 

			}
		} 			
		return canMove;
	}


	@Override
	public boolean handlePieceCheckMate(){
		boolean checked = false;
		AbstractPiece blackKing = getKing(PieceColor.BLACK); // 4 black
		AbstractPiece whiteKing = getKing(PieceColor.WHITE); // 28 white 

		int bKingPosX = blackKing.getColumnRow().x;
		int bKingPosY = blackKing.getColumnRow().y;

		int wKingPosX = whiteKing.getColumnRow().x;
		int wKingPosY = whiteKing.getColumnRow().y;

		int thisPosX = this.getColumnRow().x;
		int thisPosY = this.getColumnRow().y;


		if (this.getColor().equals(PieceColor.WHITE)) {
			if ( bKingPosX == thisPosX - 1
					&& bKingPosY == thisPosY + 1) {
				checked = true; 
			}
			if (bKingPosX == thisPosX - 1
					&& bKingPosY == thisPosY - 1) {
				checked = true; 
			}
		}
		if (this.getColor().equals(PieceColor.BLACK)) {
			if (wKingPosX == thisPosX + 1
					&& wKingPosY == thisPosY + 1) {
				checked = true;  
			}
			if (wKingPosX == thisPosX + 1
					&& wKingPosY == thisPosY - 1) {
				checked = true;  
			}
		} 
		return checked;
	}


	@Override
	public void squaresAttacked(int[][] squares) { 

		int x = this.getColumnRow().x;
		int y = this.getColumnRow().y;

		if ((this.getColor().equals(PieceColor.WHITE)) && x != 1) {
			if (y == 1) // to handle pawns on the first
				// column
			{
				if (squares[x - 2][y] == 2)
					squares[x - 2][y] += 1;
				else
					squares[x - 2][y] = 1;
			} else if (y == 8) // to handle pawns on the
				// last column
			{
				if (squares[x - 2][y - 2] == 2)
					squares[x - 2][y - 2] += 1;
				else
					squares[x - 2][y - 2] = 1;
			} else // to handle pawns in between first and last columns
			{
				if (squares[x - 2][y] == 2)
					squares[x - 2][y] += 1;
				else
					squares[x - 2][y] = 1;
				if (squares[x - 2][y - 2] == 2)
					squares[x - 2][y - 2] += 1;
				else
					squares[x - 2][y - 2] = 1;
			}
		}
		if ((this.getColor().equals(PieceColor.BLACK)) && x != 8) {
			if (y == 1) // to handle pawns on the first
				// column
			{
				if (squares[x][y] == 1)
					squares[x][y] += 2;
				else
					squares[x][y] = 2;
			} else if (y == 8) // to handle pawns on the
				// last column
			{
				if (squares[x][y - 2] == 1)
					squares[x][y - 2] += 2;
				else
					squares[x][y - 2] = 2;
			} else // to handle pawns in between first and last columns
			{
				if (squares[x][y] == 1)
					squares[x][y] += 2;
				else
					squares[x][y] = 2;
				if (squares[x][y - 2] == 1)
					squares[x][y - 2] += 2;
				else
					squares[x][y - 2] = 2;
			}
		} 
	}





}
