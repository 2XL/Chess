package chess.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import chess.model.AbstractPiece;
import chess.model.Pawn;
import chess.model.PieceName;
import chess.view.Chess;


/**
 * Controller class which inherit MouseAdapter to serve all mouse events
 * @author Xiang
 *
 */
public class ChessMousePressListener extends MouseAdapter implements MouseMotionListener{

	private static ChessMousePressListener instance = null;

	Chess c;

	/**
	 * Assign a mouse press listener to the AppletView
	 * @param chess
	 */
	private ChessMousePressListener(Chess chess) { 
		c = chess; 
	}

	/**
	 * Singleton MousePressListener instance
	 * @param chess
	 * @return MousePressListener unique instance
	 */
	public static ChessMousePressListener getInstance(Chess chess) {
		if(instance == null) {
			instance = new ChessMousePressListener(chess);
		}
		return instance;
	}

	/**
	 * Action listener on mouse click event	
	 */
	public void mousePressed(MouseEvent event) {

		int x = event.getX();
		int y = event.getY();

		String temp;
		temp = Integer.toString(y / c.BOARD_PIECE_LENGTH + 1);
		System.out.println("rawx = " + temp);
		temp = Integer.toString(x / c.BOARD_PIECE_LENGTH + 1);
		System.out.println("rawy = " + temp); 


		c.numClicks = (c.numClicks+1) % 2; 
		if (c.okToMove)
		{
			movePieceChoosen(x, y);	// movePiece(x,y)
		}
		else 
		{
			getPieceChoosen(x, y);	// boardID(x,y) // la primera vegada entra aquest perque no s'ha seleccionat res encara
		}
		c.repaint(); 
	}
	/**
	 * set an piece instance by the position given on board click, it sets the piece selected variable of the chess board
	 * @param x row click position
	 * @param y column click position
	 */
	public void getPieceChoosen(int x, int y) { // original name: move Piece (x, y)
		int boardY = (y /c.BOARD_PIECE_LENGTH );
		int boardX = (x /c.BOARD_PIECE_LENGTH );
		for (int i = 0; i < 32; i++) { 		// search piece to move														 
			if (boardY == c.pieces[i].getPosition().y /c.BOARD_PIECE_LENGTH
					&& ( boardX == c.pieces[i].getPosition().x /c.BOARD_PIECE_LENGTH)) {
				c.pieceChosen = i; // this identifies which piece has been
				c.okToMove = true; // this says we found  
				break; // after found piece don't have to continue searching...
			}
		} 
	}
	/**
	 * move a piece selected to an target position
	 * @param x target row click position
	 * @param y target column click position
	 */
	public void movePieceChoosen(int x, int y) { // original name: boardID (x, y)


		c.okToMove = false; // needed to release the current piece if you can't move

		// this detects if there is a piece or not to the destination square
		boolean pieceFound = false; // added chess 15
		boolean pieceRules = false; // no rules set by default // function[] wtf

		AbstractPiece targetPiece = null; // 40

		c.oldPosition.x = y/c.BOARD_PIECE_LENGTH +1;
		c.oldPosition.y = x/c.BOARD_PIECE_LENGTH +1; 

		// select this piece  
		for (int i = 0; i < c.BOARD_NUM_PIECES; i++) {
			if (c.pieceChosen != i) {  
				if (c.oldPosition.x == (c.pieces[i].getColumnRow().x ) &&
						(c.oldPosition.y == (c.pieces[i].getColumnRow().y ))) {
					pieceFound = true;  
					targetPiece = c.pieces[i];  
					break;
				}
			}
		}

		pieceRules = c.pieces[c.pieceChosen].pieceRules(x, y, pieceFound, targetPiece);

		if (pieceRules){ // en el cas que la regla ens permeti realit 
			if (c.pieces[c.pieceChosen].getName().equals(PieceName.PAWN)
					&&((Pawn)c.pieces[c.pieceChosen]).isPromotion()) { 
				c.isPawnPromotion(c.pieces[c.pieceChosen]); 
			} 

			c.oldPosition.setLocation(c.pieces[c.pieceChosen].getColumnRow());  	
			c.pieces[c.pieceChosen].getColumnRow().setLocation( y / c.BOARD_PIECE_LENGTH + 1, x / c.BOARD_PIECE_LENGTH + 1);  


			// checks for another piece	
			for (int i = 0; i < c.BOARD_NUM_PIECES; i++) 
			{
				if (c.pieceChosen != i) // don't take self
				{ 
					if (c.pieces[c.pieceChosen].getColumnRow().equals(c.pieces[i].getColumnRow())
							&& !c.pieces[i].getName().equals(PieceName.KING)  
							) {
						c.pieces[i].getColumnRow().setLocation(11, 11);  	// actually removes piece from board LOL 
						c.pieces[i].setVisible(false); 
						break;
					}
				}
			}

			// comprabar si ha hagut jaque mate
			if (c.checkCheckMate()) { // returns true 
				c.repaint(); 

				// si el color del rei amb jaque mate es igual al meu no me moc
				if (c.pieces[c.pieceChosen].getColor().equals(c.pieces[c.whichKing].getColor())) {  
					c.pieces[c.pieceChosen].getColumnRow().setLocation(c.oldPosition);
				}

				JOptionPane.showMessageDialog(null, "check"); 
			} 
			c.squaresAttacked(); 
		} 
		else 
			c.repaint(); 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
	}

	/**
	 * update mouse drag position to chess board oldposition	
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		c.oldPosition.setLocation(e.getX(), e.getY());  
	}

}
