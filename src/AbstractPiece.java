

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;
 





 

public abstract class AbstractPiece implements IPiece{
		
 
	
	int pieceID;
	Point positionInit; // position inicial al pintar
	Point position; 	// position 0 - 0
	
	Image pieceIcon; // Image mypic 
	int pieceIconLength;
	
	boolean firstMove;
	boolean visible;// boolean visible //counts as taken
	
	PieceName name; // String color;
	PieceColor color; // String name;
	 
	// llista de tots els punters de pieces
	protected static LinkedList<AbstractPiece> pieces = new LinkedList<AbstractPiece>();
	
	public AbstractPiece(int id, Point posInit, Point pos, boolean visible, boolean firstMove, int pieceIconLength) {
		// TODO Auto-generated constructor stub 
		this.pieceID = id;
		this.positionInit = posInit;
		this.position = pos;
		this.visible = visible; 
		this.firstMove = firstMove;
		this.pieceIconLength = pieceIconLength;
		AbstractPiece.getPieces().add(this);  
	}
	 
	public boolean sideMove(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
		
		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2];
		boolean canMove = false;

		Point sourcePiecePos = this.getPosition();
		int thisX = sourcePiecePos.x / this.pieceIconLength;
		int thisY = sourcePiecePos.y / this.pieceIconLength;
 
		if (thisY == y) {	// moviment vertical
			// case 0 up
			if (thisX - x > 0) {
				length = thisX - x;
				moveCase = 0;
				canMove = true;
			}
			// case 2 down
			if (x - thisX > 0) {
				length = x - thisX;
				moveCase = 2;
				canMove = true;
			}
		}
		if (thisX == x) {	// moviment horitzontal
			// case 1 right
			if (y - thisY > 0) {
				length = y - thisY;
				moveCase = 1;
				canMove = true;
			}
			// case 3 left
			if (thisY - y > 0) {
				length = thisY - y;
				moveCase = 3;
				canMove = true;
			}
		}
		

