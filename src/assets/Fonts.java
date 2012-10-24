package assets;

import engine.Font;

public class Fonts {
	private static Fonts instance;
	private Font smallFont;
	private Font largeFont;

	public Fonts() {
		this.smallFont = new Font("resources/fonts/bubblemad_8x8.png", 83, 8);
		this.largeFont = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
	}

	public Font small() {
		return smallFont;
	}

	public Font large() {
		return largeFont;
	}

	public static Fonts get() {
		if (instance == null) {
			instance = new Fonts();
		}
		return instance;
	}
}
