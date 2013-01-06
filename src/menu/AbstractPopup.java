package menu;

import org.lwjgl.opengl.Display;

import util.Graphics;
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
		Graphics.drawQuad(x, y, width, height, Textures.get().getPopup(), true);
	}

	public void update(float delta) {
		if (enabled) {
			updateContent(delta);
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		Commons.get().setKeyPressed(true);
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
