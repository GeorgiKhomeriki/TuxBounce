package engine;

import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glIsEnabled;

import java.util.ArrayList;
import java.util.List;

import assets.Fonts;

public class Texts {
	private final List<Text> texts;

	public Texts() {
		texts = new ArrayList<Text>();
	}

	public void add(final String text, final float x, final float y) {
		texts.add(new Text(text, x, y));
	}

	public void add(final String text, final float x, final float y,
			final int life, final boolean doFade) {
		texts.add(new Text(text, x, y, life, doFade));
	}

	public void render() {
		final boolean isLightingEnabled = glIsEnabled(GL_LIGHTING);
		if (isLightingEnabled)
			glDisable(GL_LIGHTING);
		for (Text t : texts) {
			final float alpha = t.isDoFade() ? ((float) t.getLife() - (float) t
					.getAge()) / (float) t.getLife() : 1.0f;
			glColor4f(1.0f, 1.0f, 1.0f, alpha);
			Fonts.get().small().renderText(t.getText(), t.getX(), t.getY());
		}
		if (isLightingEnabled)
			glEnable(GL_LIGHTING);
	}

	public void update(float delta) {
		for (int i = 0; i < texts.size(); i++) {
			final Text t = texts.get(i);
			if (t.isAlive()) {
				t.update(delta);
			} else {
				texts.remove(i);
				i--;
			}
		}
	}
}
