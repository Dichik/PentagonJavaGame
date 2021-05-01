package game.pieces;

public class Grid {
	public static final int LINES = 16 ;
	public static final int LINES_SIZE = 16 ;

	private Square[][] singlePieces ;

	public Grid() {
		singlePieces = new Square[LINES][LINES_SIZE] ;
	}
	
	public void placePentamimo() {

	}

	/**
	 * when we touch the 16-th place we draw that line in light grey color
	 * so that the user could see number of used squares in the line.
	 */

}
