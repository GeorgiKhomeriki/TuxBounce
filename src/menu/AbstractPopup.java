package menu;

import org.lwjgl.opengl.Display;

import util.Graphics;
import assets.Textures;

public abstract class AbstractPopup {
	private final float x;
	private final float y;
	private final float width;
	private final float height;
	private boolean enabled;

	public AbstractPopup(final float width, final float height) {
		this.width = width;
		this.height = height;
		this.x = 0.5f * Display.getWidth() - 0.5f * width;
		this.y = 0.5f * Display.getHeight() - 0.5f * height;
		this.enabled = false;
	}

	public abstract void renderContent();

	public abstract void updateContent(final float delta);

	public void render() {
		if (enabled) {
			renderBg();
			renderContent();
		}
	}

	private void renderBg() {
		Graphics.drawQuad(x, y, width, height, Textures.get().getPopup(), true);
	}

	public void update(final float delta) {
		if (enabled) {
			updateContent(delta);
		}
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		Commons.get().setKeyPressed(true);
		this.enabled = enabled;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final float getWidth() {
		return width;
	}

	public final float getHeight() {
		return height;
	}

}
