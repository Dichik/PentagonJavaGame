package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import framework.utils.MathHelper;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.ArrayBlockingQueue;

import framework.display.Window ;
import game.pieces.Pentamimo;
import game.pieces.Square;

public class PlayingState extends GameState {
	/*
	private Grid grid ;

	@Override
	protected void init() {
		grid = new Grid() ;
		System.out.println("[Game][States]: Created playing state");
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics graphics) {
		drawBackground(graphics) ;
		drawGrid(graphics);
	}

	@Override
	public void keyPressed(int key) {
		if(key == KeyEvent.VK_ESCAPE){
			Game.STATE_MANAGER.changeState(new PauseMenu());
		}
	}

	@Override
	public void keyReleased(int key) {}

	private void drawGrid(Graphics graphics){
		for(int i = 0 ; i < Grid.LINES; i ++ ){
			for(int j = 0 ; j < Grid.LINES_SIZE ; j ++ ){
				graphics.drawImage(ResourceManager.texture("block_void.png"),
						j*30, i*30-30, 30, 30, null) ;
			}
		}
	}
	private void drawBackground(Graphics graphics){
		graphics.setColor(new Color(212, 206, 206));
		graphics.fillRect(0,0, Window.getWidth(), Window.getHeight());
	}
	private static void drawGameOverMassage(Graphics graphics){
		/**
		 * when we choose level we also set the timer for solving this game
		 * and when the time is over we will get the massage about losing the game.
		 /
	}
	*/
		private Grid grid;

		private int pieceFallTime;
		private int moveDownDelay;

		private boolean foundFullLine;

		private int currentRotationOriginX;
		private int currentRotationOriginY;
		private Pentamimo.Rotation currentRotation;

		private ArrayBlockingQueue<Pentamimo> queue;
		private Pentamimo currentFallingTetromino;

		private int lines;
		private int level;

		private Pentamimo hold;

		private boolean lost;

		@Override
		protected void init() {
			this.grid = new Grid();
			this.pieceFallTime = 10;
			this.moveDownDelay = 0;
			this.foundFullLine = false;

			this.queue = new ArrayBlockingQueue<>(4);
			this.queue.add(Pentamimo.LIST.get(MathHelper.randomInt(Pentamimo.LIST.size())));
			this.queue.add(Pentamimo.LIST.get(MathHelper.randomInt(Pentamimo.LIST.size())));
			this.queue.add(Pentamimo.LIST.get(MathHelper.randomInt(Pentamimo.LIST.size())));

			this.placeTetromino();

			this.lines = 0;
			this.level = 1;

			this.hold = null;

			this.lost = false;

			System.out.println("[Game][States]: Created playing state");
		}

		@Override
		public void tick() {
			if(!this.lost) {
				this.movePiecesDown();
				this.stopPieces();

				this.nextPiece();
			}
		}

		@Override
		public void render(Graphics graphics) {
			this.drawBackground(graphics);
			this.drawGrid(graphics);
			this.drawQueue(graphics);

			if(this.lost)
				this.drawGameOverMessage(graphics);
		}

		@Override
		public void keyPressed(int key) {
			if(key == KeyEvent.VK_RIGHT) {
				if(this.grid.allSquaresCanGoRight() && !foundFullLine && !lost) {
					this.grid.movePiecesRight(); //Right
					this.currentRotationOriginX++;
				}
			}
			else if(key == KeyEvent.VK_LEFT) {
				if(this.grid.allSquaresCanGoLeft() && !foundFullLine && !lost) {
					this.grid.movePiecesLeft(); //Left
					this.currentRotationOriginX--;
				}
			}
			else if(key == KeyEvent.VK_DOWN) {
				if(this.grid.allSquaresCanFall() && !foundFullLine && !lost) {
					this.grid.movePiecesDown(); //SoftDrop
					this.currentRotationOriginY++;
				}
			} else if(key == KeyEvent.VK_UP){
				if(this.grid.allSquaresCanGoUP() && !foundFullLine && !lost) {
					this.grid.movePiecesUp();
					this.currentRotationOriginY--;
				}
			}else if(key == KeyEvent.VK_ESCAPE) {
				if(!lost) {
					Game.STATE_MANAGER.changeState(new PauseMenu());
				} else {
//					int[] scores = ResourceManager.readHighscoresFile();
//					if(this.lines > scores[8]) {
//						scores[8] = this.lines;
//						MathHelper.sortArry(scores);
//						ResourceManager.writeHighscores(scores);
//					}
					Game.STATE_MANAGER.backToPrevious(); //Reset
				}
			}else if(key == KeyEvent.VK_ENTER){
				grid.setAllSquaresToBeStopped();
			}
		}


		@Override
		public void keyReleased(int key) {}

