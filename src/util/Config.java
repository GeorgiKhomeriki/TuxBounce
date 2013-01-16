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
		final String home = System.getProperty("user.home");
		try {
			createConfigFolder(home);
			createFile(home + "/.config/tuxbounce/options");
			createFile(home + "/.config/tuxbounce/highscores");
			createFile(home + "/.config/tuxbounce/progress");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createConfigFolder(final String home) {
		final File f = new File(home + "/.config/tuxbounce");
		if (!f.exists()) {
			f.mkdirs();
		}
	}

	private static void createFile(final String filename) throws IOException {
		final File f = new File(filename);
		if (!f.exists()) {
			f.createNewFile();
		}
	}

	public static void saveOptions(final String resolution,
			final boolean fullscreen, final boolean sound, final boolean music) {
		final String home = System.getProperty("user.home");
		final String filename = home + "/.config/tuxbounce/options";
		try {
			final Writer out = new BufferedWriter(new FileWriter(filename));
			out.write(resolution + "\n");
			out.write(fullscreen + "\n");
			out.write(sound + "\n");
			out.write(music + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final List<Object> loadOptions() {
		final List<Object> options = new ArrayList<Object>();
		final String home = System.getProperty("user.home");
		try {
			final Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/options"));
			for (int i = 0; s.hasNextLine(); i++) {
				if (i == 0) {
					final String[] resParts = s.nextLine().split(" x ");
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

	public static final int loadProgress() {
		final String home = System.getProperty("user.home");
		int level = 0;
		try {
			final Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/progress"));
			level = s.hasNextInt() ? s.nextInt() : 0;
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return level;
	}

	public static void saveProgress(final int level) {
		final String home = System.getProperty("user.home");
		final String filename = home + "/.config/tuxbounce/progress";
		try {
			final Writer out = new BufferedWriter(new FileWriter(filename));
			out.write(level + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final boolean checkIsHighscore(final int score) {
		final String home = System.getProperty("user.home");
		int i = 0;
		try {
			final Scanner s = new Scanner(new File(home
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

	public static final List<Highscore> readHighscores() {
		final List<Highscore> highscores = new ArrayList<Highscore>();
		try {
			final String home = System.getProperty("user.home");
			final Scanner s = new Scanner(new File(home
					+ "/.config/tuxbounce/highscores"));
			while (s.hasNext()) {
				final String name = s.next();
				final int score = s.nextInt();
				highscores.add(new Highscore(name, score));
			}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return highscores;
	}

	public static void addNewHighScore(final Highscore score) {
		final List<Highscore> highscores = readHighscores();
		final List<Highscore> newScores = new ArrayList<Highscore>();
		if (highscores.size() == 0) {
			newScores.add(score);
		} else {
			boolean added = false;
			for (int i = 0; i < highscores.size(); i++) {
				final Highscore hs = highscores.get(i);
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

	private static void writeHighscores(final List<Highscore> highscores) {
		try {
			final String home = System.getProperty("user.home");
			final String filename = home + "/.config/tuxbounce/highscores";
			final Writer out = new BufferedWriter(new FileWriter(filename));
			for (int i = 0; i < highscores.size() && i < MAX_SCORES; i++) {
				final Highscore hs = highscores.get(i);
				out.write(hs.getName() + " " + hs.getScore() + "\n");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
