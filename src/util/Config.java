package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import menu.Highscore;

public class Config {
	
	public static void createConfig() {
		String home = System.getProperty("user.home");
		try {
			createFile(home + "/.config");
			createFile(home + "/.config/tuxbounce");
			createFile(home + "/.config/tuxbounce/highscores");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createFile(String filename) throws IOException {
		File tbDir = new File(filename);
		if(!tbDir.exists()) {
			tbDir.createNewFile();
		}
	}
	
	public boolean checkIsHighscore(int score) {
		String home = System.getProperty("user.home");
		try {
			Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/highscores"));
			while(s.hasNextInt()) {
				if(s.nextInt() < score) {
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static List<Highscore> readHighscores() {
		List<Highscore> highscores = new ArrayList<Highscore>();
		try {
			String home = System.getProperty("user.home");
			Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/highscores"));
			while (s.hasNext()) {
				String name = s.next();
				int score = s.nextInt();
				highscores.add(new Highscore(name, score));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return highscores;
	}
	
	public static void addNewHighScore(String name, int score) {
		// implement me
	}

}
