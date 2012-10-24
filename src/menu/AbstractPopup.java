package menu;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import assets.Textures;

public abstract class AbstractPopup {
	private boolean enabled;
	private float x;
	private float y;
	private float width;
	private float height;

	public AbstractPopup(float width, float height) {
		this.width = width;
		this.height = height;
		this.x = 0.5f * Display.getWidth() - 0.5f * width;
		this.y = 0.5f * Display.getHeight() - 0.5f * height;
		this.enabled = false;
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
		Texture bgTexture = Textures.get().getPopup();
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
