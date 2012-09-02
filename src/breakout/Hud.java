package breakout;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;

import engine.Font;

public class Hud {
	private static Hud instance;
	public static final float width = Display.getWidth();
	public static final float height = Display.getHeight() / 15.0f;
	public static final float displayHeight = Display.getHeight();

	private Font font;
	private float lineColor = 0.5f;
	private float lineColorDelta = 0.3f;

	private int score;

	public Hud() {
		this.font = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
		this.lineColor = 0.5f;
		this.lineColorDelta = 0.3f;
		this.score = 0;
	}

	public void render() {
		renderBackground();
		printText();
	}

	private void renderBackground() {
		glDisable(GL_TEXTURE_2D);
		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
		glBegin(GL_QUADS);
		glVertex2f(0.0f, displayHeight);
		glVertex2f(width, displayHeight);
		glVertex2f(width, displayHeight - height);
		glVertex2f(0.0f, displayHeight - height);
		glEnd();

		glBegin(GL_LINES);
		glColor3f(1.0f - lineColor, 1.0f - lineColor, 1.0f - lineColor);
		glVertex2f(0.0f, displayHeight);
		glColor3f(lineColor, lineColor, lineColor);
		glVertex2f(width, displayHeight);

		glColor3f(lineColor, lineColor, lineColor);
		glVertex2f(0.0f, displayHeight - height);
		glColor3f(1.0f - lineColor, 1.0f - lineColor, 1.0f - lineColor);
		glVertex2f(width, displayHeight - height);
		glEnd();

		glEnable(GL_TEXTURE_2D);
	}

	private void printText() {
		glColor3f(1.0f, 1.0f, 1.0f);
		String s = "" + score;
		for (int i = s.length(); i < 6; i++) {
			s = "0" + s;
		}
		font.drawText("SCORE:" + s, 0.0f, displayHeight - displayHeight / 17.0f);
	}

	public void addPoints(int delta) {
		score += delta;
	}

	public void update(float delta) {
		if (lineColor <= 0.0f || lineColor >= 1.0f) {
			lineColor = (int) lineColor;
			lineColorDelta = -lineColorDelta;
		}
		lineColor += lineColorDelta / delta;
	}
	
	public void reset() {
		score = 0;
	}

	public static Hud get() {
		if (instance == null) {
			instance = new Hud();
		}
		return instance;
	}
}
