package chess.model;

import java.awt.Image;
import java.awt.Point;
import java.util.LinkedList;

/**
 * Abstract piece class with abstract parameters for all inherit piece class
 * @author Xiang
 *
 */
public abstract class AbstractPiece implements IPiece{

	/**
	 * piece unique id index
	 */
	int pieceID;

	/**
	 * piece paint on board position
	 */
	Point columnRow;

	/**
	 * piece on ChessBoard Position
	 */
	Point position;  

	/**
	 * image icon displaying the piece
	 */
	Image pieceIcon;  

	/**
	 * piece icon length 
	 */
	int pieceIconLength;

	/**
	 * is piece first movement, used on pawns or king for their first move
	 */
	boolean firstMove;

	/**
	 * show or hide an concrete piece, this parameter is obsolet because the piece is located out of board when it is removed.
	 */
	boolean visible; 

	/**
	 * piece name
	 */
	PieceName name; 

	/**
	 * piece color
	 */
	PieceColor color; 

	/**
	 * static list of all class instances
	 */
	protected static LinkedList<AbstractPiece> pieces = new LinkedList<AbstractPiece>();

	/**
	 * this is an default constructor for the all pieces 	 
	 * @param id
	 * @param posInit
	 * @param pos
	 * @param visible
	 * @param firstMove
	 * @param pieceIconLength
	 * @param addPieceToPieces : this is a boolean, to avoid adding new piece to map when its a promote
	 */
	public AbstractPiece(int id, Point posInit, Point pos, boolean visible, boolean firstMove, int pieceIconLength, boolean addPieceToPieces) { 
		this.pieceID = id;
		this.columnRow = posInit;
		this.position = pos;
		this.visible = visible; 
		this.firstMove = firstMove;
		this.pieceIconLength = pieceIconLength;
		if(addPieceToPieces)
			AbstractPiece.getPieces().add(this);  
	}
	/**
	 * method that indicates if an piece who can side move can successfully move by side range.
	 * @param x - target row
	 * @param y - target column
	 * @param pieceFound - is there a target piece found
	 * @param targetPiece - target piece instance
	 * @return true if successfully moved, false if not successful
	 */
	public boolean sideMove(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {

		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2];
		boolean canMove = false;
		Point sourcePiecePos = this.getColumnRow();
		int thisX = sourcePiecePos.x;
		int thisY = sourcePiecePos.y;

		x = x / this.pieceIconLength + 1;
		y = y / this.pieceIconLength + 1;


		if (thisY == y) {	// movement vertical
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
		if (thisX == x) {	// movement horizontal
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
					if (tempCoor[0] == allPieces[j].getColumnRow().x && 
							tempCoor[1] ==  allPieces[j].getColumnRow().y)
						canMove = false;  
				}
			}
		} 

		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 
				canMove = this.canMoveByColour(targetPiece);
			}
		}
		return canMove;
	}

	/**
	 * method that indicates if an piece who can side move can successfully move by diagonal range.
	 * @param x - target row
	 * @param y - target column
	 * @param pieceFound - is there a target piece found
	 * @param targetPiece - target piece instance
	 * @return true if successfully moved, false if not successful
	 */
	protected boolean diagonalMove(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {

		int moveCase = 0, length = 0;
		int tempCoor[] = new int[2]; 
		boolean canMove = false; 

		y = y / this.pieceIconLength + 1;
		x = x / this.pieceIconLength + 1; 

		Point sourcePiecePos = this.getColumnRow();

		int thisX = sourcePiecePos.x;
		int thisY = sourcePiecePos.y;

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
					if (tempCoor[0] == allPieces[j].getColumnRow().x && 
							tempCoor[1] ==  allPieces[j].getColumnRow().y)
						canMove = false; 
				}
			}
		}

		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 
				canMove = this.canMoveByColour(targetPiece);
			}
		}  
		return canMove;  
	}

	/**
	 * this updates the diagonal square matrix by parameter
	 * @param tempCoor -  matrix target coordinate
	 * @param oldX - actual square row
	 * @param oldY - actual square column
	 * @param squares - square map modifier
	 */
	public void diagonalSquareAttack(int tempCoor[], int oldX, int oldY, int[][] squares) {

		int indexCompare = 0;
		int indexSum = 0;

		switch (this.getColor()){
		case WHITE:
			indexCompare = 2;
			indexSum = 1;
			break;
		case BLACK: 
			indexCompare = 1;
			indexSum = 2;
			break;
		default:
			System.out.println("Color not expected ");
			break;
		}

		int x, y;
		boolean piecethere;
		for (int k = 0; k < 4; k++) {
			piecethere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceDiagCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1]; 
					for(AbstractPiece p : AbstractPiece.pieces)
						if (p.getColumnRow().x - 1 == x
						&& p.getColumnRow().y - 1 == y)
							piecethere = true;
					if (squares[x][y] == indexCompare)
						squares[x][y] += indexSum;
					else
						squares[x][y] = indexSum;
					if (piecethere)
						break here;
				}
			}
			tempCoor[0] = oldX;
			tempCoor[1] = oldY;
		}

	}

	/**
	 * check by side if any opposite king is checked by this piece
	 * @param control - control case check
	 * @param piece - this piece
	 * @param king - target king
	 * @return true if target king is checked, false not checked
	 */
	protected boolean sideCheck(int control, AbstractPiece piece, AbstractPiece king) {

		int length = 0;
		int tempCoor[] = new int[2]; 

		// the the rook and the king are on the same column substract rows
		if (control == 0 || control == 2)
			length = Math.abs(king.getColumnRow().x - piece.getColumnRow().x);
		if (control == 1 || control == 3)
			length = Math.abs(king.getColumnRow().y - piece.getColumnRow().y);
		tempCoor[0] = piece.getColumnRow().x;
		tempCoor[1] = piece.getColumnRow().y;
		for (int i = 0; i < length - 1; i++) {
			pieceSideCoor(tempCoor, control);

			for(AbstractPiece p : AbstractPiece.pieces){ // si hi han peces entre mig no hiha checkmate!
				if(tempCoor[0] == p.getColumnRow().x && tempCoor[1] == p.getColumnRow().y)
					return false;
			} 
		} 

		return true;
	}

	/**
	 * check by diagonal if any opposite king is checked by this piece
	 * @param control - control case check
	 * @param piece - this piece
	 * @param king - target king
	 * @return true if target king is checked, false not checked
	 */
	protected boolean diagonalCheck(int control, AbstractPiece piece, AbstractPiece king) {

		int length = 0;
		int tempCoor[] = new int[2]; 

		length = Math.abs(king.getColumnRow().x - piece.getColumnRow().x);
		tempCoor[0] = king.getColumnRow().x;
		tempCoor[1] = king.getColumnRow().y;

		for (int i = 0; i < length - 1; i++) {
			pieceDiagCoor(tempCoor, control);

			for(AbstractPiece p : AbstractPiece.pieces){ // si hi han peces entre mig no hiha checkmate!
				if(tempCoor[0] == p.getColumnRow().x && tempCoor[1] == p.getColumnRow().y)
					return false;
			} 
		}

		return true; 
	}

	/**
	 * this updates the side square matrix by parameter
	 * @param tempCoor - matrix target coordinate
	 * @param oldX - actual square row
	 * @param oldY - actual square column
	 * @param squares - square map modifier
	 */
	public void sideSquareAttack(int tempCoor[], int oldX, int oldY, int[][] squares) {

		int indexCompare = 0;
		int indexSum = 0;

		switch (this.getColor()){
		case WHITE:
			indexCompare = 2;
			indexSum = 1;
			break;
		case BLACK: 
			indexCompare = 1;
			indexSum = 2;
			break;
		default:
			System.out.println("Color not expected ");
			break;
		}

		int x, y;

		boolean pieceThere;
		for (int k = 0; k < 4; k++) {
			pieceThere = false;
			here: for (int l = 0; l < 7; l++) {
				if (pieceSideCoor(tempCoor, k)) {
					x = tempCoor[0];
					y = tempCoor[1];
					for(AbstractPiece p : AbstractPiece.pieces)
						if (p.getColumnRow().x - 1 == x
						&& p.getColumnRow().y - 1 == y)
							pieceThere = true;
					if (squares[x][y] == indexCompare)
						squares[x][y] += indexSum;
					else
						squares[x][y] = indexSum;
					if (pieceThere)
						break here;
				}
			}
			tempCoor[0] = oldX;
			tempCoor[1] = oldY;
		}
	}

	/**
	 * update side target able coorPosition by piece
	 * @param tempCoor - target self position 
	 * @param moveCase - target orientation case
	 * @return true if can target, else can't target because its outside target range
	 */
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

	/**
	 * update diagonal target able coorPosition by piece
	 * @param tempCoor - target self position 
	 * @param moveCase - target orientation case
	 * @return true if can target, else can't target because its outside target range
	 */
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


	/**
	 * getKing by color by paremeter
	 * @param color - PieceColor
	 * @return piece -  colorWhite return whiteKing else if colorBlack return blackKing
	 */
	public AbstractPiece getKing(PieceColor color){
		for(AbstractPiece p : AbstractPiece.pieces){
			if( p.getName().equals(PieceName.KING) && p.getColor().equals(color))
				return p;
		}
		return null;
	} 

	/**
	 * check if can move to target able piece by color
	 * @param target - targetPiece
	 * @return true if different color, else false
	 */
	public boolean canMoveByColour(AbstractPiece target){ 
		if (this.getColor().equals(target.getColor()))
			return false; 
		return true;
	}

	/**
	 * check if can move by specific target position
	 * @param targetablePoints : list of target able positions
	 * @return true if target position exist else false
	 */
	public boolean canMoveByTargetPosition(Point[] targetablePoints){ 
		for(Point p : targetablePoints) { 
			if(this.getPosition().x / this.getPieceIconLength() == p.x &&
					this.getPosition().y / this.getPieceIconLength() == p.y )
			{	 
				return true; 
			}
		}  
		return false;
	}




	/* ************************** */
	/* DEFAULT GETTER AND SETTERS */
	/* ************************** */

	/**
	 * get the piece unique id
	 * @return pieceID
	 */
	public int getPieceID() {
		return pieceID;
	}

	/**
	 * set the piece unique id
	 * @param pieceID
	 */
	public void setPieceID(int pieceID) {
		this.pieceID = pieceID;
	}

	/**
	 * get piece column and row point
	 * @return columnRow point
	 */
	public Point getColumnRow() {
		return columnRow;
	}

	/**
	 * set piece positionInit
	 * @param positionInit
	 */
	public void setColumnRow(Point positionInit) {
		this.columnRow = positionInit;
	}

	/**
	 * get piece position
	 * @return position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * set piece position
	 * @param position 
	 */
	public void setPosition(Point position) {
		this.position = position;
	}

	/**
	 * get piece icon
	 * @return pieceIcon
	 */
	public Image getPieceIcon() {
		return pieceIcon;
	}

	/**
	 * set piecce icon
	 * @param pieceIcon
	 */
	public void setPieceIcon(Image pieceIcon) {
		this.pieceIcon = pieceIcon;
	}

	/**
	 * check if piece is visible
	 * @return visible :  boolean
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * set piece visibility
	 * @param visible 
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * get piece name
	 * @return name
	 */
	public PieceName getName() {
		return name;
	}

	/**
	 * set piece name
	 * @param name
	 */
	public void setName(PieceName name) {
		this.name = name;
	}

	/**
	 * get piece color
	 * @return color : PieceColor
	 */
	public PieceColor getColor() {
		return color;
	}

	/**
	 * set piece color
	 * @param color 
	 */
	public void setColor(PieceColor color) {
		this.color = color;
	}

	/**
	 * check if is piece first move
	 * @return firstMove
	 */
	public boolean isFirstMove() {
		return firstMove;
	}

	/**
	 * set if is piece first move
	 * @param firstMove
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	/**
	 * get the piece icon length 
	 * @return piece icon length
	 */
	public int getPieceIconLength() {
		return pieceIconLength;
	}

	/**
	 * set the piece icon length
	 * @param pieceIconLength 
	 */
	public void setPieceIconLength(int pieceIconLength) {
		this.pieceIconLength = pieceIconLength;
	}

	/**
	 * set all piece instances
	 * @return pieces : linkedList of all class instances
	 */
	public static LinkedList<AbstractPiece> getPieces() {
		return pieces;
	}


}
