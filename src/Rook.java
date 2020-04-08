

import java.awt.Point;
  
public class Rook extends AbstractPiece {

	public Rook(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) { 

		System.out.println("_Rook rules!");
		
		boolean canMove = false;
		
		if (this.getName().equals(PieceName.ROOK)){
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength(); 
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
		AbstractPiece whichKing = null;	// god is so big
		AbstractPiece rook = this;
		int x  = rook.getPosition().x;
		int y  = rook.getPosition().y;
		

		if (this.getName().equals(PieceName.ROOK)&& this.getColor().equals(PieceColor.WHITE)) {
			if (y == blackKing.getPosition().y) {
				// up
				if (x - blackKing.getPosition().x > 0)
					checked = sideCheck(0, this, blackKing);
				// down
				if (blackKing.getPosition().x - x> 0)
					checked = sideCheck(2, this, blackKing);
			}
			if (x == blackKing.getPosition().x) {
				// right
				if (blackKing.getPosition().y - y > 0)
					checked = sideCheck(1, this, blackKing);
				// left
				if (y - blackKing.getPosition().y > 0)
					checked = sideCheck(3, this, blackKing);
			}
		}
		if (this.getName().equals(PieceName.ROOK)&& this.getColor().equals(PieceColor.BLACK)) {
			if (y == whiteKing.getPosition().y) {
				// up
				if (x - whiteKing.getPosition().x > 0)
					checked = sideCheck(0, this, whiteKing);
				// down
				if (whiteKing.getPosition().x - x > 0)
					checked = sideCheck(2, this, whiteKing);
			}
			if (x == whiteKing.getPosition().x) {
				// right
				if (whiteKing.getPosition().y - y > 0)
					checked = sideCheck(1, this, whiteKing);
				// left
				if (y - whiteKing.getPosition().y > 0)
					checked = sideCheck(3, this, whiteKing);
			}
		}
		
		
		System.out.println("_Rook: \t\t"+this.getPieceID()+" who? "+whichKing);
		return checked;
	}



	public void BlackRookSA(int tempCoor[], int oldX, int oldY, int[][] squares) {
		
		int x, y;
		boolean piecethere;
		for (int k = 0; k < 4; k++) {
			piecethere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceSideCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					for(AbstractPiece p : AbstractPiece.pieces)
						if (p.getPosition().x - 1 == x
								&& p.getPosition().y - 1 == y)
							piecethere = true;
					if (squares[x][y] == 1)
						squares[x][y] += 2;
					else
						squares[x][y] = 2;
					if (piecethere)
						break here;
				}
			}
			tempCoor[0] = oldX;
			tempCoor[1] = oldY;
		}
		
	}
	
	public void WhiteRookSA(int tempCoor[], int oldX, int oldY, int[][] squares) {
		
		int x, y;
		
		boolean pieceThere;
		for (int k = 0; k < 4; k++) {
			pieceThere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceSideCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					for(AbstractPiece p : AbstractPiece.pieces)
						if (p.getPosition().x - 1 == x
								&& p.getPosition().y - 1 == y)
							pieceThere = true;
					if (squares[x][y] == 2)
						squares[x][y] += 1;
					else
						squares[x][y] = 1;
					if (pieceThere)
						break here;
				}
			}
			tempCoor[0] = oldX;
			tempCoor[1] = oldY;
		}
		
	}

	@Override
	public boolean squaresAttacked(int[][] squares) {
		

		int tempcoor[] = new int[2];
		int oldx, oldy; 

		tempcoor[0] = this.getPosition().x;
		tempcoor[1] = this.getPosition().y;
		
		tempcoor[0]--;
		tempcoor[1]--;
		oldx = tempcoor[0];
		oldy = tempcoor[1];
		if (this.getColor().equals(PieceColor.BLACK))
			BlackRookSA(tempcoor, oldx, oldy, squares); 
		

		if (this.getColor().equals(PieceColor.WHITE))
			WhiteRookSA(tempcoor, oldx, oldy, squares); 
		
		return false;
		// TODO Auto-generated method stub
		
	}

	
}
