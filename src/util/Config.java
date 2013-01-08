package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import menu.Highscore;

public class Config {
	private static final int MAX_SCORES = 10;

	public static void createConfig() {
		String home = System.getProperty("user.home");
		try {
			createConfigFolder(home);
			createFile(home + "/.config/tuxbounce/options");
			createFile(home + "/.config/tuxbounce/highscores");
			createFile(home + "/.config/tuxbounce/progress");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createConfigFolder(String home) {
		File f = new File(home + "/.config/tuxbounce");
		if(!f.exists()) {
			f.mkdirs();
		}
	}

	private static void createFile(String filename) throws IOException {
		File f = new File(filename);
		if (!f.exists()) {
			f.createNewFile();
		}
	}

	public static void saveOptions(String resolution, boolean fullscreen,
			boolean sound, boolean music) {
		String home = System.getProperty("user.home");
		String filename = home + "/.config/tuxbounce/options";
		try {
			Writer out = new BufferedWriter(new FileWriter(filename));
			out.write(resolution + "\n");
			out.write(fullscreen + "\n");
			out.write(sound + "\n");
			out.write(music + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Object> loadOptions() {
		List<Object> options = new ArrayList<Object>();
		String home = System.getProperty("user.home");
		try {
			Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/options"));
			for (int i = 0; s.hasNextLine(); i++) {
				if(i == 0) {
					String[] resParts = s.nextLine().split(" x ");
					options.add(Integer.parseInt(resParts[0]));
					options.add(Integer.parseInt(resParts[1]));
				} else {
					options.add(Boolean.parseBoolean(s.nextLine()));
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return options;
	}

	public static int loadProgress() {
		String home = System.getProperty("user.home");
		int level = 0;
		try {
			Scanner s = new Scanner(new File(home + "/.config/tuxbounce/progress"));
			level = s.hasNextInt() ? s.nextInt() : 0;
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return level;
	}
	
	public static void saveProgress(int level) {
		String home = System.getProperty("user.home");
		String filename = home + "/.config/tuxbounce/progress";
		try {
			Writer out = new BufferedWriter(new FileWriter(filename));
			out.write(level + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkIsHighscore(int score) {
		String home = System.getProperty("user.home");
		int i = 0;
		try {
			Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/highscores"));
			for (; s.hasNext(); i++) {
				s.next();
				if (s.nextInt() < score) {
					return true;
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return i < MAX_SCORES;
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
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return highscores;
	}

	public static void addNewHighScore(Highscore score) {
		List<Highscore> highscores = readHighscores();
		List<Highscore> newScores = new ArrayList<Highscore>();
		if (highscores.size() == 0) {
			newScores.add(score);
		} else {
			boolean added = false;
			for (int i = 0; i < highscores.size(); i++) {
				Highscore hs = highscores.get(i);
				if (!added && hs.getScore() < score.getScore()) {
					newScores.add(score);
					added = true;
				}
				newScores.add(hs);
			}
			if (!added && highscores.size() < MAX_SCORES - 1) {
				newScores.add(score);
			}
		}
		writeHighscores(newScores);
	}

	private static void writeHighscores(List<Highscore> highscores) {
		try {
			String home = System.getProperty("user.home");
			String filename = home + "/.config/tuxbounce/highscores";
			Writer out = new BufferedWriter(new FileWriter(filename));
			for (int i = 0; i < highscores.size() && i < MAX_SCORES; i++) {
				Highscore hs = highscores.get(i);
				out.write(hs.getName() + " " + hs.getScore() + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
