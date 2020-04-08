package chess.view;

import java.awt.Color; 
import java.awt.Graphics; 
import java.awt.Point;   

import java.applet.Applet;

import javax.swing.JOptionPane;

import chess.controller.ChessMousePressListener;
import chess.model.*;

/**
 * View class which extends Applet to display the user interface.
 * @author Xiang
 *
 */
public class Chess extends Applet{

	private static final long serialVersionUID = -3360659134996762950L;

	protected final Color BOARD_FIELD_COLOR = Color.DARK_GRAY;  // default red

	final int BOARD_SIZE = 400;		// in pixels
	final int BOARD_LENGTH  = 8;
	final int SQUARE_LENGTH  = 12;
	public final int BOARD_PIECE_LENGTH = BOARD_SIZE/BOARD_LENGTH; // 50
	public final int BOARD_NUM_PIECES = 32; 

	/**
	 * CHESS_BOARD MAP_PIECE CONSTANT
	 */
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

	public AbstractPiece pieces[];
	public boolean okToMove;

	int pieceCounter; // only used for creating pieces 
	public int pieceChosen; // this identifies which piece to move

	public Point oldPosition; 

	public int numClicks;   // 
	public int whichKing;   // whichKing is checked 

	/**
	 * Chess class builder that creates a new chess board with all its default configuration setting
	 */
	public Chess() {
		// TODO Auto-generated constructor stub
		chessBoard = new int[BOARD_LENGTH][BOARD_LENGTH];
		pieces = new AbstractPiece[BOARD_NUM_PIECES];
		oldPosition = new Point();
		okToMove = false;
		pieceCounter = 0;
		pieceChosen = 0;
		numClicks = 0;  
	}

	/**
	 * init all pieces with its specific setting and sets the mouseListener to the applet 
	 */
	public void init(){  
		createPieces();  
		addMouseListener(ChessMousePressListener.getInstance(this));
	}

