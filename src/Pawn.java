

import java.awt.Point;
 

 
public class Pawn extends AbstractPiece{

	
	
	public Pawn(int id, Point posInit, Point pos, boolean visible,
			boolean firstMove, int iconLength) {
		super(id, posInit, pos, visible, firstMove, iconLength);
		// TODO Auto-generated constructor stub
	}
 
	boolean promotion;
	 
	@Override
	public boolean pieceRules(int x, int y, boolean pieceFound, AbstractPiece targetPiece) {
		

		System.out.println("_Pawn rules!");

		boolean canMove = false;
				
		int pieceNum = targetPiece == null ? 40 : targetPiece.getPieceID();
		// *************************************
		// logic for pawn
		// doesn't handle last row
		
		System.out.println("TargetPiece: "+pieceNum);
		
		if (this.getName().equals(PieceName.PAWN)) {
			y = y / this.getPieceIconLength();
			x = x / this.getPieceIconLength();
			
			int thisX = this.getPosition().x/ this.getPieceIconLength();
			int thisY = this.getPosition().y/ this.getPieceIconLength();
			
			System.out.println(this.isFirstMove()+" Positions: "+thisX +","+thisY+" : "+x+","+y);
			
			if (this.getColor().equals(PieceColor.WHITE)) {
				if (thisX == x) {
					// generic white pawn move
					if (thisY == y + 1) {
						// detects if there isn't a piece
						if (!pieceFound) // can move
						{
							this.setFirstMove(false);
							canMove = true; 
							System.out.println("w is move1");
						}
					}
					// exception for the first move to move two places
					if (thisY == y + 2
							&& this.isFirstMove()) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true;
							System.out.println("w is move2");
						}
					}
				}
				// handles white pawn taking pieces diagonally
				else if (thisX == x + 1
						|| thisX == x - 1) {
					if (thisY == y + 1) {
						if (pieceNum < 16) {
							if (pieceFound) {
								this.setFirstMove(false);
								canMove = true;

								System.out.println("w is attacking");
							}
						}
					}
				}
				if (canMove && y == 0)
					promotion = true;
			}else{
				System.out.println("is not white");
			}
			
			
			if (this.getColor().equals(PieceColor.BLACK)) {
				if (thisX == x) {
					if (thisY == y - 1) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true;
							System.out.println("b is move1");
						}
					}
					if (thisY == y - 2
							&& this.isFirstMove()) {
						if (!pieceFound) {
							this.setFirstMove(false);
							canMove = true;
							System.out.println("b is move2");
						}
					}
				}
				// handles black pawn taking pieces diagonally
				else if (thisX == x + 1
						|| thisX == x - 1) {
					if (thisY == y - 1) {
						if (pieceNum < 32 && pieceNum > 15) {
							if (pieceFound) {
								this.setFirstMove(false);
								canMove = true;
								System.out.println("b is attacking");
							}
						}
					}
				}
				if (canMove && y == 7)
					promotion = true;
			}else{
				System.out.println("is not black");
			}
			
		}

		// ************************************
		if (canMove) {
			if (pieceFound) {
				if (targetPiece.getName().equals(PieceName.KING)) 
					return false; 
				 
			}
		}
		System.out.println(" CanMove? "+canMove+" isPromote? "+promotion);				
		return canMove;
	}
 

	@Override
	public boolean handlePieceCheckMate(){
		boolean checked = false;
		AbstractPiece blackKing = getKing(PieceColor.BLACK); // 4 black
		AbstractPiece whiteKing = getKing(PieceColor.WHITE); // 28 white
		AbstractPiece whichKing = null;
		
		int bKingPosX = blackKing.getPosition().x / this.getPieceIconLength();
		int bKingPosY = blackKing.getPosition().y / this.getPieceIconLength();
		
		int wKingPosX = whiteKing.getPosition().x / this.getPieceIconLength();
		int wKingPosY = whiteKing.getPosition().y / this.getPieceIconLength();
		
		int thisPosX = this.getPosition().x / this.getPieceIconLength();
		int thisPosY = this.getPosition().y / this.getPieceIconLength();
		
	//	System.out.println(" tP "+this.getPosition()+" bK "+blackKing.getPosition()+ " wK "+whiteKing.getPosition());
		
		if (this.getColor().equals(PieceColor.WHITE)) {
			if ( bKingPosX == thisPosX - 1
					&& bKingPosY == thisPosY + 1) {
				checked = true;
				whichKing = blackKing;
			}
			if (bKingPosX == thisPosX - 1
					&& bKingPosY == thisPosY - 1) {
				checked = true;
				whichKing = blackKing;
			}
		}
		if (this.getColor().equals(PieceColor.BLACK)) {
			if (wKingPosX == thisPosX + 1
					&& wKingPosY == thisPosY + 1) {
				checked = true; 
				whichKing = whiteKing;
			}
			if (wKingPosX == thisPosX + 1
					&& wKingPosY == thisPosY - 1) {
				checked = true; 
				whichKing = whiteKing;
			}
		}
		System.out.println("_Pawn: \t\t"+this.getPieceID()+" isChecked? "+checked+ " who? "+whichKing);
		return checked;
	}


	@Override
public boolean squaresAttacked(int[][] squares) { 
		
		System.out.println("Pawn squaresAttacked!");

		int x = this.getPosition().x / this.getPieceIconLength();
		int y = this.getPosition().y / this.getPieceIconLength();
		
		
		System.out.println("x:y  -> " +x+":"+y);
		
		/*
		if ((this.getColor().equals(PieceColor.WHITE)) && x != 0) {
				if (y == 0) // to handle pawns on the first
											// column
				{
					if (squares[x - 2][y] == 2)
						squares[x - 2][y] += 1;
					else
						squares[x - 2][y] = 1;
				} else if (y == 7) // to handle pawns on the
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
			if ((this.getColor().equals(PieceColor.BLACK)) && x != 7) {
				if (y == 0) // to handle pawns on the first
											// column
				{
					if (squares[x][y] == 1)
						squares[x][y] += 2;
					else
						squares[x][y] = 2;
				} else if (y == 7) // to handle pawns on the
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
		 
		*/
			return true;
		
	}
	 




}
