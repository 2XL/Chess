

import java.awt.Point;
 



 
 
 
public class Bishop extends AbstractPiece{

	public Bishop(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
 
		System.out.println("_Bishop rules!");
		 
		boolean canMove = false;
		 
		if (this.getName().equals(PieceName.BISHOP)){
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength(); 
			canMove = diagonalMove(x, y, pieceFound , targetPiece); 
			
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
		 
		if ((this.getName().equals(PieceName.BISHOP)&& this.getColor().equals(PieceColor.WHITE)) 
				&& this.isVisible()) {
			// case 0 up to the left
			if (blackKing.getPosition().x - x == blackKing.getPosition().y
					- y)
				if (blackKing.getPosition().x - x > 0
						&& blackKing.getPosition().y - y > 0)
					checked = diagonalCheck(0, this, blackKing);
			// case 1 up to the right
			if (blackKing.getPosition().x - x == y
					- blackKing.getPosition().y)
				if (blackKing.getPosition().x - x > 0
						&& y - blackKing.getPosition().y > 0)
					checked = diagonalCheck(1, this, blackKing);
			// case 2 down to the right
			if (x - blackKing.getPosition().x == y
					- blackKing.getPosition().y)
				if (x - blackKing.getPosition().x > 0
						&& y - blackKing.getPosition().y > 0)
					checked = diagonalCheck(2, this, blackKing);
			// case 3 down to the left
			if (x - blackKing.getPosition().x == blackKing.getPosition().y
					- y)
				if (x - blackKing.getPosition().x > 0
						&& blackKing.getPosition().y - y > 0)
					checked = diagonalCheck(3, this, blackKing);
		}
		if ((this.getName().equals(PieceName.BISHOP)&& this.getColor().equals(PieceColor.BLACK)) 
				&& this.isVisible()) {
			// case 0 up to the left
			if (whiteKing.getPosition().x - x == whiteKing.getPosition().y
					- y)
				if (whiteKing.getPosition().x - x > 0
						&& whiteKing.getPosition().y - y > 0)
					checked = diagonalCheck(0, this, whiteKing);
			// case 1 up to the right
			if (whiteKing.getPosition().x - x == y
					- whiteKing.getPosition().y)
				if (whiteKing.getPosition().x - x > 0
						&& y - whiteKing.getPosition().y > 0)
					checked = diagonalCheck(1, this, whiteKing);
			// case 2 down to the right
			if (x - whiteKing.getPosition().x == y
					- whiteKing.getPosition().y)
				if (x - whiteKing.getPosition().x > 0
						&& y - whiteKing.getPosition().y > 0)
					checked = diagonalCheck(2, this, whiteKing);
			// case 3 down to the left
			if (x - whiteKing.getPosition().x == whiteKing.getPosition().y
					- y)
				if (x - whiteKing.getPosition().x > 0
						&& whiteKing.getPosition().y - y > 0)
					checked = diagonalCheck(3, this, whiteKing);
		}
		
		System.out.println("_Bishop: \t"+this.getPieceID()+" who? "+whichKing);
		return checked;
	}



	
	
	public void BlackBishopSA(int tempcoor[], int oldx, int oldy, int[][] squares) {
		
		int x, y;
		boolean piecethere;
		for (int k = 0; k < 4; k++) {
			piecethere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceDiagCoor(tempcoor, k)) {
					x = tempcoor[0];
					y = tempcoor[1]; 
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
			tempcoor[0] = oldx;
			tempcoor[1] = oldy;
		}
		
	}

	
	public void WhiteBishopSA(int tempCoor[], int oldX, int oldY, int[][] squares) {
		int x, y;
		boolean pieceThere;
		for (int k = 0; k < 4; k++) {
			pieceThere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceDiagCoor(tempCoor, k)) {
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
		if (this.getColor().equals(PieceColor.BLACK) && this.isVisible())
			BlackBishopSA(tempcoor, oldx, oldy, squares); 

		if (this.getColor().equals(PieceColor.WHITE) && this.isVisible())
			WhiteBishopSA(tempcoor, oldx, oldy, squares); 
	
		return false;
		// TODO Auto-generated method stub
		
	}
}
