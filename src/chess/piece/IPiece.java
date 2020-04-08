package chess.piece;

 
 

public interface IPiece
{ 
	// about the pieces 
	boolean pieceRules(int x, int y, boolean bool, AbstractPiece targetPiece); 
	boolean handlePieceCheckMate();
	boolean squaresAttacked(int[][] squares);
}
