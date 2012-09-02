package breakout;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import engine.IGameState;

public class GameState implements IGameState {

	public static final String name = "STATE_GAME";

	private Texture bgTexture;
	private Level level;
	private Paddle paddle;
	private Ball ball;
	private Hud hud;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		try {
			loadTextures();
		} catch (IOException e) {
			e.printStackTrace();
		}

		paddle = new Paddle(100, 10, Display.getWidth() / 6,
				Display.getHeight() / 20);
		ball = new Ball(Display.getWidth() / 2, Display.getHeight() / 2,
				Display.getWidth() / 30, Display.getWidth() / 8,
				Display.getHeight() / 6);
		level = new Level("resources/levels/level1.txt", Hud.height);
		hud = new Hud();
	}

	private void loadTextures() throws IOException {
		bgTexture = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coldmountain.png"));
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

		renderBackground(delta);

		paddle.render();

		ball.render();

		level.render();
		
		hud.render();
	}

	private void renderBackground(float delta) {
		bgTexture.bind();
		Color.white.bind();
		glBegin(GL_QUADS);

		glTexCoord2f(0.0f, bgTexture.getHeight());
		glVertex2f(0.0f, 0.0f);

		glTexCoord2f(bgTexture.getWidth(), bgTexture.getHeight());
		glVertex2f(Display.getWidth(), 0.0f);

		glTexCoord2f(bgTexture.getWidth(), 0.0f);
		glVertex2f(Display.getWidth(), Display.getHeight());

		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(0.0f, Display.getHeight());
		glEnd();
	}

	@Override
	public void update(int delta) {
		paddle.update(delta);

		ball.update(delta, paddle);

		level.update(delta, paddle, ball);
		
		hud.update(delta);
	}

}
