package chess.model;

import java.awt.Point;




/**
 * piece family specific piece king
 * @author Xiang
 *
 */
public class King extends AbstractPiece {

	boolean checked;
	/**
	 * this generates a new king piece
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param iconLength
	 * @param isPromote : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public King(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength, boolean isPromote) {
		super(id, posInit, pos, visible, firstMove, iconLength, isPromote); 
		checked = false;
		this.setName(PieceName.KING);
	}


	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {

		boolean canMove = false;

		if (this.getName().equals(PieceName.KING)){
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength();

			Point[] movePosition = new Point[] {
					new Point(x-1, y-1),
					new Point(x-1, y  ),
					new Point(x-1, y+1),
					new Point(x  , y-1),
					new Point(x  , y+1),
					new Point(x+1, y-1),
					new Point(x+1, y  ),
					new Point(x+1, y+1),
			}; 
			canMove =  canMoveByTargetPosition(movePosition);   
			if (canMove) {
				if (pieceFound) {
					canMove = canMoveByColour(targetPiece);
				}
			}
		}
		if (canMove) this.setFirstMove(false); 
		return canMove;

	}



	@Override
	public boolean handlePieceCheckMate() { 
		return false;
	}


	@Override
	public void squaresAttacked(int[][] squares) {


		int tempCoor[] = new int[2];
		int oldX, oldY, x, y; 


		tempCoor[0] = this.getColumnRow().x;
		tempCoor[1] = this.getColumnRow().y;
		tempCoor[0]--;
		tempCoor[1]--;
		oldX = tempCoor[0];
		oldY = tempCoor[1];


		if (this.getColor().equals(PieceColor.BLACK)) {
			for (int k = 0; k < 8; k++) {
				if (pieceKingCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					if (squares[x][y] == 1)
						squares[x][y] += 2;
					else
						squares[x][y] = 2;
				}
				tempCoor[0] = oldX;
				tempCoor[1] = oldY;
			}
		} 

		if (this.getColor().equals(PieceColor.WHITE)) {
			for (int k = 0; k < 8; k++) {
				if (pieceKingCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					if (squares[x][y] == 2)
						squares[x][y] += 1;
					else
						squares[x][y] = 1;
				}
				tempCoor[0] = oldX;
				tempCoor[1] = oldY;
			}
		}

	}

	/**
	 * update king target able coorPosition by piece
	 * @param tempCoor - target self position 
	 * @param moveCase - target orientation case
	 * @return true if can target, else can't target because its outside target range
	 */
	protected boolean pieceKingCoor(int[] tempCoor, int moveCase){
		boolean moveOk = false;
		switch (moveCase) {
		case 0:
			tempCoor[0]--;
			tempCoor[1]--;
			break;
		case 1:
			tempCoor[0]--;
			break;
		case 2:
			tempCoor[0]++;
			tempCoor[1]--;
			break;
		case 3:
			tempCoor[1]--;
			break;
		case 4:
			tempCoor[1]++;
			break;
		case 5:
			tempCoor[0]--;
			tempCoor[1]++;
			break;
		case 6:
			tempCoor[0]++;
			break;
		case 7:
			tempCoor[0]++;
			tempCoor[1]++;
			break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveOk = true;
		return moveOk;
	}



}