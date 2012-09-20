package engine;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

public class Texts {
	private List<Text> texts;
	private Font font;

	public Texts() {
		font = new Font("resources/fonts/bubblemad_8x8.png", 83, 8);
		texts = new ArrayList<Text>();
	}

	public void add(String text, float x, float y) {
		texts.add(new Text(text, x, y));
	}

	public void add(String text, float x, float y, int life, boolean doFade) {
		texts.add(new Text(text, x, y, life, doFade));
	}

	public void render() {
		boolean isLightingEnabled = glIsEnabled(GL_LIGHTING);
		if(isLightingEnabled)
			glDisable(GL_LIGHTING);
		for (Text t : texts) {
			float alpha = t.isDoFade() ? ((float) t.getLife() - (float) t
					.getAge()) / (float) t.getLife() : 1.0f;
			glColor4f(1.0f, 1.0f, 1.0f, alpha);
			font.drawText(t.getText(), t.getX(), t.getY());
		}
		if(isLightingEnabled)
			glEnable(GL_LIGHTING);
	}

	public void update(float delta) {
		for (int i = 0; i < texts.size(); i++) {
			Text t = texts.get(i);
			if (t.isAlive()) {
				t.update(delta);
			} else {
				texts.remove(i);
				i--;
			}
		}
	}
}
