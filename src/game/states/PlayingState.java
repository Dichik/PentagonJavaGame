package game.states;

import framework.gamestates.GameState;

import java.awt.Graphics;

public class PlayingState extends GameState {


	
	@Override
	protected void init() {

		System.out.println("[Game][States]: Created playing state");
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics graphics) {

	}

	@Override
	public void keyPressed(int key) {

	}

	@Override
	public void keyReleased(int key) {}

}