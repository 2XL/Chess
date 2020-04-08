package origin;

import java.awt.*; 
import java.awt.event.*;

import javax.swing.*;

public class chess32 extends java.applet.Applet implements MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// changed class structure to allow globals declared
	// here to determine what square on the board has
	// been clicked | 1/24/05
	class piece // this allows us to track all the information
	{ // about the pieces
		int IDNum;
		int row; // int row;
		int column; // int column;
		int xposition;
		int yposition;
		Image mypic; // Image mypic
		boolean visible;// boolean visible //counts as taken
		boolean firstmove;
		String color; // String color;
		String name; // String name;
	};

	// piece 0 at 0,0 is black rook, piece 31 at 7,7 is white rook
	// using top to bottom, and left to right to indentify pieces
	public static piece[] pieces = new piece[32]; // allows us use specific
													// pieces

	// globals
	public static int ChessBoard[][] = new int[8][8];
	static int counter = 0;
	boolean ok = false; // this is for seeing if its ok to move
	int pieceChosen = 0; // this identifies which piece to move
	int numclicks = 0;
	int oldx;
	int oldy;
	int squares[][] = new int[12][12];
	boolean checked; // this is used for the JOptionPane message "check"
	int whichKing; // this is used to reduce the amount of code for BishopCheck
	// int whos_turn; // used to alternate turns
	boolean promotion; // used to promote pawns

	public void GetFileAndName(int rowIndex, int colIndex) {
		String fileName;
		String blackName = "brbnbbbqbk";
		String whiteName = "wrwnwbwqwk";
		String tempName = "";
		String fileExt = ".gif";
		String filePackagePath = "media/";
		if (rowIndex == 1)
			tempName = blackName;
		else if (rowIndex == 8)
			tempName = whiteName;
		if (rowIndex == 2 || rowIndex == 7) {
			pieces[counter].name = "Pawn";
			if (rowIndex == 2)
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+"bp.gif");
			else
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+"wp.gif");
		} else {
			switch (colIndex) {
			case 1:
			case 8: {
				fileName = tempName.substring(0, 2) + fileExt;
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+fileName);
				pieces[counter].name = "Rook";
				break;
			}
			case 2:
			case 7: {
				fileName = tempName.substring(2, 4) + fileExt;
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+fileName);
				pieces[counter].name = "Knight";
				break;
			}
			case 3:
			case 6: {
				fileName = tempName.substring(4, 6) + fileExt;
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+fileName);
				pieces[counter].name = "Bishop";
				break;
			}
			case 4: {
				fileName = tempName.substring(6, 8) + fileExt;
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+fileName);
				pieces[counter].name = "Queen";
				break;
			}
			case 5: {
				fileName = tempName.substring(8, 10) + fileExt;
				pieces[counter].mypic = getImage(getCodeBase(), filePackagePath+fileName);
				pieces[counter].name = "King";
				break;
			}
			}
		}
	}

	public void init() {
		// this declares the pieces, can't use them without this
		for (int i = 0; i < 32; i++) {
			pieces[i] = new piece();
		}
		// This puts pieces in their original settings in the class
		// top left corner is [0][0]
		// bottom right is [7][7]
		int rowArray[] = { 1, 2, 7, 8 };
		for (int k = 0; k < 4; k++) {
			for (int l = 0; l < 8; l++) {
				pieces[counter].IDNum = counter;
				pieces[counter].row = rowArray[k];
				pieces[counter].column = l + 1;
				pieces[counter].xposition = 0;
				pieces[counter].yposition = 0;
				pieces[counter].visible = true;
				pieces[counter].firstmove = true;
				if (rowArray[k] < 3)
					pieces[counter].color = "black";
				else
					pieces[counter].color = "white";
				GetFileAndName(rowArray[k], l + 1);
				counter++;
			}
		}
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				squares[i][j] = 0;
		addMouseListener(new MousePressListener());
		addMouseMotionListener(this);
		// whos_turn = 0; // used to alternate turns
		checked = false;
		promotion = false;
	}

	public int rowToX(int r) {
		int myx;
		int iHeight = getHeight();
		myx = r * iHeight / 8 - 50;
		return myx;
	}

	public int colToY(int c) {
		int myy;
		int iWidth = getWidth();
		myy = c * iWidth / 8 - 50;
		return myy;
	}

	public void paint(Graphics g) {
		setSize(400, 400);

		int iWidth = getWidth();
		int iHeight = getHeight();
		for (int i = 0; i < 8; i = i + 2) {
			for (int j = 0; j < 8; j = j + 2) {
				g.setColor(Color.red);
				g.fillRect(j * iWidth / 8, (1 + i) * iWidth / 8, iWidth / 8,
						iHeight / 8);
				g.fillRect((1 + j) * iWidth / 8, i * iWidth / 8, iWidth / 8,
						iHeight / 8);
			}
		}
		
		for (int i = 0; i < 32; i++) {
			pieces[i].xposition = rowToX(pieces[i].column);
			pieces[i].yposition = colToY(pieces[i].row);
			if (pieces[i].visible)
				g.drawImage(pieces[i].mypic, pieces[i].xposition,
						pieces[i].yposition, iWidth / 8, iHeight / 8, this);
		}
		
		if (ok) {
			g.setColor(Color.green.darker());
			g.drawRect(pieces[pieceChosen].xposition,
					pieces[pieceChosen].yposition, 50, 50);
			g.setColor(Color.green.brighter().brighter());
			g.drawRect(pieces[pieceChosen].xposition + 1,
					pieces[pieceChosen].yposition + 1, 48, 48);
			g.setColor(Color.green.darker());
			g.drawRect(pieces[pieceChosen].xposition + 2,
					pieces[pieceChosen].yposition + 2, 46, 46);
		}
	}

	public void mouseMoved(MouseEvent event) {
	}

	public void mouseDragged(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();

		oldx = x;
		oldy = y;
	}

	
	
	
	
	
	
	
	
	
	class MousePressListener extends MouseAdapter {
		
		
		
		
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			String temp;
			
			
			temp = Integer.toString(y / 50 + 1);
			System.out.println("rawx = " + temp);
			temp = Integer.toString(x / 50 + 1);
			System.out.println("rawy = " + temp);
			
			
			
			numclicks = (numclicks + 1) % 2;
			// this way we can move pieces with alternating mouse clicks
			// when ok = true movement takes place

			if (ok)
				MovePiece(x, y);
			else
				// when ok = false we look for a piece
				BoardID(x, y);
			repaint();
		}

		
		
		
		
		
		
		
		
		public void BoardID(int x, int y) {
			for (int i = 0; i < 32; i++) {
				if (((y / 50) + 1 == pieces[i].row)
						&& ((x / 50) + 1 == pieces[i].column)) {
					pieceChosen = i; // this identifies which piece has been
										// selected
					// if (whos_turn == 0 &&
					// pieces[pieceChosen].color.equals("white"))
					ok = true; // this says we found a white piece and we are
								// ready to move it
					// if (whos_turn == 1 &&
					// pieces[pieceChosen].color.equals("black"))
					ok = true; // this says we found a black piece and we are
								// ready to move it
				}
			}
		}

		// y controls columns x controls row
		public boolean KingRules(int y, int x, boolean piecefound, int pieceNum) {
			String color1, color2;
			boolean canmove = false;

			if (pieces[pieceChosen].name.equals("King")) {
				y = y / 50 + 1;
				x = x / 50 + 1;
				if (pieces[pieceChosen].column == y - 1
						&& pieces[pieceChosen].row == x - 1)
					canmove = true;
				else if (pieces[pieceChosen].column == y
						&& pieces[pieceChosen].row == x - 1)
					canmove = true;
				else if (pieces[pieceChosen].column == y + 1
						&& pieces[pieceChosen].row == x - 1)
					canmove = true;
				else if (pieces[pieceChosen].column == y - 1
						&& pieces[pieceChosen].row == x)
					canmove = true;
				else if (pieces[pieceChosen].column == y + 1
						&& pieces[pieceChosen].row == x)
					canmove = true;
				else if (pieces[pieceChosen].column == y - 1
						&& pieces[pieceChosen].row == x + 1)
					canmove = true;
				else if (pieces[pieceChosen].column == y
						&& pieces[pieceChosen].row == x + 1)
					canmove = true;
				else if (pieces[pieceChosen].column == y + 1
						&& pieces[pieceChosen].row == x + 1)
					canmove = true;
				// conditions for castling - first move, no pieces in the way,
				// no squares under attack
				if (canmove) {
					if (piecefound) {
						color1 = pieces[pieceChosen].color;
						color2 = pieces[pieceNum].color;
						if (color1.equals(color2))
							canmove = false;
					}
				}
			}
			if (canmove)
				pieces[pieceChosen].firstmove = false;
			return canmove;
		}

		public boolean KingCoor(int tempcoor[], int movecase) {
			boolean moveok = false;
			switch (movecase) {
			case 0:
				tempcoor[0]--;
				tempcoor[1]--;
				break;
			case 1:
				tempcoor[0]--;
				break;
			case 2:
				tempcoor[0]++;
				tempcoor[1]--;
				break;
			case 3:
				tempcoor[1]--;
				break;
			case 4:
				tempcoor[1]++;
				break;
			case 5:
				tempcoor[0]--;
				tempcoor[1]++;
				break;
			case 6:
				tempcoor[0]++;
				break;
			case 7:
				tempcoor[0]++;
				tempcoor[1]++;
				break;
			}
			if (tempcoor[0] >= 0 && tempcoor[0] < 9)
				if (tempcoor[1] >= 0 && tempcoor[1] < 9)
					moveok = true;
			return moveok;
		}

		public boolean KnightRules(int y, int x, boolean piecefound,
				int pieceNum) {
			boolean canmove = false;
			String color1, color2;

			if (pieces[pieceChosen].name.equals("Knight")) {
				y = y / 50 + 1;
				x = x / 50 + 1;
				if (((pieces[pieceChosen].row == x - 1) || (pieces[pieceChosen].row == x + 1))
						&& ((pieces[pieceChosen].column == y + 2) || (pieces[pieceChosen].column == y - 2)))
					canmove = true;
				if ((pieces[pieceChosen].row == x - 2 || pieces[pieceChosen].row == x + 2)
						&& ((pieces[pieceChosen].column == y + 1) || (pieces[pieceChosen].column == y - 1)))
					canmove = true;
				if (canmove) {
					if (piecefound) {
						color1 = pieces[pieceChosen].color;
						color2 = pieces[pieceNum].color;
						if (color1.equals(color2))
							canmove = false;
					}
				}
			}
			if (pieceNum != 40)
				if (pieces[pieceNum].name.equals("King"))
					canmove = false;
			return canmove;
		}

		public void KnightCoor(int tempcoor[], int movecase) {
			switch (movecase) {
			case 0:
				tempcoor[0] -= 2;
				tempcoor[1] += 1;
				break;
			case 1:
				tempcoor[0] -= 1;
				tempcoor[1] += 2;
				break;
			case 2:
				tempcoor[0] += 1;
				tempcoor[1] += 2;
				break;
			case 3:
				tempcoor[0] += 2;
				tempcoor[1] += 1;
				break;
			case 4:
				tempcoor[0] += 2;
				tempcoor[1] -= 1;
				break;
			case 5:
				tempcoor[0] += 1;
				tempcoor[1] -= 2;
				break;
			case 6:
				tempcoor[0] -= 1;
				tempcoor[1] -= 2;
				break;
			case 7:
				tempcoor[0] -= 2;
				tempcoor[1] -= 1;
				break;
			}
		}

		public boolean BishopRules(int y, int x, boolean piecefound,
				int pieceNum) {
			boolean canmove = false;
			if (pieces[pieceChosen].name.equals("Bishop"))
				canmove = DiagonalMove(x, y, piecefound, pieceNum);
			return canmove;
		}

		public boolean DiagonalMove(int x, int y, boolean piecefound,
				int pieceNum) {
			int movecase = 0, length = 0;
			int tempcoor[] = new int[2];
			String color1, color2;
			boolean canmove = false;
			y = y / 50 + 1;
			x = x / 50 + 1;
			if (pieces[pieceChosen].row - x == pieces[pieceChosen].column - y) {
				// case 1 up to the left
				if (pieces[pieceChosen].row - x > 0
						&& pieces[pieceChosen].column - y > 0) {
					length = pieces[pieceChosen].row - x;
					movecase = 0;
					canmove = true;
				}
			}
			if (pieces[pieceChosen].row - x == y - pieces[pieceChosen].column) {
				// case 2 up to the right
				if (pieces[pieceChosen].row - x > 0
						&& y - pieces[pieceChosen].column > 0) {
					length = pieces[pieceChosen].row - x;
					movecase = 1;
					canmove = true;
				}
			}
			if (x - pieces[pieceChosen].row == y - pieces[pieceChosen].column) {
				// case 3 down to the right
				if (x - pieces[pieceChosen].row > 0
						&& y - pieces[pieceChosen].column > 0) {
					length = x - pieces[pieceChosen].row;
					movecase = 2;
					canmove = true;
				}
			}
			if (x - pieces[pieceChosen].row == pieces[pieceChosen].column - y) {
				// case 4 down to the left
				if (x - pieces[pieceChosen].row > 0
						&& pieces[pieceChosen].column - y > 0) {
					length = x - pieces[pieceChosen].row;
					movecase = 3;
					canmove = true;
				}
			}
			if (canmove) {
				tempcoor[0] = pieces[pieceChosen].row;
				tempcoor[1] = pieces[pieceChosen].column;
				for (int i = 0; i < length - 1; i++) {
					BishopCoor(tempcoor, movecase);
					for (int j = 0; j < 32; j++)
						if (tempcoor[0] == pieces[j].row
								&& tempcoor[1] == pieces[j].column)
							canmove = false;
				}
			}
			if (canmove) {
				if (piecefound) {
					color1 = pieces[pieceChosen].color;
					color2 = pieces[pieceNum].color;
					if (color1.equals(color2))
						canmove = false;
				}
			}
			if (pieceNum != 40)
				if (pieces[pieceNum].name.equals("King"))
					canmove = false;
			return canmove;
		}

		public boolean BishopCoor(int coor[], int movecase) {
			boolean moveok = false;
			switch (movecase) {
			case 0: // up to the left
				coor[0]--;
				coor[1]--;
				break;
			case 1: // up to the right
				coor[0]--;
				coor[1]++;
				break;
			case 2: // down to the right
				coor[0]++;
				coor[1]++;
				break;
			case 3: // down to the left
				coor[0]++;
				coor[1]--;
				break;
			}
			if (coor[0] >= 0 && coor[0] < 9)
				if (coor[1] >= 0 && coor[1] < 9)
					moveok = true;
			return moveok;
		}

		public boolean RookRules(int y, int x, boolean piecefound, int pieceNum) {
			boolean canmove = false;
			if (pieces[pieceChosen].name.equals("Rook"))
				canmove = SideMove(x, y, piecefound, pieceNum);
			if (canmove)
				pieces[pieceChosen].firstmove = false;
			return canmove;
		}

		public boolean SideMove(int x, int y, boolean piecefound, int pieceNum) {
			int movecase = 0, length = 0;
			int tempcoor[] = new int[2];
			boolean canmove = false;
			String color1, color2;
			y = y / 50 + 1;
			x = x / 50 + 1;
			if (pieces[pieceChosen].column == y) {
				// case 0 up
				if (pieces[pieceChosen].row - x > 0) {
					length = pieces[pieceChosen].row - x;
					movecase = 0;
					canmove = true;
				}
				// case 2 down
				if (x - pieces[pieceChosen].row > 0) {
					length = x - pieces[pieceChosen].row;
					movecase = 2;
					canmove = true;
				}
			}
			if (pieces[pieceChosen].row == x) {
				// case 1 right
				if (y - pieces[pieceChosen].column > 0) {
					length = y - pieces[pieceChosen].column;
					movecase = 1;
					canmove = true;
				}
				// case 3 left
				if (pieces[pieceChosen].column - y > 0) {
					length = pieces[pieceChosen].column - y;
					movecase = 3;
					canmove = true;
				}
			}
			if (canmove) {
				tempcoor[0] = pieces[pieceChosen].row;
				tempcoor[1] = pieces[pieceChosen].column;
				for (int i = 0; i < length - 1; i++) {
					RookCoor(tempcoor, movecase);
					for (int j = 0; j < 32; j++)
						if (tempcoor[0] == pieces[j].row
								&& tempcoor[1] == pieces[j].column)
							canmove = false;
				}
			}
			if (canmove) {
				if (piecefound) {
					color1 = pieces[pieceChosen].color;
					color2 = pieces[pieceNum].color;
					if (color1.equals(color2))
						canmove = false;
				}
			}
			if (pieceNum != 40)
				if (pieces[pieceNum].name.equals("King"))
					canmove = false;
			return canmove;
		}

		public boolean RookCoor(int tempcoor[], int movecase) {
			boolean moveok = false;
			switch (movecase) {
			case 0:
				tempcoor[0]--;
				break;
			case 1:
				tempcoor[1]++;
				break;
			case 2:
				tempcoor[0]++;
				break;
			case 3:
				tempcoor[1]--;
				break;
			}
			if (tempcoor[0] >= 0 && tempcoor[0] < 9)
				if (tempcoor[1] >= 0 && tempcoor[1] < 9)
					moveok = true;
			return moveok;
		}

		public boolean QueenRules(int y, int x, boolean piecefound, int pieceNum) {
			boolean canmove = false;
			if (pieces[pieceChosen].name.equals("Queen"))
				canmove = SideMove(x, y, piecefound, pieceNum)
						^ DiagonalMove(x, y, piecefound, pieceNum);
			return canmove;
		}

		public boolean PawnRules(int y, int x, boolean piecefound, int pieceNum) {
			boolean canmove = false;

			// *************************************
			// logic for pawn
			// doesn't handle last row
			if (pieces[pieceChosen].name.equals("Pawn")) {
				y = y / 50 + 1;
				x = x / 50 + 1;
				if (pieces[pieceChosen].color.equals("white")) {
					if (pieces[pieceChosen].column == y) {
						// generic white pawn move
						if (pieces[pieceChosen].row == x + 1) {
							// detects if there isn't a piece
							if (!piecefound) // can move
							{
								pieces[pieceChosen].firstmove = false;
								canmove = true;
							}
						}
						// exception for the first move to move two places
						if (pieces[pieceChosen].row == x + 2
								&& pieces[pieceChosen].firstmove) {
							if (!piecefound) {
								pieces[pieceChosen].firstmove = false;
								canmove = true;
							}
						}
					}
					// handles white pawn taking pieces diagonally
					else if (pieces[pieceChosen].column == y + 1
							|| pieces[pieceChosen].column == y - 1) {
						if (pieces[pieceChosen].row == x + 1) {
							if (pieceNum < 16) {
								if (piecefound) {
									pieces[pieceChosen].firstmove = false;
									canmove = true;
								}
							}
						}
					}
					if (canmove && x == 1)
						promotion = true;
				}
				if (pieces[pieceChosen].color.equals("black")) {
					if (pieces[pieceChosen].column == y) {
						if (pieces[pieceChosen].row == x - 1) {
							if (!piecefound) {
								pieces[pieceChosen].firstmove = false;
								canmove = true;
							}
						}
						if (pieces[pieceChosen].row == x - 2
								&& pieces[pieceChosen].firstmove) {
							if (!piecefound) {
								pieces[pieceChosen].firstmove = false;
								canmove = true;
							}
						}
					}
					// handles black pawn taking pieces diagonally
					else if (pieces[pieceChosen].column == y + 1
							|| pieces[pieceChosen].column == y - 1) {
						if (pieces[pieceChosen].row == x - 1) {
							if (pieceNum < 32 && pieceNum > 15) {
								if (piecefound) {
									pieces[pieceChosen].firstmove = false;
									canmove = true;
								}
							}
						}
					}
					if (canmove && x == 8)
						promotion = true;
				}
			}

			// ************************************
			if (pieceNum != 40)
				if (pieces[pieceNum].name.equals("King"))
					canmove = false;
			return canmove;
		}

		public boolean CheckMethod() {
			// have to handle white putting white in check
			// have to handle black putting white in check
			// and vice versa

			for (int i = 0; i < 32; i++) {
				HandlePieceType(pieces[i].color, pieces[i].name, i);
				if (checked)
					break;
			}
			return checked;
		}

		// black king = 4 white king = 28
		public void HandlePieceType(String mycolor, String myname, int index) {
			
			
			
			
			if (myname.equals("Pawn") && mycolor.equals("white")) {
				if (pieces[4].row == pieces[index].row - 1
						&& pieces[4].column == pieces[index].column + 1) {
					checked = true;
					whichKing = 4;
				}
				if (pieces[4].row == pieces[index].row - 1
						&& pieces[4].column == pieces[index].column - 1) {
					checked = true;
					whichKing = 4;
				}
			}
			if (myname.equals("Pawn") && mycolor.equals("black")) {
				if (pieces[28].row == pieces[index].row + 1
						&& pieces[28].column == pieces[index].column + 1) {
					checked = true;
					whichKing = 28;
				}
				if (pieces[28].row == pieces[index].row + 1
						&& pieces[28].column == pieces[index].column - 1) {
					checked = true;
					whichKing = 28;
				}
			}
			
			
			
			
			
			
			if (myname.equals("Knight") && mycolor.equals("white")) {
				if ((pieces[4].row == pieces[index].row - 1 || pieces[4].row == pieces[index].row + 1)
						&& (pieces[4].column == pieces[index].column - 2 || pieces[4].column == pieces[index].column + 2)) {
					checked = true;
					whichKing = 4;
				}
				if ((pieces[4].row == pieces[index].row - 2 || pieces[4].row == pieces[index].row + 2)
						&& (pieces[4].column == pieces[index].column - 1 || pieces[4].column == pieces[index].column + 1)) {
					checked = true;
					whichKing = 4;
				}
			}
			if (myname.equals("Knight") && mycolor.equals("black")) {
				if ((pieces[28].row == pieces[index].row - 1 || pieces[28].row == pieces[index].row + 1)
						&& (pieces[28].column == pieces[index].column - 2 || pieces[28].column == pieces[index].column + 2)) {
					checked = true;
					whichKing = 28;
				}
				if ((pieces[28].row == pieces[index].row - 2 || pieces[28].row == pieces[index].row + 2)
						&& (pieces[28].column == pieces[index].column - 1 || pieces[28].column == pieces[index].column + 1)) {
					checked = true;
					whichKing = 28;
				}
			}
			
			
			
			
			
			
			
			
			if (myname.equals("Bishop") && mycolor.equals("white")
					&& pieces[index].visible) {
				// case 0 up to the left
				if (pieces[4].row - pieces[index].row == pieces[4].column
						- pieces[index].column)
					if (pieces[4].row - pieces[index].row > 0
							&& pieces[4].column - pieces[index].column > 0)
						BishopCheck(0, index, 4);
				// case 1 up to the right
				if (pieces[4].row - pieces[index].row == pieces[index].column
						- pieces[4].column)
					if (pieces[4].row - pieces[index].row > 0
							&& pieces[index].column - pieces[4].column > 0)
						BishopCheck(1, index, 4);
				// case 2 down to the right
				if (pieces[index].row - pieces[4].row == pieces[index].column
						- pieces[4].column)
					if (pieces[index].row - pieces[4].row > 0
							&& pieces[index].column - pieces[4].column > 0)
						BishopCheck(2, index, 4);
				// case 3 down to the left
				if (pieces[index].row - pieces[4].row == pieces[4].column
						- pieces[index].column)
					if (pieces[index].row - pieces[4].row > 0
							&& pieces[4].column - pieces[index].column > 0)
						BishopCheck(3, index, 4);
			}
			if (myname.equals("Bishop") && mycolor.equals("black")
					&& pieces[index].visible) {
				// case 0 up to the left
				if (pieces[28].row - pieces[index].row == pieces[28].column
						- pieces[index].column)
					if (pieces[28].row - pieces[index].row > 0
							&& pieces[28].column - pieces[index].column > 0)
						BishopCheck(0, index, 28);
				// case 1 up to the right
				if (pieces[28].row - pieces[index].row == pieces[index].column
						- pieces[28].column)
					if (pieces[28].row - pieces[index].row > 0
							&& pieces[index].column - pieces[28].column > 0)
						BishopCheck(1, index, 28);
				// case 2 down to the right
				if (pieces[index].row - pieces[28].row == pieces[index].column
						- pieces[28].column)
					if (pieces[index].row - pieces[28].row > 0
							&& pieces[index].column - pieces[28].column > 0)
						BishopCheck(2, index, 28);
				// case 3 down to the left
				if (pieces[index].row - pieces[28].row == pieces[28].column
						- pieces[index].column)
					if (pieces[index].row - pieces[28].row > 0
							&& pieces[28].column - pieces[index].column > 0)
						BishopCheck(3, index, 28);
			}
			
			
			
			
			
			
			
			
			
			
			if (myname.equals("Rook") && mycolor.equals("white")) {
				if (pieces[index].column == pieces[4].column) {
					// up
					if (pieces[index].row - pieces[4].row > 0)
						RookCheck(0, index, 4);
					// down
					if (pieces[4].row - pieces[index].row > 0)
						RookCheck(2, index, 4);
				}
				if (pieces[index].row == pieces[4].row) {
					// right
					if (pieces[4].column - pieces[index].column > 0)
						RookCheck(1, index, 4);
					// left
					if (pieces[index].column - pieces[4].column > 0)
						RookCheck(3, index, 4);
				}
			}
			if (myname.equals("Rook") && mycolor.equals("black")) {
				if (pieces[index].column == pieces[28].column) {
					// up
					if (pieces[index].row - pieces[28].row > 0)
						RookCheck(0, index, 28);
					// down
					if (pieces[28].row - pieces[index].row > 0)
						RookCheck(2, index, 28);
				}
				if (pieces[index].row == pieces[28].row) {
					// right
					if (pieces[28].column - pieces[index].column > 0)
						RookCheck(1, index, 28);
					// left
					if (pieces[index].column - pieces[28].column > 0)
						RookCheck(3, index, 28);
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			if (myname.equals("Queen") && mycolor.equals("white")
					&& pieces[index].visible) {
				// case 0 up to the left
				if (pieces[4].row - pieces[index].row == pieces[4].column
						- pieces[index].column)
					if (pieces[4].row - pieces[index].row > 0
							&& pieces[4].column - pieces[index].column > 0)
						BishopCheck(0, index, 4);
				// case 1 up to the right
				if (pieces[4].row - pieces[index].row == pieces[index].column
						- pieces[4].column)
					if (pieces[4].row - pieces[index].row > 0
							&& pieces[index].column - pieces[4].column > 0)
						BishopCheck(1, index, 4);
				// case 2 down to the right
				if (pieces[index].row - pieces[4].row == pieces[index].column
						- pieces[4].column)
					if (pieces[index].row - pieces[4].row > 0
							&& pieces[index].column - pieces[4].column > 0)
						BishopCheck(2, index, 4);
				// case 3 down to the left
				if (pieces[index].row - pieces[4].row == pieces[4].column
						- pieces[index].column)
					if (pieces[index].row - pieces[4].row > 0
							&& pieces[4].column - pieces[index].column > 0)
						BishopCheck(3, index, 4);
				if (pieces[index].column == pieces[4].column) {
					// up
					if (pieces[index].row - pieces[4].row > 0)
						RookCheck(0, index, 4);
					// down
					if (pieces[4].row - pieces[index].row > 0)
						RookCheck(2, index, 4);
				}
				if (pieces[index].row == pieces[4].row) {
					// right
					if (pieces[4].column - pieces[index].column > 0)
						RookCheck(1, index, 4);
					// left
					if (pieces[index].column - pieces[4].column > 0)
						RookCheck(3, index, 4);
				}
			}
			
			if (myname.equals("Queen") && mycolor.equals("black")
					&& pieces[index].visible) {
				// case 0 up to the left
				if (pieces[28].row - pieces[index].row == pieces[28].column
						- pieces[index].column)
					if (pieces[28].row - pieces[index].row > 0
							&& pieces[28].column - pieces[index].column > 0)
						BishopCheck(0, index, 28);
				// case 1 up to the right
				if (pieces[28].row - pieces[index].row == pieces[index].column
						- pieces[28].column)
					if (pieces[28].row - pieces[index].row > 0
							&& pieces[index].column - pieces[28].column > 0)
						BishopCheck(1, index, 28);
				// case 2 down to the right
				if (pieces[index].row - pieces[28].row == pieces[index].column
						- pieces[28].column)
					if (pieces[index].row - pieces[28].row > 0
							&& pieces[index].column - pieces[28].column > 0)
						BishopCheck(2, index, 28);
				// case 3 down to the left
				if (pieces[index].row - pieces[28].row == pieces[28].column
						- pieces[index].column)
					if (pieces[index].row - pieces[28].row > 0
							&& pieces[28].column - pieces[index].column > 0)
						BishopCheck(3, index, 28);
				if (pieces[index].column == pieces[28].column) {
					// up
					if (pieces[index].row - pieces[28].row > 0)
						RookCheck(0, index, 28);
					// down
					if (pieces[28].row - pieces[index].row > 0)
						RookCheck(2, index, 28);
				}
				if (pieces[index].row == pieces[28].row) {
					// right
					if (pieces[28].column - pieces[index].column > 0)
						RookCheck(1, index, 28);
					// left
					if (pieces[index].column - pieces[28].column > 0)
						RookCheck(3, index, 28);
				}
			}
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void BishopCheck(int control, int index, int kingnum) {
			int length;
			int tempcoor[] = new int[2];
			boolean check;

			length = Math.abs(pieces[kingnum].row - pieces[index].row);
			tempcoor[0] = pieces[kingnum].row;
			tempcoor[1] = pieces[kingnum].column;
			check = true;
			for (int i = 0; i < length - 1; i++) {
				BishopCoor(tempcoor, control);
				for (int j = 0; j < 32; j++)
					if (tempcoor[0] == pieces[j].row
							&& tempcoor[1] == pieces[j].column)
						check = false;
			}
			if (check) {
				checked = true;
				whichKing = kingnum;
			}
		}

		public void RookCheck(int control, int index, int kingnum) {
			int length = 0;
			int tempcoor[] = new int[2];
			boolean check = true;

			// the the rook and the king are on the same column substract rows
			if (control == 0 || control == 2)
				length = Math.abs(pieces[kingnum].row - pieces[index].row);
			if (control == 1 || control == 3)
				length = Math
						.abs(pieces[kingnum].column - pieces[index].column);
			tempcoor[0] = pieces[index].row;
			tempcoor[1] = pieces[index].column;
			for (int i = 0; i < length - 1; i++) {
				RookCoor(tempcoor, control);
				for (int j = 0; j < 32; j++)
					if (tempcoor[0] == pieces[j].row
							&& tempcoor[1] == pieces[j].column)
						check = false;
			}
			if (check) {
				checked = true;
				whichKing = kingnum;
			}
		}

		// this determines squares attacked
		// pieces 0 - 15 = black; pieces 16 - 31 = white
		// 0 - square not attacked
		// 1 - sqaure attacked by white
		// 2 - square attacked by black
		// 3 - square attacked by both
		public boolean SquaresAttacked() {
			// this loop handles black and white pawns
			for (int i = 8; i < 24; i++) {
				if (pieces[i].color.equals("white") && pieces[i].row != 1) {
					if (pieces[i].column == 1) // to handle pawns on the first
												// column
					{
						if (squares[pieces[i].row - 2][pieces[i].column] == 2)
							squares[pieces[i].row - 2][pieces[i].column] += 1;
						else
							squares[pieces[i].row - 2][pieces[i].column] = 1;
					} else if (pieces[i].column == 8) // to handle pawns on the
														// last column
					{
						if (squares[pieces[i].row - 2][pieces[i].column - 2] == 2)
							squares[pieces[i].row - 2][pieces[i].column - 2] += 1;
						else
							squares[pieces[i].row - 2][pieces[i].column - 2] = 1;
					} else // to handle pawns in between first and last columns
					{
						if (squares[pieces[i].row - 2][pieces[i].column] == 2)
							squares[pieces[i].row - 2][pieces[i].column] += 1;
						else
							squares[pieces[i].row - 2][pieces[i].column] = 1;
						if (squares[pieces[i].row - 2][pieces[i].column - 2] == 2)
							squares[pieces[i].row - 2][pieces[i].column - 2] += 1;
						else
							squares[pieces[i].row - 2][pieces[i].column - 2] = 1;
					}
				}
				if (pieces[i].color.equals("black") && pieces[i].row != 8) {
					if (pieces[i].column == 1) // to handle pawns on the first
												// column
					{
						if (squares[pieces[i].row][pieces[i].column] == 1)
							squares[pieces[i].row][pieces[i].column] += 2;
						else
							squares[pieces[i].row][pieces[i].column] = 2;
					} else if (pieces[i].column == 8) // to handle pawns on the
														// last column
					{
						if (squares[pieces[i].row][pieces[i].column - 2] == 1)
							squares[pieces[i].row][pieces[i].column - 2] += 2;
						else
							squares[pieces[i].row][pieces[i].column - 2] = 2;
					} else // to handle pawns in between first and last columns
					{
						if (squares[pieces[i].row][pieces[i].column] == 1)
							squares[pieces[i].row][pieces[i].column] += 2;
						else
							squares[pieces[i].row][pieces[i].column] = 2;
						if (squares[pieces[i].row][pieces[i].column - 2] == 1)
							squares[pieces[i].row][pieces[i].column - 2] += 2;
						else
							squares[pieces[i].row][pieces[i].column - 2] = 2;
					}
				}
			}

			int tempcoor[] = new int[2];
			int oldx, oldy, x, y; 
			// *******************************************
			// this loop handles the other black pieces
			for (int i = 0; i < 16; i++) {
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("King")) {
					for (int k = 0; k < 8; k++) {
						if (KingCoor(tempcoor, k)) {
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
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Knight") && pieces[i].visible) {
					for (int j = 0; j < 8; j++) {
						KnightCoor(tempcoor, j);
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
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Bishop") && pieces[i].visible)
					BlackBishopSA(tempcoor, oldx, oldy);
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Rook"))
					BlackRookSA(tempcoor, oldx, oldy);
				if (pieces[i].name.equals("Queen") && pieces[i].visible) {
					BlackBishopSA(tempcoor, oldx, oldy);
					tempcoor[0] = pieces[i].row;
					tempcoor[1] = pieces[i].column;
					tempcoor[0]--;
					tempcoor[1]--;
					oldx = tempcoor[0];
					oldy = tempcoor[1];
					BlackRookSA(tempcoor, oldx, oldy);
				}
			}
			// *******************************************
			// this loop handles the other white pieces
			for (int i = 16; i < 32; i++) {
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("King")) {
					for (int k = 0; k < 8; k++) {
						if (KingCoor(tempcoor, k)) {
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
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Knight") && pieces[i].visible) {
					for (int j = 0; j < 8; j++) {
						KnightCoor(tempcoor, j);
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
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Bishop") && pieces[i].visible)
					WhiteBishopSA(tempcoor, oldx, oldy);
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Rook"))
					WhiteRookSA(tempcoor, oldx, oldy);
				tempcoor[0] = pieces[i].row;
				tempcoor[1] = pieces[i].column;
				tempcoor[0]--;
				tempcoor[1]--;
				oldx = tempcoor[0];
				oldy = tempcoor[1];
				if (pieces[i].name.equals("Queen") && pieces[i].visible) {
					WhiteBishopSA(tempcoor, oldx, oldy);
					tempcoor[0] = pieces[i].row;
					tempcoor[1] = pieces[i].column;
					tempcoor[0]--;
					tempcoor[1]--;
					oldx = tempcoor[0];
					oldy = tempcoor[1];
					WhiteRookSA(tempcoor, oldx, oldy);
				}
			}
			// *******************************************
			for (int i = 0; i < 8; i++) // this displays the squares attacked
			{
				for (int j = 0; j < 8; j++)
					System.out.print(squares[i][j]);
				System.out.println("");
			}
			System.out.println("********");
			for (int i = 0; i < 8; i++)
				// need to reinitialize for the next iteration
				for (int j = 0; j < 8; j++)
					squares[i][j] = 0;
			return true;
		}

		public void WhiteBishopSA(int tempcoor[], int oldx, int oldy) {
			int x, y;
			boolean piecethere;
			for (int k = 0; k < 4; k++) {
				piecethere = false;
				here: for (int l = 0; l < 7; l++) {
					if (BishopCoor(tempcoor, k)) {
						x = tempcoor[0];
						y = tempcoor[1];
						for (int m = 0; m < 32; m++)
							if (pieces[m].row - 1 == x
									&& pieces[m].column - 1 == y)
								piecethere = true;
						if (squares[x][y] == 2)
							squares[x][y] += 1;
						else
							squares[x][y] = 1;
						if (piecethere)
							break here;
					}
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		}

		public void WhiteRookSA(int tempcoor[], int oldx, int oldy) {
			int x, y;
			boolean piecethere;
			for (int k = 0; k < 4; k++) {
				piecethere = false;
				here: for (int l = 0; l < 7; l++) {
					if (RookCoor(tempcoor, k)) {
						x = tempcoor[0];
						y = tempcoor[1];
						for (int m = 0; m < 32; m++)
							if (pieces[m].row - 1 == x
									&& pieces[m].column - 1 == y)
								piecethere = true;
						if (squares[x][y] == 2)
							squares[x][y] += 1;
						else
							squares[x][y] = 1;
						if (piecethere)
							break here;
					}
				}
				tempcoor[0] = oldx;
				tempcoor[1] = oldy;
			}
		}

		public void BlackBishopSA(int tempcoor[], int oldx, int oldy) {
			int x, y;
			boolean piecethere;
			for (int k = 0; k < 4; k++) {
				piecethere = false;
				here: for (int l = 0; l < 7; l++) {
					if (BishopCoor(tempcoor, k)) {
						x = tempcoor[0];
						y = tempcoor[1];
						for (int m = 0; m < 32; m++)
							if (pieces[m].row - 1 == x
									&& pieces[m].column - 1 == y)
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

		public void BlackRookSA(int tempcoor[], int oldx, int oldy) {
			int x, y;
			boolean piecethere;
			for (int k = 0; k < 4; k++) {
				piecethere = false;
				here: for (int l = 0; l < 7; l++) {
					if (RookCoor(tempcoor, k)) {
						x = tempcoor[0];
						y = tempcoor[1];
						for (int m = 0; m < 32; m++)
							if (pieces[m].row - 1 == x
									&& pieces[m].column - 1 == y)
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

		public void PawnPromotion() {
			String newPiece;
			boolean finished = false;
			while (!finished) {
				newPiece = JOptionPane
						.showInputDialog("Choose a new piece Q / R / B / K");
				counter = pieceChosen;
				if (newPiece.equalsIgnoreCase("Q")) {
					if (pieces[pieceChosen].color.equals("white"))
						GetFileAndName(8, 4);
					if (pieces[pieceChosen].color.equals("black"))
						GetFileAndName(1, 4);
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("R")) {
					if (pieces[pieceChosen].color.equals("white"))
						GetFileAndName(8, 1);
					if (pieces[pieceChosen].color.equals("black"))
						GetFileAndName(1, 1);
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("B")) {
					if (pieces[pieceChosen].color.equals("white"))
						GetFileAndName(8, 3);
					if (pieces[pieceChosen].color.equals("black"))
						GetFileAndName(1, 3);
					finished = true;
				}
				if (newPiece.equalsIgnoreCase("K")) {
					if (pieces[pieceChosen].color.equals("white"))
						GetFileAndName(8, 2);
					if (pieces[pieceChosen].color.equals("black"))
						GetFileAndName(1, 2);
					finished = true;
				}
			}
			promotion = false;
		}

		// this function does the assignment that allows for movement
		// x and y are the coordinates of the square you're moving to
		public void MovePiece(int x, int y) {
			ok = false; // needed to release the current piece if you can't move
						// it
			// this detects if there is a piece or not to the destination square
			boolean foundpiece = false; // added chess 15
			boolean function[] = new boolean[6];
			int pieceNum = 40;

			oldx = y / 50 + 1;
			oldy = x / 50 + 1;
			for (int i = 0; i < 32; i++) {
				if (pieceChosen != i) {
					if (oldx == pieces[i].row && oldy == pieces[i].column) {
						foundpiece = true;
						pieceNum = i;
					}
				}
			}
			function[0] = PawnRules(x, y, foundpiece, pieceNum);
			function[1] = KingRules(x, y, foundpiece, pieceNum);
			function[2] = KnightRules(x, y, foundpiece, pieceNum);
			function[3] = BishopRules(x, y, foundpiece, pieceNum);
			function[4] = RookRules(x, y, foundpiece, pieceNum);
			function[5] = QueenRules(x, y, foundpiece, pieceNum);
			if (function[0] ^ function[1] ^ function[2] ^ function[3]
					^ function[4] ^ function[5]) {
				if (promotion)
					PawnPromotion();
				oldx = pieces[pieceChosen].row;
				oldy = pieces[pieceChosen].column;
				pieces[pieceChosen].row = y / 50 + 1;
				pieces[pieceChosen].column = x / 50 + 1;
				for (int i = 0; i < 32; i++) // checks for another piece
				{
					if (pieceChosen != i) // don't take self
					{
						if (pieces[pieceChosen].row == pieces[i].row
								&& pieces[pieceChosen].column == pieces[i].column
								&& !pieces[i].name.equals("King")) {
							pieces[i].row = 11; // actually removes piece from
												// board
							pieces[i].column = 11;
							pieces[i].visible = false;
						}
					}

				}
				if (CheckMethod()) {
					if (pieces[pieceChosen].color
							.equals(pieces[whichKing].color)) {
						pieces[pieceChosen].row = oldx;
						pieces[pieceChosen].column = oldy;
					}
				}
				SquaresAttacked();
				// after the calculation repaint is called to display the
				// movement
				// whos_turn = (whos_turn + 1) % 2; //this toggles between the
				// players
				repaint();
				if (checked) {
					JOptionPane.showMessageDialog(null, "check");
					checked = false;
				}
			} else
				repaint();
		}
	}
}
