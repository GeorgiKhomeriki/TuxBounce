package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import util.Config;
import assets.Fonts;
import assets.Sounds;
import assets.Textures;

public abstract class HighscoreMenu {
	private static List<Highscore> highscores;
	
	private float highlightColor;
	private float highlightColorDelta;

	public HighscoreMenu() {
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
		loadHighscores();
	}

	public abstract void backToMainMenu();

	public void render(int delta) {
		renderHighscores();
		renderBack();
		renderCursor();
	}

	private void renderHighscores() {
		glColor3f(1.0f, 1.0f, 1.0f);
		float xOffset = 0.15f * Display.getWidth();
		float yOffset = 0.7f * Display.getHeight();
		for (int i = 0; i < 10; i++) {
			String name;
			int score;
			if (i < highscores.size()) {
				Highscore highscore = highscores.get(i);
				name = highscore.getName();
				score = highscore.getScore();
			} else {
				name = "?";
				score = 0;
			}
			float y = yOffset - i * Fonts.get().large().getCharacterHeight()
					* 1.2f;
			Fonts.get().large().renderText(i + " " + name, xOffset, y);
			Fonts.get()
					.large()
					.renderText(
							String.valueOf(score),
							xOffset + Fonts.get().large().getCharacterWidth()
									* 13, y);
		}
	}

	private void renderBack() {
		glColor3f(1.0f, highlightColor, 0.0f);
		Fonts.get()
				.large()
				.renderText("BACK", Display.getWidth() * 0.45f,
						0.03f * Display.getHeight());
	}

	private void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.45f - 1.2f * width;
		float y = 0.03f * Display.getHeight();
		Texture cursorTexture = Textures.get().getBall();
		cursorTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, cursorTexture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(cursorTexture.getWidth(), cursorTexture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(cursorTexture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}

	public void update(int delta) {
		if (!Commons.get().isKeyPressed()
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0) && isMouseOnBack())) {
			Commons.get().setKeyPressed(true);
			backToMainMenu();
			Sounds.get().playDecline();
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !Mouse.isButtonDown(0)) {
			Commons.get().setKeyPressed(false);
		}
		updateHighlightColor(delta);
	}

	private boolean isMouseOnBack() {
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > Display.getWidth() * 0.45f
				&& x < Display.getWidth() * 0.45f + 4
						* Fonts.get().large().getCharacterWidth()
				&& y > 0.03f * Display.getHeight()
				&& y < 0.03f * Display.getHeight()
						+ Fonts.get().large().getCharacterHeight();
	}

	private void updateHighlightColor(float delta) {
		float newColor = highlightColor + highlightColorDelta * delta / 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highlightColorDelta = -highlightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

	public static void loadHighscores() {
		HighscoreMenu.highscores = Config.readHighscores();
	}
	
}
