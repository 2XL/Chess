package chess.model;

import java.awt.Point;




/**
 * piece family specific piece knight
 * @author Xiang
 *
 */
public class Knight extends AbstractPiece{

	/**
	 * this generates a new knight piece
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param iconLength
	 * @param isPromote : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public Knight(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength, boolean isPromote) {
		super(id, posInit, pos, visible, firstMove, iconLength, isPromote); 
		this.setName(PieceName.KNIGHT);
	}

	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {

		boolean canMove = false;

		if (this.getName().equals(PieceName.KNIGHT)){
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength();

			Point[] movePosition = new Point[] {
					new Point(x - 1, y + 2),
					new Point(x + 1, y + 2),
					new Point(x - 1, y - 2),
					new Point(x + 1, y - 2),

					new Point(x - 2, y + 1),
					new Point(x + 2 ,y + 1), 
					new Point(x - 2, y - 1),
					new Point(x + 2 ,y - 1) 
			};


			canMove =  canMoveByTargetPosition(movePosition); 
			if (canMove) {
				if (pieceFound) {
					if (targetPiece.getName().equals(PieceName.KING)) 
						return false; 
					canMove = canMoveByColour(targetPiece);

				}
			}
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

		int thisPosX = this.getColumnRow().x;
		int thisPosY = this.getColumnRow().y; 

		if (this.getColor().equals(PieceColor.WHITE)) { 
			if ((bKingPosX == thisPosX - 1 || bKingPosX == thisPosX + 1)
					&& (bKingPosY == thisPosY - 2 || bKingPosY == thisPosY + 2)) {
				checked = true; 
			}
			if ((bKingPosX == thisPosX - 2 || bKingPosX == thisPosX + 2)
					&& (bKingPosY == thisPosY - 1 || bKingPosY == thisPosY + 1)) {
				checked = true; 
			}
		}

		if (this.getColor().equals(PieceColor.BLACK)) { 
			if ((wKingPosX == thisPosX - 1 || wKingPosX == thisPosX + 1)
					&& (wKingPosY == thisPosY - 2 || wKingPosY == thisPosY + 2)) {
				checked = true; 
			}
			if ((wKingPosX == thisPosX - 2 || wKingPosX == thisPosX + 2)
					&& (wKingPosY == thisPosY - 1 || wKingPosY == thisPosY + 1)) {
				checked = true; 
			}
		}

		return checked;
	}

	@Override
	public void squaresAttacked(int[][] squares) {


		int tempcoor[] = new int[2];
		int oldx, oldy, x, y; 

		tempcoor[0] = this.getColumnRow().x;
		tempcoor[1] = this.getColumnRow().y;


		tempcoor[0]--;
		tempcoor[1]--;


		oldx = tempcoor[0];
		oldy = tempcoor[1];


		if (this.getColor().equals(PieceColor.BLACK) && this.isVisible()) {
			for (int j = 0; j < 8; j++) {
				pieceKnightCoor(tempcoor, j);
				x = tempcoor[0];
				y = tempcoor[1];
				if (tempcoor[0] < 8 && tempcoor[0] >= 0) {
					if (tempcoor[1] < 8 && tempcoor[1] >= 0) {
						if (squares[x][y] == 1)
							squares[x][y] += 2;
						else
							squares[x][y] = 2;
					}
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		}

		if (this.getColor().equals(PieceColor.WHITE) && this.isVisible()) {
			for (int j = 0; j < 8; j++) {
				pieceKnightCoor(tempcoor, j);
				x = tempcoor[0];
				y = tempcoor[1];
				if (tempcoor[0] < 8 && tempcoor[0] >= 0) {
					if (tempcoor[1] < 8 && tempcoor[1] >= 0) {
						if (squares[x][y] == 2)
							squares[x][y] += 1;
						else
							squares[x][y] = 1;
					}
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		}

	}

	/**
	 * update knight target able coorPosition by piece
	 * @param tempCoor - target self position 
	 * @param moveCase - target orientation case
	 * @return true if can target, else can't target because its outside target range
	 */
	protected boolean pieceKnightCoor(int tempCoor[], int moveCase) {
		boolean moveok = false;
		switch (moveCase) {
		case 0:
			tempCoor[0] -= 2;
			tempCoor[1] += 1;
			break;
		case 1:
			tempCoor[0] -= 1;
			tempCoor[1] += 2;
			break;
		case 2:
			tempCoor[0] += 1;
			tempCoor[1] += 2;
			break;
		case 3:
			tempCoor[0] += 2;
			tempCoor[1] += 1;
			break;
		case 4:
			tempCoor[0] += 2;
			tempCoor[1] -= 1;
			break;
		case 5:
			tempCoor[0] += 1;
			tempCoor[1] -= 2;
			break;
		case 6:
			tempCoor[0] -= 1;
			tempCoor[1] -= 2;
			break;
		case 7:
			tempCoor[0] -= 2;
			tempCoor[1] -= 1;
			break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveok = true;
		return moveok;
	}



}
