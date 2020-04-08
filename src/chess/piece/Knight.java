package chess.piece;


import java.awt.Point;
  
 
public class Knight extends AbstractPiece{

	public Knight(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
		
		System.out.println("_Knight rules!");
		 
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
		
		// can update code order
		return canMove;
		
	}
 
	
	@Override
	public boolean handlePieceCheckMate() {
		boolean checked = false;
		AbstractPiece blackKing = getKing(PieceColor.BLACK); // 4 black
		AbstractPiece whiteKing = getKing(PieceColor.WHITE); // 28 white
		AbstractPiece whichKing = null;	// god is so big
		
		if (this.getName().equals(PieceName.KNIGHT) && this.getColor().equals(PieceColor.WHITE)) { 
			if ((blackKing.getPosition().x == this.getPosition().x - 1 || blackKing.getPosition().x == this.getPosition().x + 1)
					&& (blackKing.getPosition().y == this.getPosition().y - 2 || blackKing.getPosition().y == this.getPosition().y + 2)) {
				checked = true;
				whichKing = blackKing;
			}
			if ((blackKing.getPosition().x == this.getPosition().x - 2 || blackKing.getPosition().x == this.getPosition().x + 2)
					&& (blackKing.getPosition().y == this.getPosition().y - 1 || blackKing.getPosition().y == blackKing.getPosition().y + 1)) {
				checked = true;
				whichKing = blackKing;
			}
		}
		
		if (this.getName().equals(PieceName.KNIGHT) && this.getColor().equals(PieceColor.BLACK)) { 
			if ((whiteKing.getPosition().x == this.getPosition().x - 1 || whiteKing.getPosition().x == this.getPosition().x + 1)
					&& (whiteKing.getPosition().y == this.getPosition().y - 2 || whiteKing.getPosition().y == this.getPosition().y + 2)) {
				checked = true;
				whichKing = whiteKing;
			}
			if ((whiteKing.getPosition().x == this.getPosition().x - 2 || whiteKing.getPosition().x == this.getPosition().x + 2)
					&& (whiteKing.getPosition().y == this.getPosition().y - 1 || whiteKing.getPosition().y == this.getPosition().y + 1)) {
				checked = true;
				whichKing = whiteKing;
			}
		}
		
		System.out.println("_Knight: \t"+this.getPieceID()+" who? "+whichKing);
		return checked;
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
		
		
		
		return false;
		// TODO Auto-generated method stub
		
	}


}
