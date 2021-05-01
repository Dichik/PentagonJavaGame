package framework.resources;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceManager {

	private static final HashMap<String, BufferedImage> TEXTURES = new HashMap<>();

	public static void readImageFiles() {

		System.out.println("[Framework][Resources]: Finished reading image files");
	}

	public static BufferedImage texture(String name) {
		return TEXTURES.get(name);
	}

}
