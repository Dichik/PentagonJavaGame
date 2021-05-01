package game.pieces;

public class Grid {
	public static final int LINES = 16 ;
	public static final int LINES_SIZE = 16 ;
		private Square[][] singlePieces;

		public Grid() {
			this.singlePieces = new Square[LINES][LINES_SIZE];
		}

		public void placeTetromino(Pentamimo tetromino, int locationX, int locationY, Pentamimo.Rotation rotation) {
			for(int i=0;i<tetromino.getSize();i++) {
				for(int j=0;j<tetromino.getSize();j++) {
					if(tetromino.getSquareAt(rotation, i, j) != null) {
						this.singlePieces[locationY+i][locationX+j] = tetromino.getSquareAt(rotation, i, j);
					}
				}
			}
		}

		public Square[] getLine(int i) {
			return singlePieces[i];
		}

		/**Iterates through all squares in the grid<br>
		 * Checks if all squares can fall in the tile below
		 * @return True if all squares can go to the tile below, false if one of them can't
		 */
		public boolean allSquaresCanFall() {
			for(int i=Grid.LINES-1;i>=0;i--) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if((!this.singlePieces[i][j].isFixed() && i == Grid.LINES-1) || (!this.singlePieces[i][j].isFixed() && this.singlePieces[i+1][j] != null && this.singlePieces[i+1][j].isFixed())) {
							return false;
						}
					}
				}
			}
			return true;
		}
		public boolean allSquaresCanGoUP() {
			for(int i=0;i < Grid.LINES; i ++ ) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if((!this.singlePieces[i][j].isFixed() && i == 2)
								|| (!this.singlePieces[i][j].isFixed() && this.singlePieces[i - 1][j] != null
								&& this.singlePieces[i - 1][j].isFixed())) {
							return false;
						}
					}
				}
			}
			return true;
		}
		public boolean allSquaresCanGoLeft() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if((!this.singlePieces[i][j].isFixed() && j == 0) || (!this.singlePieces[i][j].isFixed() && this.singlePieces[i][j-1] != null && this.singlePieces[i][j-1].isFixed())) {
							return false;
						}
					}
				}
			}
			return true;
		}
		public boolean allSquaresCanGoRight() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=Grid.LINES_SIZE-1;j>=0;j--) {
					if(this.singlePieces[i][j] != null) {
						if((!this.singlePieces[i][j].isFixed() && j == Grid.LINES_SIZE-1)  || (!this.singlePieces[i][j].isFixed() && this.singlePieces[i][j+1] != null && this.singlePieces[i][j+1].isFixed())) {
							return false;
						}
					}
				}
			}
			return true;
		}
		public void movePiecesDown() {
			for(int i=Grid.LINES-2;i>=0;i--) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							this.singlePieces[i + 1][j] = this.singlePieces[i][j];
							this.singlePieces[i][j] = null;
						}
					}
				}
			}
		}
		public void movePiecesUp() {
			for(int i = 0;i < Grid.LINES - 1; i++ ) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							this.singlePieces[i - 1][j] = this.singlePieces[i][j];
							this.singlePieces[i][j] = null;
						}
					}
				}
			}
		}
		public void movePiecesLeft() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=1;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							this.singlePieces[i][j-1] = this.singlePieces[i][j];
							this.singlePieces[i][j-1].resetStopTime();
							this.singlePieces[i][j] = null;
						}
					}
				}
			}
		}
		public void movePiecesRight() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=Grid.LINES_SIZE-2;j>=0;j--) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							this.singlePieces[i][j+1] = this.singlePieces[i][j];
							this.singlePieces[i][j+1].resetStopTime();
							this.singlePieces[i][j] = null;
						}
					}
				}
			}
		}
		public void setAllSquaresToBeStopped() {
			for(int i=Grid.LINES-1;i>=0;i--) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed() && !this.singlePieces[i][j].isStopping()) {
							this.singlePieces[i][j].setStopping();
						}
					}
				}
			}
		}
		public boolean allSquaresAreFixed() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							return false;
						}
					}
				}
			}
			return true;
		}
		public void removeLine(int line) {
			for(int i=0;i<Grid.LINES_SIZE;i++) {
				this.singlePieces[line][i] = null;
			}
			for(int i=line;i>=0;i--) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						this.singlePieces[i][j].setNotFixed();
					}
				}
			}
		}
		public void removeTetromino() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.singlePieces[i][j] != null) {
						if(!this.singlePieces[i][j].isFixed()) {
							this.singlePieces[i][j] = null;
						}
					}
				}
			}
		}

		/**Checks if the given tetromino can be placed at the given position
		 * @param tetromino - The tetromino to place
		 * @param locationX - The position where it should be placed
		 * @param locationY - The position where it should be placed
		 * @param rotation - Its rotation
		 * @return True if the tetromino can be placed here, false if that spot is already occupied
		 */
		public boolean canPlaceHere(Pentamimo tetromino, int locationX, int locationY, Pentamimo.Rotation rotation) {
			for(int i=0;i<tetromino.getSize();i++) {
				for(int j=0;j<tetromino.getSize();j++) {
					if(tetromino.getSquareAt(rotation, i, j) != null && this.singlePieces[locationY+i][locationX+j] != null && this.singlePieces[locationY+i][locationX+j].isFixed()) {
						return false;
					}
				}
			}
			return true;
		}

	/**
	 * when we touch the 16-th place we draw that line in light grey color
	 * so that the user could see number of used squares in the line.
	 */

}
