

import java.awt.Point;
   
public class King extends AbstractPiece {

	boolean checked;
	
	public King(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
		checked = false;
	}

	
	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
 
		System.out.println("_King rules !");
		// x and y are the target position 
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
			// conditions for castling - first move, no pieces in the way,
			// no squares under attack
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
	public boolean squaresAttacked(int[][] squares) {
		
		
		int tempcoor[] = new int[2];
		int oldx, oldy, x, y; 
		
		
		tempcoor[0] = this.getPosition().x;
		tempcoor[1] = this.getPosition().y;
		tempcoor[0]--;
		tempcoor[1]--;
		oldx = tempcoor[0];
		oldy = tempcoor[1];
		
		
		if (this.getColor().equals(PieceColor.BLACK)) {
			for (int k = 0; k < 8; k++) {
				if (pieceKingCoor(tempcoor, k)) {
					x = tempcoor[0];
					y = tempcoor[1];
					if (squares[x][y] == 1)
						squares[x][y] += 2;
					else
						squares[x][y] = 2;
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		} 

		if (this.getColor().equals(PieceColor.WHITE)) {
			for (int k = 0; k < 8; k++) {
				if (pieceKingCoor(tempcoor, k)) {
					x = tempcoor[0];
					y = tempcoor[1];
					if (squares[x][y] == 2)
						squares[x][y] += 1;
					else
						squares[x][y] = 1;
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		}
		
		return true;
		// TODO Auto-generated method stub
		
	}

	
	

}