package breakout;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import org.lwjgl.input.Keyboard;

import particles.SimpleExplosion;
import engine.IGameState;

public class MenuState implements IGameState {
	public static final String name = "MENU_STATE";
	private SimpleExplosion explosion;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		explosion = new SimpleExplosion(30, "resources/images/ball.png",
				400.0f, 300.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f);
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
		explosion.render();
	}

	@Override
	public void update(int delta) {
		explosion.update(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			explosion.reset();
		}
	}

}
