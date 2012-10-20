package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import sound.Sound;

import engine.Font;

public abstract class CreditsMenu implements IMenu {
	private Font font;
	private Texture cursorTexture;
	private boolean isKeyPressed;
	private float highlightColor;
	private float highlightColorDelta;

	public CreditsMenu(Font font, Texture cursorTexture) {
		this.font = font;
		this.cursorTexture = cursorTexture;
		this.isKeyPressed = true;
		this.highlightColor = 1.0f;
		this.highlightColorDelta = -1.0f;
	}

	public abstract void backToMainMenu();

	@Override
	public void render(int delta) {
		renderBack();
		renderCursor();
		renderCredits();
	}

	private void renderCredits() {
		glColor3f(1.0f, 1.0f, 1.0f);
		font.drawText("CODE:   GEORGI KHOMERIKI",
				0.02f * Display.getWidth(), 0.7f * Display.getHeight());
		font.drawText("DESIGN: KETI MAGLAPERIDZE",
				0.02f * Display.getWidth(), 0.6f * Display.getHeight());
	}

	private void renderBack() {
		glColor3f(1.0f, highlightColor, 0.0f);
		font.drawText("BACK", Display.getWidth() * 0.45f,
				0.03f * Display.getHeight());
	}

	private void renderCursor() {
		float width = Display.getWidth() / 24;
		float height = Display.getHeight() / 18;
		float x = Display.getWidth() * 0.45f - 1.2f * width;
		float y = 0.03f * Display.getHeight();
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

	@Override
	public void update(int delta) {
		if (!isKeyPressed
				&& (Keyboard.isKeyDown(Keyboard.KEY_RETURN) || Mouse
						.isButtonDown(0) && isMouseOnBack())) {
			isKeyPressed = true;
			backToMainMenu();
			Sound.get().playDecline();
		}
		if (!Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !Mouse.isButtonDown(0)) {
			isKeyPressed = false;
		}
		updateHighlightColor(delta);
	}

	private boolean isMouseOnBack() {
		float x = Mouse.getX();
		float y = Mouse.getY();
		return x > Display.getWidth() * 0.45f
				&& x < Display.getWidth() * 0.45f + 4
						* font.getCharacterWidth()
				&& y > 0.03f * Display.getHeight()
				&& y < 0.03f * Display.getHeight() + font.getCharacterHeight();
	}

	private void updateHighlightColor(float delta) {
		float newColor = highlightColor + highlightColorDelta * delta / 200;
		if (newColor < 0.0f || newColor > 1.0f) {
			highlightColorDelta = -highlightColorDelta;
		} else {
			highlightColor = newColor;
		}
	}

}
