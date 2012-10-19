package menu;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import engine.Font;

public abstract class HighscoreMenu implements IMenu {
	private Font font;
	private Texture cursorTexture;

	public HighscoreMenu(Font font, Texture cursorTexture) {
		this.font = font;
		this.cursorTexture = cursorTexture;
	}

	public abstract void backToMainMenu();

	@Override
	public void render(int delta) {

	}

	@Override
	public void update(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			backToMainMenu();
		}
	}

}
