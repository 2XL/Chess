

import java.awt.Point;
 
 
public class Queen extends Rook{

	public Queen(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
		// TODO Auto-generated method stub

		System.out.println("__Queen rules!");
		
		boolean canMoveSide = false;
		boolean canMoveDiag = false;
		if (this.getName().equals(PieceName.QUEEN)) {
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength();
			canMoveSide = this.sideMove(x, y, pieceFound, targetPiece);
			canMoveDiag = this.diagonalMove(x, y, pieceFound, targetPiece);
			System.out.println("### Side: "+canMoveSide+" : Diag: "+canMoveDiag+ " Return: "+(canMoveDiag ^ canMoveSide));
		}
		return canMoveDiag ^ canMoveSide;
	}
 

	@Override
public boolean handlePieceCheckMate() {
		
		boolean checked = false;
		AbstractPiece blackKing = getKing(PieceColor.BLACK); // blackKing black
		AbstractPiece whiteKing = getKing(PieceColor.WHITE); // whiteKing white
		AbstractPiece whichKing = null;	// god is so big
		AbstractPiece rook = this;
		int x  = rook.getPosition().x;
		int y  = rook.getPosition().y;

		if ((this.getName().equals(PieceName.QUEEN)&& this.getColor().equals(PieceColor.WHITE)) 
				&& this.isVisible()) {
			// case 0 up to the left
			if (blackKing.getPosition().x - x == blackKing.getPosition().y
					- y)
				if (blackKing.getPosition().x  - x > 0
						&& blackKing.getPosition().y - y > 0)
					checked = diagonalCheck(0, this, blackKing);
			// case 1 up to the right
			if (blackKing.getPosition().x  - x == y
					- blackKing.getPosition().y)
				if (blackKing.getPosition().x  - x > 0
						&& y - blackKing.getPosition().y > 0)
					checked = diagonalCheck(1, this, blackKing);
			// case 2 down to the right
			if (x - blackKing.getPosition().x  == y
					- blackKing.getPosition().y)
				if (x - blackKing.getPosition().x  > 0
						&& y - blackKing.getPosition().y > 0)
					checked = diagonalCheck(2, this, blackKing);
			// case 3 down to the left
			if (x - blackKing.getPosition().x  == blackKing.getPosition().y
					- y)
				if (x - blackKing.getPosition().x  > 0
						&& blackKing.getPosition().y - y > 0)
					checked = diagonalCheck(3, this, blackKing);
			if (y == blackKing.getPosition().y) {
				// up
				if (x - blackKing.getPosition().x  > 0)
					checked = sideCheck(0, this, blackKing);
				// down
				if (blackKing.getPosition().x  - x > 0)
					checked = sideCheck(2, this, blackKing);
			}
			if (x == blackKing.getPosition().x ) {
				// right
				if (blackKing.getPosition().y - y > 0)
					checked = sideCheck(1, this, blackKing);
				// left
				if (y - blackKing.getPosition().y > 0)
					checked = sideCheck(3, this, blackKing);
			}
		}
		
		if ((this.getName().equals(PieceName.QUEEN)&& this.getColor().equals(PieceColor.BLACK)) 
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

		System.out.println("_Queen: \t"+this.getPieceID()+" who? "+whichKing);
		return checked;
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
		
		if (this.getColor().equals(PieceColor.BLACK) && this.isVisible()) {
			BlackBishopSA(tempcoor, oldx, oldy, squares); 
			BlackRookSA(tempcoor, oldx, oldy, squares); 
		}
		if (this.getColor().equals(PieceColor.WHITE) && this.isVisible()) {
			WhiteBishopSA(tempcoor, oldx, oldy, squares); 
			WhiteRookSA(tempcoor, oldx, oldy, squares); 
		}
		
		
		return false; 
		
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
}
