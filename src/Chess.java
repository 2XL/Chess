

import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.Point;   
   



 

import java.applet.*;

import javax.swing.JOptionPane;
 


public class Chess extends Applet{
 
	private static final long serialVersionUID = -3360659134996762950L;
	
	final Color BOARD_FIELD_COLOR = Color.DARK_GRAY;  // default red

	final int BOARD_SIZE = 400;	// in pixels
	final int BOARD_LENGTH  = 8;
	final int BOARD_PIECE_LENGTH = BOARD_SIZE/BOARD_LENGTH; // 50
	final int BOARD_NUM_PIECES = 32; 

	 
	final PieceName[][] CHESS_MAP = { 
			{ PieceName.ROOK, PieceName.KNIGHT,PieceName.BISHOP,PieceName.QUEEN,PieceName.KING,PieceName.BISHOP,PieceName.KNIGHT,PieceName.ROOK},
			{ PieceName.PAWN, PieceName.PAWN,  PieceName.PAWN,  PieceName.PAWN, PieceName.PAWN,PieceName.PAWN,  PieceName.PAWN,  PieceName.PAWN},
			{ null, null, null, null, null, null,null, null},
			{ null, null, null, null, null, null,null, null},	
			{ null, null, null, null, null, null,null, null},	
			{ null, null, null, null, null, null,null, null}, 
			{ PieceName.PAWN, PieceName.PAWN,  PieceName.PAWN,  PieceName.PAWN, PieceName.PAWN,PieceName.PAWN,  PieceName.PAWN,  PieceName.PAWN},
			{ PieceName.ROOK, PieceName.KNIGHT,PieceName.BISHOP,PieceName.QUEEN,PieceName.KING,PieceName.BISHOP,PieceName.KNIGHT,PieceName.ROOK}
	}; 
	
	int chessBoard[][];
    AbstractPiece pieces[];
	
	boolean okToMove;
	int pieceCounter; // only used for creating pieces 
	int pieceChosen; // this identifies which piece to move

	Point oldPosition; 
	
	int numClicks; // 
	int whichKing;   // wtf?
	boolean promotion; // wtf?
	
	// INSTANTIATE
	public Chess() {
		// TODO Auto-generated constructor stub
		chessBoard = new int[BOARD_LENGTH][BOARD_LENGTH];
		pieces = new AbstractPiece[BOARD_NUM_PIECES];
		oldPosition = new Point();
		okToMove = false;
		pieceCounter = 0;
		pieceChosen = 0;
		numClicks = 0; 
		promotion = false;
	}
	
	// INIT
	public void init(){ 

		createPieces(); 
		addMouseListener(new ChessMousePressListener(this));
	}
	

