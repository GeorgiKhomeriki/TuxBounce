package util;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

public class Graphics {
	/**
	 * Compares two display modes.
	 * 
	 * @param d1
	 *            DisplayMode one
	 * @param d2
	 *            DisplayMode two
	 * @return true iff d1 equals d2
	 */
	public static final boolean compareDisplayModes(final DisplayMode d1,
			final DisplayMode d2) {
		return d1.getWidth() == d2.getWidth()
				&& d1.getHeight() == d2.getHeight()
				&& d1.getFrequency() == d2.getFrequency();
	}

	/**
	 * Set the display mode to be used
	 * 
	 * @param width
	 *            The width of the display required
	 * @param height
	 *            The height of the display required
	 * @param fullscreen
	 *            True if we want fullscreen mode
	 */
	public static void setDisplayMode(final int width, final int height,
			final boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width)
				&& (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				final DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					final DisplayMode current = modes[i];

					if ((current.getWidth() == width)
							&& (current.getHeight() == height)) {
						if ((targetDisplayMode == null)
								|| (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode
											.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for BPP and frequency against
						// the original display mode then it's probably best to
						// go
						// for this one since it's most likely compatible with
						// the monitor
						if ((current.getBitsPerPixel() == Display
								.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display
										.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
				Display.setVSyncEnabled(true);
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: " + width + "x"
						+ height + " fs=" + fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height
					+ " fullscreen=" + fullscreen + e);
		}
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height) {
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x + width, y);
		glVertex2f(x + width, y + height);
		glVertex2f(x, y + height);
		glEnd();
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final float tx0,
			final float ty0, final float tx1, final float ty1) {
		glBegin(GL_QUADS);
		glTexCoord2f(tx0, ty0);
		glVertex2f(x, y);
		glTexCoord2f(tx1, ty0);
		glVertex2f(x + width, y);
		glTexCoord2f(tx1, ty1);
		glVertex2f(x + width, y + height);
		glTexCoord2f(tx0, ty1);
		glVertex2f(x, y + height);
		glEnd();
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final Texture texture) {
		drawQuad(x, y, width, height, texture, 1.0f, 1.0f, 1.0f, 1.0f, false);
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final Texture texture,
			final boolean inverseTexture) {
		drawQuad(x, y, width, height, texture, 1.0f, 1.0f, 1.0f, 1.0f,
				inverseTexture);
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final Texture texture,
			final float alpha) {
		drawQuad(x, y, width, height, texture, 1.0f, 1.0f, 1.0f, alpha, false);
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final Texture texture,
			final float intensity, final float alpha,
			final boolean inverseTexture) {
		drawQuad(x, y, width, height, texture, intensity, intensity, intensity,
				alpha, inverseTexture);
	}

	public static void drawQuad(final float x, final float y,
			final float width, final float height, final Texture texture,
			final float r, final float g, final float b, final float alpha,
			boolean inverseTexture) {
		texture.bind();
		glColor4f(r, g, b, alpha);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, inverseTexture ? texture.getHeight() : 0);
		glVertex2f(x, y);
		glTexCoord2f(texture.getWidth(), inverseTexture ? texture.getHeight()
				: 0);
		glVertex2f(x + width, y);
		glTexCoord2f(texture.getWidth(),
				inverseTexture ? 0 : texture.getHeight());
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, inverseTexture ? 0 : texture.getHeight());
		glVertex2f(x, y + height);
		glEnd();
	}
}
