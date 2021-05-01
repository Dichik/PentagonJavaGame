package game.pieces;

public class Square {
 	private String color ;
 	private boolean fixed ;

	public Square(String color) {
		this.color = color ;
		fixed = false ;
	}

	public String getColor() {
		return color;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