	public void createPieces(){  
			for(int x = 0; x<BOARD_LENGTH; x++){
				for(int y = 0; y<BOARD_LENGTH; y++){  
					if(CHESS_MAP[x][y] != (null)){
						switch (CHESS_MAP[x][y]){
						case KING:
							pieces[pieceCounter] = new King(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						case QUEEN:
							pieces[pieceCounter] = new Queen(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						case BISHOP:
							pieces[pieceCounter] = new Bishop(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						case KNIGHT:
							pieces[pieceCounter] = new Knight(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						case PAWN:
							pieces[pieceCounter] = new Pawn(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						case ROOK:
							pieces[pieceCounter] = new Rook(pieceCounter, new Point(y+1,x+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH);
							break;
						default:
							break;
						}   
						setPieceIconAndName(x, y); 	
						pieceCounter++;
					}else{
						break;
					}
				}
		} 
	}
	
	private void setPieceIconAndName( int rowIndex, int colIndex){
	 
		boolean isBlack = (rowIndex == 0 || rowIndex == 1) ? true : false;
		boolean isWhite = (rowIndex == 6 || rowIndex == 7) ? true : false;
		String fileExt = ".gif";
		String filePackagePath = "icons/";
		String fileName;
		PieceName pieceName = CHESS_MAP[rowIndex][colIndex]; 
		PieceColor pieceColor; 
		
		if(isBlack||isWhite){ 
			if(isBlack)
			{	
				fileName = filePackagePath+"b"+pieceName+fileExt;
				pieceColor = PieceColor.BLACK;
			}
			else{
				fileName = filePackagePath+"w"+pieceName+fileExt;  
				pieceColor = PieceColor.WHITE;
			} 
			pieces[pieceCounter].setPieceIcon(getImage(getCodeBase(), fileName));
			pieces[pieceCounter].setName(pieceName);
			pieces[pieceCounter].setColor(pieceColor);
		}else{
			System.out.println("Error input Position");
		} 
	}
	
	public void printBoard(){
		
		int matrixLength = 15; 
		String board[][] = new String[matrixLength][matrixLength];
		
		
		for(int i= 0; i<matrixLength; i++){
			for(int j = 0; j<matrixLength; j++){
				board[i][j] = ":"+ i+":"+j +" :";
			}
		}
		
		
		for(AbstractPiece p : AbstractPiece.getPieces()){
			board[p.getPosition().x/p.getPieceIconLength()][p.getPosition().y/p.getPieceIconLength()] += p.getName().toString()+p.getColor().toString(); 
		}
		

		System.out.println("*************");
		
		for(int i= 0; i<matrixLength; i++){
			for(int j = 0; j<matrixLength; j++){
				System.out.print(board[j][i]+"\t");
			}
			System.out.println("");
		}
		System.out.println("*************");
		
		
	}
	
	
	// PAINT
	public void paint(Graphics graphics){
		
		setSize(BOARD_SIZE, BOARD_SIZE);
		
		int iWidth = getWidth();
		int iHeight = getHeight();
		
		// print the cells of the game board
		for(int i = 0; i< BOARD_LENGTH; i+=2){
			for(int j = 0; j<BOARD_LENGTH; j+=2){
				graphics.setColor(BOARD_FIELD_COLOR);
				graphics.fillRect(j * iWidth / BOARD_LENGTH, (1 + i) * iWidth / BOARD_LENGTH, iWidth / BOARD_LENGTH, iHeight / BOARD_LENGTH); 
				graphics.fillRect((1 + j) * iWidth / BOARD_LENGTH, i * iWidth / BOARD_LENGTH, iWidth / BOARD_LENGTH, iHeight / BOARD_LENGTH);
			}
		}
		
		// print the pieces on the gameboard
		for(int i = 0; i<BOARD_NUM_PIECES; i++){
			pieces[i].getPosition().x = rowToX(pieces[i].getPositionInit().x);
			pieces[i].getPosition().y = colToY(pieces[i].getPositionInit().y);
			if (pieces[i].isVisible())
				graphics.drawImage(
						pieces[i].getPieceIcon(), pieces[i].getPosition().x,
						pieces[i].getPosition().y, iWidth / BOARD_LENGTH, iHeight / BOARD_LENGTH, this);
			
		}
		// this is a square hightlighted
		if(this.okToMove){
			graphics.setColor(Color.green.darker());
			graphics.drawRect(pieces[pieceChosen].getPosition().x,
					pieces[pieceChosen].getPosition().y, BOARD_PIECE_LENGTH, BOARD_PIECE_LENGTH);
			
			graphics.setColor(Color.green.brighter().brighter());
			graphics.drawRect(pieces[pieceChosen].getPosition().x + 1,
					pieces[pieceChosen].getPosition().y + 1, BOARD_PIECE_LENGTH-2, BOARD_PIECE_LENGTH-2);
			
			graphics.setColor(Color.green.darker());
			graphics.drawRect(pieces[pieceChosen].getPosition().x + 2,
					pieces[pieceChosen].getPosition().y + 2, BOARD_PIECE_LENGTH-4, BOARD_PIECE_LENGTH-4);
			
		} 
	}
	
	private int rowToX(int r) { 
		return r * getHeight() / BOARD_LENGTH - (BOARD_PIECE_LENGTH); // why - 50 ?
	}
	private int colToY(int c) { 
		return c * getWidth() / BOARD_LENGTH -  (BOARD_PIECE_LENGTH);
	}
	
	
	public boolean checkCheckMate() {
		// 
		boolean checked = false;
		for (int i = 0; i < 32; i++) {  
			if (pieces[i].handlePieceCheckMate()) {
				checked = true;
				
				switch (pieces[i].getColor())
				{
				case BLACK:
					whichKing = 28;
					break;
				case WHITE:
					whichKing = 4;
					break;
				default:
					whichKing = 40;
					break;
				}
				break;
			} 	
		}
		return checked;
	}
	
	public void isPawnPromotion() {
		String newPiece;
		boolean finished = false;
		
		pieceCounter = pieceChosen;
		String fileName;
		String fileExt = ".gif";
		String filePackagePath = "better/media/";
		AbstractPiece p = pieces[pieceChosen];
		 
		while (!finished) {
			newPiece = JOptionPane
					.showInputDialog("Choose a new piece Q / R / B / K"); 
			
			switch(newPiece){
			case "Q":  
				p.setName(PieceName.QUEEN);
				finished = true;
				break;
			case "R": 
				p.setName(PieceName.ROOK); 
				finished = true;
				break;
			case "B":
				p.setName(PieceName.BISHOP);
				finished = true;
				break;
			case "K":
				p.setName(PieceName.KNIGHT); 
				finished = true;
				break;
			default:
				System.out.println("Error: promote error option!");
				break;
			}
			
			if(finished){
			String color = p.getColor().equals(PieceColor.WHITE) ? "w" : "b";  
			fileName = filePackagePath+color+p.getName()+fileExt;
			p.setPieceIcon(getImage(getCodeBase(), fileName));
			} 
		} 
		promotion = false;
	}


	// this determines squares attacked
	// pieces 0 - 15 = black; pieces 16 - 31 = white
	// 0 - square not attacked
	// 1 - square attacked by white
	// 2 - square attacked by black
	// 3 - square attacked by both
	public boolean squaresAttacked(){
		
		int[][] squares = new int[12][12];
		for (int i = 0; i < 8; i++) 
			for (int j = 0; j < 8; j++)
				squares[i][j] = 0;
		
		for(AbstractPiece p : pieces)
			p.squaresAttacked(squares);
		
		
		
		
		for (int i = 0; i < 8; i++) // this displays the squares attacked
		{
			for (int j = 0; j < 8; j++)
				System.out.print(squares[i][j]);
			System.out.println("");
		}
		
		System.out.println("********");

					return true;  // LOL <---- LOLOLO
	}
	

	
	
}























