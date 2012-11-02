package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import menu.Highscore;

public class Data {

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

}
