package framework.display;

import javax.swing.JFrame;

public class Window {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600 ;

	public static void create() {
		JFrame window = new JFrame("Pentagon");
		window.setBounds(100, 50, WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.add(new GameScreen());
		window.setVisible(true);
		System.out.println("[Framework][Display]: Created window");
	}
	public static int getWidth(){
		return WIDTH ;
	}
	public static int getHeight(){
		return HEIGHT ;
	}

}