		System.out.println(sourcePiecePos + " : " +x+","+y+"\n CanMove: "+canMove+"\n Length: "+length+ "\n moveCase: "+moveCase);
		
		
		if (canMove) {
			tempCoor[0] = thisX;
			tempCoor[1] = thisY;
			
			AbstractPiece[] allPieces = new AbstractPiece[AbstractPiece.pieces.size()];
			
			int elem = 0;
			for(AbstractPiece p : AbstractPiece.pieces){
				allPieces[elem] = p; 
				elem++; 
			}
			
			for (int i = 0; i < length - 1; i++) {
				this.pieceSideCoor(tempCoor, moveCase);
				for (int j = 0; j < allPieces.length; j++){  
					if (tempCoor[0] == allPieces[j].getPosition().x/this.pieceIconLength && 
							tempCoor[1] ==  allPieces[j].getPosition().y/this.pieceIconLength)
						canMove = false; // es decir que hay una pieza entre yo y el target point
				}
			}
		}
		System.out.println("___Side? canMove? "+canMove);
		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 
				canMove = this.canMoveByColour(targetPiece);
			}
		}
		return canMove;
	}

	protected boolean diagonalMove(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
		
		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2]; 
		boolean canMove = false; 
		
		Point sourcePiecePos = this.getPosition();
		int thisX = sourcePiecePos.x / this.pieceIconLength;
		int thisY = sourcePiecePos.y / this.pieceIconLength;
		
		if (thisX - x == thisY - y) {
			// case 1 up to the left
			if (thisX - x > 0 && thisY - y > 0) { 
				length = thisX - x;
				moveCase = 0;
				canMove = true;
			}
		}
		
		if (thisX - x == y - thisY) {
			// case 2 up to the right
			if (thisX - x > 0 && y - thisY > 0) {
				length = thisX - x;
				moveCase = 1;
				canMove = true;
			}
		}
		 
		if (x - thisX == y - thisY) {
			// case 3 down to the right
			if (x - thisX > 0 && y - thisY > 0) {
				length = x - thisX;
				moveCase = 2;
				canMove = true;
			}
		}
		
		if (x - thisX == thisY - y) {
			// case 4 down to the left
			if (x - thisX > 0 && thisY - y > 0) {
				length = x - thisX;
				moveCase = 3;
				canMove = true;
			}
		} 
		
		System.out.println(sourcePiecePos + " : " +x+","+y+"\n CanMove: "+canMove+"\n Length: "+length+ "\n moveCase: "+moveCase);
		
		if (canMove) {
			tempCoor[0] = thisX;
			tempCoor[1] = thisY;
			AbstractPiece[] allPieces = new AbstractPiece[AbstractPiece.pieces.size()];
			
			int elem = 0;
			for(AbstractPiece p : AbstractPiece.pieces){
				allPieces[elem] = p; 
				elem++;
	
			}
			 
			for (int i = 0; i < length - 1; i++) {
				
				this.pieceDiagCoor(tempCoor, moveCase); 
				for (int j = 0; j < allPieces.length; j++){ 
					if (tempCoor[0] == allPieces[j].getPosition().x/this.pieceIconLength && 
							tempCoor[1] ==  allPieces[j].getPosition().y/this.pieceIconLength)
						canMove = false; // es decir que hay una pieza entre yo y el target point
				}
			}
		}
		

		System.out.println("___Diag? canMove? "+canMove);
		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 
				canMove = this.canMoveByColour(targetPiece);
			}
		}  
		return canMove;  
	}
	
	protected boolean sideCheck(int control, AbstractPiece piece, AbstractPiece king) {
		
		int length = 0;
		int tempCoor[] = new int[2];
		boolean check = true;

		// the the rook and the king are on the same column substract rows
		if (control == 0 || control == 2)
			length = Math.abs(king.getPosition().x - piece.getPosition().x);
		if (control == 1 || control == 3)
			length = Math.abs(king.getPosition().y - piece.getPosition().y);
		tempCoor[0] = piece.getPosition().x;
		tempCoor[1] = piece.getPosition().y;
		for (int i = 0; i < length - 1; i++) {
			pieceSideCoor(tempCoor, control);
			
			for(AbstractPiece p : AbstractPiece.pieces){ // si hi han peces entre mig no hiha checkmate!
				if(tempCoor[0] == p.getPosition().x && tempCoor[1] == p.getPosition().y)
					return false;
			} 
		}
		
		if (check) {  
			System.out.println("You will be chased and rapted: "+king);
		}
		
		return true;
	}
	
	protected boolean diagonalCheck(int control, AbstractPiece piece, AbstractPiece king) {
		
		int length = 0;
		int tempCoor[] = new int[2];
		boolean check;

		length = Math.abs(king.getPosition().x - king.getPosition().y);
		tempCoor[0] = king.getPosition().x;
		tempCoor[1] = king.getPosition().y;
		
		check = true;
		for (int i = 0; i < length - 1; i++) {
			pieceDiagCoor(tempCoor, control);
			
			for(AbstractPiece p : AbstractPiece.pieces){ // si hi han peces entre mig no hiha checkmate!
				if(tempCoor[0] == p.getPosition().x && tempCoor[1] == p.getPosition().y)
					return false;
			}
		
		}
			if (check) {  
				System.out.println("You will be chased and rapted: "+king);
			}
		return true;
	}
	
	protected boolean pieceSideCoor(int[] tempCoor, int moveCase) {
		boolean moveOk = false;
		switch (moveCase) {
		case 0:
			tempCoor[0]--;
			break;
		case 1:
			tempCoor[1]++;
			break;
		case 2:
			tempCoor[0]++;
			break;
		case 3:
			tempCoor[1]--;
			break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveOk = true;
		return moveOk;
	}
	
	protected boolean pieceDiagCoor(int[] tempCoor, int moveCase) {
		boolean moveOk = false;
		switch (moveCase) {
		case 0: // up to the left
			tempCoor[0]--;
			tempCoor[1]--;
			break;
		case 1: // up to the right
			tempCoor[0]--;
			tempCoor[1]++;
			break;
		case 2: // down to the right
			tempCoor[0]++;
			tempCoor[1]++;
			break;
		case 3: // down to the left
			tempCoor[0]++;
			tempCoor[1]--;
			break;
		}
		if (tempCoor[0] >= 0 && tempCoor[0] < 9)
			if (tempCoor[1] >= 0 && tempCoor[1] < 9)
				moveOk = true;
		return moveOk;
	}
	
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
	
	public AbstractPiece getKing(PieceColor color){
		for(AbstractPiece p : AbstractPiece.pieces){
			if( p.getName().equals(PieceName.KING) && p.getColor().equals(color))
				return p;
		}
		return null;
	} 
	
	public boolean canMoveByColour(AbstractPiece target){ 
		if (this.getColor().equals(target.getColor()))
			return false;
			System.out.println("_cantMoveCauseOfColor!");
		return true;
	}
	
	public boolean canMoveByTargetPosition(Point[] targetablePoints){ 
		for(Point p : targetablePoints) {
			System.out.println(p + ":"+ this.getPosition());
			if(this.getPosition().x / this.getPieceIconLength() == p.x &&
					this.getPosition().y / this.getPieceIconLength() == p.y )
			{	

				System.out.println("_canMove!");
				return true; 
			}
		}  
		return false;
	}
	
	
	/* ************************** */
	/* DEFAULT GETTER AND SETTERS */
	/* ************************** */
	
	public int getPieceID() {
		return pieceID;
	}
	public void setPieceID(int pieceID) {
		this.pieceID = pieceID;
	}
	public Point getPositionInit() {
		return positionInit;
	}
	public void setPositionInit(Point positionInit) {
		this.positionInit = positionInit;
	}
	public Point getPosition() {
		return position;
	}
	public void setPosition(Point position) {
		this.position = position;
	}
	public Image getPieceIcon() {
		return pieceIcon;
	}
	public void setPieceIcon(Image pieceIcon) {
		this.pieceIcon = pieceIcon;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public PieceName getName() {
		return name;
	}
	public void setName(PieceName name) {
		this.name = name;
	}
	public PieceColor getColor() {
		return color;
	}
	public void setColor(PieceColor color) {
		this.color = color;
	}
	
	public boolean isFirstMove() {
		return firstMove;
	}
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public int getPieceIconLength() {
		return pieceIconLength;
	}

	public void setPieceIconLength(int pieceIconLength) {
		this.pieceIconLength = pieceIconLength;
	}
 

	public static LinkedList<AbstractPiece> getPieces() {
		return pieces;
	}


	public static void setPieces(LinkedList<AbstractPiece> pieces) {
		AbstractPiece.pieces = pieces;
	}
	
/*	
	 @Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id: "+pieceID+" pos: "+positionInit.toString()+" posi: "+position.toString();
	}
	*/
	
}