		private void movePiecesDown() {
			if(this.moveDownDelay == this.pieceFallTime) {
//			if(this.grid.allSquaresCanFall()) {
//				this.grid.movePiecesDown();
//				this.currentRotationOriginY++;
//			}

				if(!this.grid.allSquaresCanFall()) {
					this.grid.setAllSquaresToBeStopped();
				}
				this.moveDownDelay = 0;
			}
			if(this.moveDownDelay < this.pieceFallTime) this.moveDownDelay++;
		}
		private void stopPieces() {
			for(int i=0;i<Grid.LINES;i++) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					if(this.grid.getLine(i)[j] != null) {
						if(this.grid.getLine(i)[j].isStopping()) {
							this.grid.getLine(i)[j].setFixed();
						}
					}
				}
			}
		}
		private void nextPiece() {
			if(this.grid.allSquaresAreFixed()) {
				this.moveDownDelay = 0;
				this.foundFullLine = false;
				this.placeTetromino();
			}
		}
		private void drawBackground(Graphics graphics) {
			graphics.setColor(new Color(10, 10, 30));
			graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

			graphics.setColor(new Color(212, 206, 206));

			graphics.setFont(new Font("arial", Font.PLAIN, 16));
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.drawString("Next:", 510, 30);
		}
		private void drawGrid(Graphics graphics) {
			for(int i=2;i<Grid.LINES;i++) {
				for(int j=0;j<Grid.LINES_SIZE;j++) {
					Square square = this.grid.getLine(i)[j];
					if(square != null) {
						if(!square.isFixed())
							graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+".png"), j*20, i*20-40, 20, 20, null);
						else
							graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+"_dark.png"), j*20, i*20-40, 20, 20, null);
					}
					else {
						graphics.drawImage(ResourceManager.texture("block_void.png"), j*20, i*20-40, 20, 20, null);
					}
				}
			}
		}
		private void drawQueue(Graphics graphics) {
			int p = 0;
			for(Pentamimo tetromino : this.queue) {
				for(int i=0;i<tetromino.getSize();i++) {
					for(int j=0;j<tetromino.getSize();j++) {
						Square square = tetromino.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
						if(square != null)
							graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+".png"), 520+j*15, 40+p*70+i*15, 15, 15, null);
					}
				}
				p++;
			}
			if(this.hold != null) {
				for(int i=0;i<this.hold.getSize();i++) {
					for(int j=0;j<this.hold.getSize();j++) {
						Square square = this.hold.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
						if(square != null)
							graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+".png"), 220+j*15, 340+i*15, 15, 15, null);
					}
				}
			}
		}

		private void drawGameOverMessage(Graphics graphics) {
			graphics.setColor(Color.BLACK);
			graphics.fillRect(0, Window.HEIGHT/2-30, Window.WIDTH, 70);
			graphics.setColor(Color.RED);
			graphics.setFont(new Font("Arial", Font.PLAIN, 30));
			graphics.drawString("Game Over!", 15, Window.HEIGHT/2);
			graphics.setFont(new Font("Arial", Font.PLAIN, 14));
			graphics.drawString("Press esc to return to title screen", 15, Window.HEIGHT/2+30);
		}

		private void placeTetromino() {
			try {
				this.currentFallingTetromino = this.queue.poll();
				this.currentRotationOriginX = 3;
				//this.currentRotationOriginY = this.grid.findEmptyLine(2, this.currentFallingTetromino);
				this.currentRotation = Pentamimo.Rotation.ROT0;
				this.grid.placeTetromino(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation);
				this.queue.add(Pentamimo.LIST.get(MathHelper.randomInt(Pentamimo.LIST.size())));
			} catch(ArrayIndexOutOfBoundsException e) {
				this.lost = true;
			}
		}

		private void rotateClockwise() {
			this.fixRotationOrigin();

			if(this.grid.canPlaceHere(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateClockwise())) {
				this.grid.removeTetromino();
				this.currentRotation = this.currentRotation.rotateClockwise();
				this.grid.placeTetromino(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation);
			}
		}

		private void rotateCounterclockwise() {
			this.fixRotationOrigin();

			if(this.grid.canPlaceHere(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateCounterclockwise())) {
				this.grid.removeTetromino();
				this.currentRotation = this.currentRotation.rotateCounterclockwise();
				this.grid.placeTetromino(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation);
			}
		}

		private void fixRotationOrigin() {
			if(this.currentRotationOriginX < 0)
				this.currentRotationOriginX = 0;
			if(this.currentRotationOriginX + this.currentFallingTetromino.getSize() > Grid.LINES_SIZE)
				this.currentRotationOriginX = (Grid.LINES_SIZE-1) - this.currentFallingTetromino.getSize();
			if(this.currentRotationOriginY + this.currentFallingTetromino.getSize() > Grid.LINES)
				this.currentRotationOriginY = (Grid.LINES-1) - this.currentFallingTetromino.getSize();
		}

		private void hold() {
			this.grid.removeTetromino();
			Pentamimo newTetromino = null;
			if(this.hold == null) {
				newTetromino = this.queue.poll();
				this.queue.add(Pentamimo.LIST.get(MathHelper.randomInt(Pentamimo.LIST.size())));
			} else {
				newTetromino = this.hold;
			}
			this.currentRotation = Pentamimo.Rotation.ROT0;
			this.currentRotationOriginX = 3;
			//this.currentRotationOriginY = this.grid.findEmptyLine(2, newTetromino);
			this.grid.placeTetromino(newTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation);
			this.hold = this.currentFallingTetromino;
			this.currentFallingTetromino = newTetromino;
		}
	}