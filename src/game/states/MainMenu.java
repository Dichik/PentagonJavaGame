package game.states;

import framework.gamestates.GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class MainMenu extends GameState {

	protected String[] options;
	protected int selected;
	
	@Override
	protected void init() {
		this.options = new String[] {};
		this.selected = 0;
		System.out.println("[Game][States]: Created main menu");
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics graphics) {

	}

	// work with keys up -> and down <-, (PgUp, PgDn) ;

	@Override
	public void keyPressed(int key) {

	}

	@Override
	public void keyReleased(int key) {}

	private void drawBackground(Graphics graphics) {
	}

	private void drawOptions(Graphics graphics) {

	}
	
	private void drawButtons(Graphics graphics) {

	}
}
