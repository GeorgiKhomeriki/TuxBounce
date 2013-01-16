package menu;

public class Highscore {
	private final String name;
	private final int score;

	public Highscore(final String name, final int score) {
		this.name = name;
		this.score = score;
	}

	public final String getName() {
		return name;
	}

	public final int getScore() {
		return score;
	}
}