	/**
	 * create a specific piece by CHESS_MAP matrix position item
	 */
	public void createPieces(){  
		for(int x = 0; x<BOARD_LENGTH; x++){
			for(int y = 0; y<BOARD_LENGTH; y++){  
				if(CHESS_MAP[x][y] != (null)){
					switch (CHESS_MAP[x][y]){
					case KING:
						pieces[pieceCounter] = new King(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					case QUEEN:
						pieces[pieceCounter] = new Queen(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					case BISHOP:
						pieces[pieceCounter] = new Bishop(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					case KNIGHT:
						pieces[pieceCounter] = new Knight(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					case PAWN:
						pieces[pieceCounter] = new Pawn(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					case ROOK:
						pieces[pieceCounter] = new Rook(pieceCounter, new Point(x+1,y+1), new Point(0,0), true, true, BOARD_PIECE_LENGTH, true);
						break;
					default:
						break;
					}   
					setPieceIconAndName(x, y, CHESS_MAP[x][y]); 	
					pieceCounter++;
				}else{
					break;
				}
			}
		} 
	}

	/**
	 * set the respective name, color, and icon to an specify piece on initiation
	 * @param rowIndex
	 * @param colIndex
	 * @param pieceName
	 */
	private void setPieceIconAndName(int rowIndex, int colIndex, PieceName pieceName){
		boolean isBlack = (rowIndex == 0 || rowIndex == 1) ? true : false;
		boolean isWhite = (rowIndex == 6 || rowIndex == 7) ? true : false;
		String fileExt = ".gif";
		String filePackagePath = "chess/icon/";
		String fileName; 
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
			pieces[pieceCounter].setColor(pieceColor);
		}else{
			System.out.println("Error input Position");
		} 
	}


	/**
	 * initiates the ChessBoard graphics, drawing the pieces and the board background
	 */
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
			pieces[i].getPosition().x = rowToX(pieces[i].getColumnRow().y);
			pieces[i].getPosition().y = colToY(pieces[i].getColumnRow().x);
			if (pieces[i].isVisible())
				graphics.drawImage(
						pieces[i].getPieceIcon(), 
						pieces[i].getPosition().x,
						pieces[i].getPosition().y, 
						iWidth / BOARD_LENGTH, 
						iHeight / BOARD_LENGTH, this);

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

	/**
	 * convert mouse click position into ChessBoard matrix position row
	 * @param r : row position by mouse click
	 * @return ChessBoard Matrix row position
	 */
	private int rowToX(int r) { 
		return r * getHeight() / BOARD_LENGTH - (BOARD_PIECE_LENGTH); // why - 50 ?
	}

	/**
	 * convert mouse click position into ChessBoard matrix position column
	 * @param c : column position by mouse click
	 * @return ChessBoard Matrix row position
	 */
	private int colToY(int c) { 
		return c * getWidth() / BOARD_LENGTH -  (BOARD_PIECE_LENGTH);
	}

	/**
	 * check all pieces handle checkmate method
	 * @return true if exist checkmate else false.
	 */
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

	/**
	 * pawn promotion process, it converts the pawn into an higher class piece between( queen, rook, bishop and knight ) 
	 * @param pawn - concrete piece to promote
	 */
	public void isPawnPromotion(AbstractPiece pawn) { // aixo va en pawn noperque ha de crear una nova instancia abstracta xD aki nose no tinc clar
		String newPiece;
		boolean finished = false;

		String fileName;
		String fileExt = ".gif";
		String filePackagePath = "chess/icon/"; 

		PieceColor pColor = pawn.getColor();

		while (!finished) {
			newPiece = JOptionPane
					.showInputDialog("Choose a new piece Q / R / B / K"); 

			switch(newPiece){
			case "Q":   
				pieces[pieceChosen] = new Queen(pawn.getPieceID(), pawn.getPosition(), pawn.getColumnRow(), true, false, BOARD_PIECE_LENGTH, false);
				finished = true;
				break;
			case "R":   
				pieces[pieceChosen] = new Rook(pawn.getPieceID(), pawn.getPosition(), pawn.getColumnRow(), true, false, BOARD_PIECE_LENGTH, false);
				finished = true;
				break;
			case "B":
				pieces[pieceChosen] = new Bishop(pawn.getPieceID(), pawn.getPosition(), pawn.getColumnRow(), true, false, BOARD_PIECE_LENGTH, false);
				finished = true;
				break;
			case "K": 
				pieces[pieceChosen] = new Knight(pawn.getPieceID(), pawn.getPosition(), pawn.getColumnRow(), true, false, BOARD_PIECE_LENGTH, false);
				finished = true;
				break;
			default:
				System.out.println("Error: promote error option!");
				break;
			}

			if(finished){ 
				String color = pColor.equals(PieceColor.WHITE) ? "w" : "b";  
				fileName = filePackagePath+color+pieces[pieceChosen].getName()+fileExt;
				pieces[pieceChosen].setPieceIcon(getImage(getCodeBase(), fileName));
				pieces[pieceChosen].setColor(pColor);
			} 
		}  
	}


	/**
	 * this determines the squares attacked
	 * pieces 0 - 15 = black; pieces 16 - 31 = white
	 * 0 - square not attacked
	 * 1 - square attacked by white
	 * 2 - square attacked by black
	 * 3 - square attacked by both
	 */
	public void squaresAttacked(){ 
		int[][] squares = new int[SQUARE_LENGTH][SQUARE_LENGTH];
		for (int i = 0; i < BOARD_LENGTH; i++) 
			for (int j = 0; j < BOARD_LENGTH; j++)
				squares[i][j] = 0;

		for(AbstractPiece p : pieces)
			p.squaresAttacked(squares);

		for (int i = 0; i < BOARD_LENGTH; i++) // this displays the squares attacked
		{
			for (int j = 0; j < BOARD_LENGTH; j++)
				System.out.print(squares[i][j]);
			System.out.println("");
		}
		System.out.println("********"); 
	}
}























