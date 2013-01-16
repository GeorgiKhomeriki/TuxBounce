package assets;

import java.io.IOException;

import engine.Font;

public class Fonts {
	private static Fonts instance;
	private final Font smallFont;
	private final Font largeFont;

	public Fonts() throws IOException {
		this.smallFont = new Font("resources/fonts/bubblemad_8x8.png", 83, 8);
		this.largeFont = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
	}

	public final Font small() {
		return smallFont;
	}

	public final Font large() {
		return largeFont;
	}

	public final static Fonts get() {
		if (instance == null) {
			try {
				instance = new Fonts();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
}
