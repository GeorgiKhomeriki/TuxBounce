package menu;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;

import engine.Font;

public abstract class CreditsMenu implements IMenu {

	public CreditsMenu(Font font, Texture cursorTexture) {
		
	}
	
	public abstract void backToMainMenu();

	@Override
	public void render(int delta) {
		
	}

	@Override
	public void update(int delta) {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			backToMainMenu();
		}
	}

}
