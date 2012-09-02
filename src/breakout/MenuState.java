package breakout;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import org.lwjgl.opengl.Display;

import engine.Font;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";

	private Font font;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		font = new Font("resources/fonts/bubblemad_8x8.png", 83, 8);
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		font.drawText("TEST GELA!", Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f);
	}

	@Override
	public void update(int delta) {

	}

}
