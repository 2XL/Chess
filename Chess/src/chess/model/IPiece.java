package chess.model;


/**
 * This is a interface for abstract piece method
 * @author Xiang
 * @version 1.0
 *
 */
public interface IPiece
{ 
	/**
	 * This is a interface to make the pieces getting rules
	 * @param x - row
	 * @param y - column
	 * @param bool - whas there a piece found as target?
	 * @param targetPiece - who is the piece to target, can be null
	 * @return true the piece can move to target position, else false
	 */
	boolean pieceRules(int x, int y, boolean bool, AbstractPiece targetPiece); 

	/**
	 * this method returns a bool, after checking self target positions if a king is on check
	 * @return true if checking else false
	 */
	boolean handlePieceCheckMate();

	/**
	 * this method add the squares attacked by a specific Piece
	 * @param squares
	 */
	void squaresAttacked(int[][] squares);
}
