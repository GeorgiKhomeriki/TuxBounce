package menu;

import org.newdawn.slick.opengl.Texture;

import engine.Font;

public abstract class LevelChoiceMenu {

	private Font font;
	private Texture cursorTexture;

	public LevelChoiceMenu(Font font, Texture cursorTexture) {
		this.font = font;
		this.cursorTexture = cursorTexture;
	}

	public abstract void backToMainMenu();

	public void render(int delta) {
		
	}

	public void update(int delta) {

	}

}
