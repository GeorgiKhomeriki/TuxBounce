package engine;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import util.Graphics;

public class Font {
	private final Texture texture;
	private final int numChars;
	private final float widthScale;
	private final float heightScale;

	public Font(final String file, final int numChars, final int pixels)
			throws IOException {
		texture = TextureLoader.getTexture("PNG",
				ResourceLoader.getResourceAsStream(file));
		this.numChars = numChars;
		// pixels should be 8 or 16
		widthScale = pixels == 16 ? 30.0f : 80.0f;
		heightScale = pixels == 16 ? 20.0f : 45.0f;
	}

	public void renderText(final String text, final float x, final float y) {
		texture.bind();
		final float width = Display.getWidth() / widthScale;
		final float height = Display.getHeight() / heightScale;
		for (int i = 0; i < text.length(); i++) {
			float c = (int) text.charAt(i) - 32;
			if (c > 0) {
				Graphics.drawQuad(x + i * width, y, width, height, c / numChars
						* texture.getWidth(), texture.getHeight(), (c + 1)
						/ numChars * texture.getWidth(), 0.0f);
			}
		}
	}

	public final float getCharacterWidth() {
		return Display.getWidth() / widthScale;
	}

	public final float getCharacterHeight() {
		return Display.getHeight() / heightScale;
	}

}
