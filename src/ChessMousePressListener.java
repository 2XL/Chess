
 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
 


public class ChessMousePressListener extends MouseAdapter implements MouseMotionListener{
	  
	Chess c;
	
	public ChessMousePressListener(Chess chess) { 
		c = chess; 
	}
	 
	
	public void mousePressed(MouseEvent event) {
		
		int x = event.getX();
		int y = event.getY();
		
		System.out.println(x+ " " +y);
		
		String temp;
		temp = Integer.toString(y / c.BOARD_PIECE_LENGTH + 1);
		System.out.println("rawx = " + temp);
		temp = Integer.toString(x / c.BOARD_PIECE_LENGTH + 1);
		System.out.println("rawy = " + temp); 
		
		
		c.numClicks = (c.numClicks+1) % 2;
		   
		if (c.okToMove)
			movePieceChoosen(x, y);	// movePiece(x,y)
		else 
			getPieceChoosen(x, y);	// boardID(x,y) // la primera vegada entra aquest perque no s'ha seleccionat res encara
		c.repaint();
		
		c.printBoard(); // this is just a print. of the squares pieces alternative to the ordinaryone
	}

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
	
	
	
	
	
	
	public void movePieceChoosen(int x, int y) { // original name: boardID (x, y)
		
		c.okToMove = false; // needed to release the current piece if you can't move
					
		// this detects if there is a piece or not to the destination square
		boolean pieceFound = false; // added chess 15
		boolean pieceRules = false; // no rules set by default // function[] wtf
		 
		AbstractPiece targetPiece = null;
		
		c.oldPosition.x = x/c.BOARD_PIECE_LENGTH;
		c.oldPosition.y = y/c.BOARD_PIECE_LENGTH; 
		
		// select this piece  
		for (int i = 0; i < c.BOARD_NUM_PIECES; i++) {
			if (c.pieceChosen != i) {  
				if (c.oldPosition.x == (c.pieces[i].getPosition().x /c.BOARD_PIECE_LENGTH) &&
						(c.oldPosition.y == (c.pieces[i].getPosition().y /c.BOARD_PIECE_LENGTH))) {
					pieceFound = true;  
					targetPiece = c.pieces[i]; 
					break;
				}
			}
		}
		
		pieceRules = c.pieces[c.pieceChosen].pieceRules(x, y, pieceFound, targetPiece);
		
		System.out.println("Piece found? "+pieceFound);
		System.out.println("Piece rules? "+pieceRules);
		
		
		if (pieceRules){ // en el cas que la regla ens permeti realit
		//	if (targetPiece.getName().equals(PieceName.PAWN))
			//	c.isPawnPromotion();
			
					c.oldPosition.setLocation(c.pieces[c.pieceChosen].getPositionInit());  	
					c.pieces[c.pieceChosen].getPositionInit().setLocation( x / c.BOARD_PIECE_LENGTH + 1, y / c.BOARD_PIECE_LENGTH + 1);  
					
			
			// checks for another piece	
			for (int i = 0; i < c.BOARD_NUM_PIECES; i++) 
			{
				if (c.pieceChosen != i) // don't take self
				{ 
					if (c.pieces[c.pieceChosen].getPositionInit().equals(c.pieces[i].getPositionInit())
							 && !c.pieces[i].getName().equals(PieceName.KING) && ! c.pieces[c.pieceChosen].getColor().equals(c.pieces[i].getColor())) {
						c.pieces[i].getPositionInit().setLocation(11, 11);  	// actually removes piece from board LOL 
						c.pieces[i].setVisible(false); 
						break;
					}
				}
			}
			
			// comprabar si ha hagut jaque mate
			if (c.checkCheckMate()) { // returns true
				// si el color del rei amb jaque mate es igual al meu no me moc
				if (c.pieces[c.pieceChosen].getColor().equals(c.pieces[c.whichKing].getColor())) { 
					// if there was check mate avoid movement, doing a backward
					System.out.println(c.pieces[c.pieceChosen]);
					c.pieces[c.pieceChosen].getPositionInit().setLocation(c.oldPosition);
				}
				
				JOptionPane.showMessageDialog(null, "check");
 
				
			}
			
			
		//	c.squaresAttacked();
 
		} 
		System.out.println("Repaint: ");
			c.repaint(); 
	}

	 
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		c.oldPosition.setLocation(e.getX(), e.getY()); 
		System.out.println(c.oldPosition);
	}
	 
 

 
}
