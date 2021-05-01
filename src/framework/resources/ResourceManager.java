package framework.resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ResourceManager {

	private static final HashMap<String, BufferedImage> TEXTURES = new HashMap<>();

	public static void readImageFiles() {

		//System.out.println("[Framework][Resources]: Finished reading image files");
	}

	public static BufferedImage texture(String name) {
		return TEXTURES.get(name);
	}

	public static int[] readTopScores(){
		int[] scores = new int[10];
		int it = 0 ;
		Scanner in = null;
		try {
			in = new Scanner(Path.of("res/scores.txt"), "UTF-8");
		} catch (IOException e) {
			System.err.println("Error!");
		}
		while(in.hasNextLine()){
			scores[it++] = Integer.parseInt(in.nextLine()) ;
		}
		return scores ;
	}

}
