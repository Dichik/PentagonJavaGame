package game.states;

import framework.gamestates.GameState;
import framework.resources.ResourceManager;
import game.Game;
import game.pieces.Grid;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import framework.display.Window ;
import game.pieces.Pentamimo;
import game.pieces.Square;

public class PlayingState extends GameState {
/**
 * when we choose level we also set the timer for solving this game
 * and when the time is over we will get the massage about losing the game.
 */
	private Grid grid;

	private int pieceFallTime;
	private int moveDownDelay;

	private int currentRotationOriginX;
	private int currentRotationOriginY;
	private Pentamimo.Rotation currentRotation;

	private ArrayBlockingQueue<Pentamimo> queue;
	private Pentamimo currentFallingTetromino;

	private Pentamimo hold;

	private boolean lost;

	private static int randomInt(int SIZE){
		return new Random().nextInt(SIZE) ;
	}

	@Override
	protected void init() {
		this.grid = new Grid();
		this.pieceFallTime = 10;
		this.moveDownDelay = 0;

		this.queue = new ArrayBlockingQueue<>(4);
		this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
		this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
		this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));

		this.placePentamimo();

		this.hold = null;

		this.lost = false;

		System.out.println("[Game][States]: Created playing state");
	}

	@Override
	public void tick() {
		if(!this.lost){
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
		/**
		 * should make ability to drag a piece
		 * because when we use keys it makes lots of collapse and troubles.
		 */
		if(key == KeyEvent.VK_RIGHT) {
			if(this.grid.allSquaresCanGoRight() && !lost) {
				this.grid.movePiecesRight();
				this.currentRotationOriginX++;
			}
		}
		else if(key == KeyEvent.VK_LEFT) {
			if(this.grid.allSquaresCanGoLeft() && !lost) {
				this.grid.movePiecesLeft();
				this.currentRotationOriginX--;
			}
		}
		else if(key == KeyEvent.VK_DOWN) {
			if(this.grid.allSquaresCanFall() && !lost) {
				this.grid.movePiecesDown();
				this.currentRotationOriginY++;
			}
		} else if(key == KeyEvent.VK_UP){
			if(this.grid.allSquaresCanGoUP() && !lost) {
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
			this.placePentamimo();
		}
	}
	private void drawBackground(Graphics graphics) {
		graphics.setColor(new Color(212, 206, 206));
		graphics.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

		graphics.setFont(new Font("Roboto", Font.ITALIC + Font.BOLD, 16));
		graphics.setColor(Color.BLACK);
		graphics.drawString("Next:", 820, 30);
	}
	private int count = 0 ;
	private void drawGrid(Graphics graphics) {
		for(int i=2;i<Grid.LINES;i++) {
			for(int j=0;j<Grid.LINES_SIZE;j++) {
				Square square = this.grid.getLine(i)[j];
				if(square != null) {
					if(!square.isFixed())
						graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+".png"),
								j*30, i*30-40, 30, 30, null);
					else {
						graphics.drawImage(ResourceManager.texture("block_" + square.getColor() + "_dark.png"),
								j * 30, i * 30 - 40, 30, 30, null);
						}
					}
				else {
					graphics.drawImage(ResourceManager.texture("block_void.png"),
							j*30, i*30-40, 30, 30, null);
				}
			}
		}
	}
	private void drawQueue(Graphics graphics) {
		int count = 0;
		for(Pentamimo p : this.queue) {
			for(int i=0;i<p.getSize();i++) {
				for(int j=0;j<p.getSize();j++) {
					Square square = p.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
					if(square != null)
						graphics.drawImage(ResourceManager.texture("block_"+square.getColor() +".png"),
								820+j*30, 40+count*150+i*30, 30, 30, null);
				}
			}
			count++;
		}
		if(this.hold != null) {
			for(int i=0;i<this.hold.getSize();i++) {
				for(int j=0;j<this.hold.getSize();j++) {
					Square square = this.hold.getSquareAt(Pentamimo.Rotation.ROT0, i, j);
					if(square != null)
						graphics.drawImage(ResourceManager.texture("block_"+square.getColor()+".png"),
								520+j*30, 340+i*30, 30, 30, null);
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

	private void placePentamimo() {
		try {
			this.currentFallingTetromino = this.queue.poll();
			this.currentRotationOriginX = 3;
			this.currentRotation = Pentamimo.Rotation.ROT0;
			this.grid.placeTetromino(currentFallingTetromino, 0, 2, currentRotation);
			this.queue.add(Pentamimo.LIST.get(randomInt(Pentamimo.LIST.size())));
		} catch(ArrayIndexOutOfBoundsException e) {
			this.lost = true;
		}
	}

	private void rotateClockwise() {
		this.fixRotationOrigin();

		if(this.grid.canPlaceHere(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateClockwise())) {
			//this.grid.removeTetromino();
			this.currentRotation = this.currentRotation.rotateClockwise();
			this.grid.placeTetromino(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation);
		}
	}

	private void rotateCounterclockwise() {
		this.fixRotationOrigin();

		if(this.grid.canPlaceHere(currentFallingTetromino, currentRotationOriginX, currentRotationOriginY, currentRotation.rotateCounterclockwise())) {
			//this.grid.removeTetromino();
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
}