package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine.Font;

public abstract class Popup {
	private Font fontSmall;
	private Font fontLarge;
	private boolean enabled;
	private Texture bgTexture;
	private float x;
	private float y;
	private float width;
	private float height;

	public Popup(float width, float height) {
		try {
			bgTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/futureui.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = width;
		this.height = height;
		this.x = 0.5f * Display.getWidth() - 0.5f * width;
		this.y = 0.5f * Display.getHeight() - 0.5f * height;
		this.enabled = false;
		this.fontSmall = new Font("resources/fonts/bubblemad_8x8.png", 83, 8);
		this.fontLarge = new Font("resources/fonts/kromasky_16x16.png", 59, 16);
	}

	public abstract void renderContent();
	public abstract void updateContent(float delta);

	public void render() {
		if (enabled) {
			renderBg();
			renderContent();
		}
	}

	private void renderBg() {
		bgTexture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, bgTexture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(bgTexture.getWidth(), bgTexture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(bgTexture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}

	public void update(float delta) {
		if (enabled) {
			updateContent(delta);
		}
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Font getFontSmall() {
		return fontSmall;
	}

	public Font getFontLarge() {
		return fontLarge;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

}
