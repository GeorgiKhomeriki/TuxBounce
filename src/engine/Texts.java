package engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

public class Texts {
	private List<Text> texts;
	private Font font;

	public Texts() {
		font = new Font("resources/fonts/bubblemad_8x8.png", 83);
		texts = new ArrayList<Text>();
	}

	public void add(String text, float x, float y) {
		texts.add(new Text(text, x, y));
	}

	public void add(String text, float x, float y, int life) {
		texts.add(new Text(text, x, y, life));
	}
	
	public void render() {
		Color.white.bind();
		for (Text t : texts) {
			if(t.isAlive()) {
				font.drawText(t.getText(), t.getX(), t.getY());
			}
		}
	}

	public void update(float delta) {
		for(int i = 0; i < texts.size(); i++) {
			Text t = texts.get(i);
			if(t.isAlive()) {
				t.update(delta);
			} else {
				texts.remove(i);
				i--;
			}
		}
	}
}
