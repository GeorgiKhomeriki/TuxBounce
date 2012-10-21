package game;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import menu.MenuState;
import menu.Popup;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import particles.Particles;
import particles.SimpleExplosion;
import sound.Sound;
import util.LevelLoader;
import engine.Game;
import engine.IGameState;
import engine.Texts;
import game.Block.BlockState;
import game.Block.BlockType;

public class GameState implements IGameState {

	public static final String name = "STATE_GAME";

	public static boolean paused = false;

	private Texture bgTexture;
	private Paddle paddle;
	private List<Ball> balls;
	private List<Block> blocks;
	private List<Particles> particles;
	private List<Coin> coins;
	private Texture[] coinTextures;
	private Texts texts;
	private Random random;
	private int level;
	private Popup winPopup;
	private Popup losePopup;

	// private Lights lights;

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

		level = 0;
		initPopups();
		initLevel(level);
	}

	private void initLevel(int level) {
		// lights = new Lights();
		paddle = new Paddle(100, 10, Display.getWidth() / 6,
				Display.getHeight() / 20);
		balls = new ArrayList<Ball>();
		blocks = LevelLoader.load("resources/levels/" + level + ".txt",
				Hud.height);
		particles = new ArrayList<Particles>();
		coins = new ArrayList<Coin>();
		texts = new Texts();
		random = new Random();
		winPopup.setEnabled(false);
		losePopup.setEnabled(false);
		spawnBall();
	}

	private void initPopups() {
		winPopup = new Popup("AWESOME!",
				"ARE YOU READY FOR THE NEXT LEVEL?", 0.5f * Display.getWidth(),
				0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				initLevel(++level);
			}
		};
		losePopup = new Popup("TOO BAD!", "WOULD YOU LIKE TO RETRY?",
				0.5f * Display.getWidth(), 0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				Hud.get().setLives(3);
				initLevel(level);
			}
		};
	}

	private void loadTextures() throws IOException {
		bgTexture = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coldmountain.png"));
		coinTextures = new Texture[5];
		coinTextures[0] = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coinBlue.png"));
		coinTextures[1] = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coinRed.png"));
		coinTextures[2] = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coinGreen.png"));
		coinTextures[3] = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coinGrey.png"));
		coinTextures[4] = TextureLoader.getTexture("PNG", ResourceLoader
				.getResourceAsStream("resources/images/coinPotion.png"));
	}

	@Override
	public void start() {
		GameState.paused = false;
		Sound.get().playMusic();
	}

	@Override
	public void stop() {
		Sound.get().stopMusic();
	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		renderBackground();

		paddle.render();

		for (Ball ball : balls) {
			ball.render();
		}

		for (Block block : blocks) {
			block.render();
		}

		for (Particles p : particles) {
			p.render();
		}

		for (Coin c : coins) {
			c.render();
		}

		texts.render();

		Hud.get().render();

		winPopup.render();
		losePopup.render();
	}

	private void renderBackground() {
		float screenWidth = Display.getWidth();
		float screenHeight = Display.getHeight();
		float texWidth = bgTexture.getWidth();
		float texHeight = bgTexture.getHeight();
		float blockSize = 40.0f;
		bgTexture.bind();
		Color.white.bind();
		for (float x = 0; x < screenWidth; x += blockSize) {
			float xi = x / blockSize;
			for (float y = 0; y < screenHeight; y += blockSize) {
				float yi = y / blockSize;
				float tx0 = texWidth * xi / (screenWidth / blockSize);
				float tx1 = texWidth * (xi + 1) / (screenWidth / blockSize);
				float ty0 = texHeight - texHeight * yi
						/ (screenHeight / blockSize);
				float ty1 = texHeight - texHeight * (yi + 1)
						/ (screenHeight / blockSize);
				glBegin(GL_QUADS);
				glTexCoord2f(tx0, ty0);
				glVertex2f(x, y);
				glTexCoord2f(tx1, ty0);
				glVertex2f(x + blockSize, y);
				glTexCoord2f(tx1, ty1);
				glVertex2f(x + blockSize, y + blockSize);
				glTexCoord2f(tx0, ty1);
				glVertex2f(x, y + blockSize);
				glEnd();
			}
		}
	}

	@Override
	public void update(int delta) {
		// if (delta != 16 && delta != 17)
		// System.out.println(delta + " " + balls.get(0).getX() + " "
		// + balls.get(0).getY());

		// update paddle
		paddle.update(delta);

		// update balls
		for (int i = 0; i < balls.size(); i++) {
			Ball ball = balls.get(i);
			if (ball.isAlive()) {
				ball.update(delta, paddle);
			} else {
				balls.remove(i);
				i--;
				if (balls.isEmpty()) {
					if (!blocks.isEmpty()) {
						Hud.get().addLives(-1);
					}
					if (Hud.get().getLives() > 0) {
						spawnBall();
					}
				}
				texts.add("OOPS!", ball.getX(), 0.0f, 100, true);
				Sound.get().playDeath();
			}
		}

		// update light
		// lights.update(paddle, balls);

		// update blocks
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			if (block.getState().equals(BlockState.ALIVE) && block.isHit(balls)) {
				if (!block.getType().equals(BlockType.WALL)
						&& !block.getType().equals(BlockType.BROWN_FACE)) {
					spawnParticles(block);
					spawnCoin(block);
				}
				block.onHit();
			} else if (block.getState().equals(BlockState.DEAD)) {
				blocks.remove(block);
				i--;
				break;
			} else {
				block.update(delta);
			}
		}

		// update particles
		for (int i = 0; i < particles.size(); i++) {
			Particles p = particles.get(i);
			if (p.isAlive()) {
				p.update(delta, paddle, texts);
			} else {
				particles.remove(i);
				i--;
			}
		}

		// update coins
		for (int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			if (c.isAlive()) {
				if (c.isHit(paddle)) {
					Hud.get().addPoints(10);
					texts.add("+10", c.getX(), c.getY(), 30, true);
					doPowerup(c);
					coins.remove(i);
					i--;
				} else {
					c.update(delta);
				}
			} else {
				coins.remove(i);
				i--;
			}
		}

		// update texts
		texts.update(delta);

		// update HUD
		Hud.get().update(delta);

		// update popups
		winPopup.update(delta);
		losePopup.update(delta);

		// check for victory
		if (blocks.isEmpty()) {
			winPopup.setEnabled(true);
		}

		// check for defeat
		if (Hud.get().getLives() <= 0) {
			losePopup.setEnabled(true);
		}

		// check if escape is pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			GameState.paused = true;
			Sound.get().playDecline();
			Game.get().setCurrentState(MenuState.name);
		}

		// polling is required to allow streaming to get a chance to queue
		// buffers.
		SoundStore.get().poll(0);
	}

	private void doPowerup(Coin coin) {
		switch (coin.getType()) {
		case GREY_FACE:
			texts.add("MULTI TUX", coin.getX(), coin.getY(), 130, true);
			spawnBall();
			break;
		case RED_FACE:
			texts.add("EXTRA LIFE", coin.getX(), coin.getY(), 130, true);
			Hud.get().addLives(1);
			break;
		case BLUE_FACE:
			texts.add("LONGER PADDLE", coin.getX(), coin.getY(), 130, true);
			paddle.setWidth(paddle.getWidth() + Display.getWidth() / 50.0f);
			break;
		case GREEN_FACE:
			texts.add("POINT FRENZY", coin.getX(), coin.getY(), 130, true);
			spawnParticles(coin);
			Sound.get().playPointsPowerup();
			break;
		case BROWN_FACE_BROKEN:
			texts.add("MAGIC POTION", coin.getX(), coin.getY(), 130, true);
			for (Block b : blocks) {
				if (random.nextFloat() < 0.2f) {
					b.onHit();
					spawnParticles(b);
					spawnCoin(b);
				}
			}
			break;
		default:
			break;
		}
	}

	private void spawnBall() {
		for (Ball ball : balls) {
			if (ball.isSticky())
				return;
		}
		balls.add(new Ball(Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f, Display.getWidth() / 22.0f, 0.0f,
				-Display.getHeight() / 6));
	}

	private void spawnParticles(Block block) {
		particles.add(new SimpleExplosion(5, block.getTexture(), block.getX()
				+ 0.5f * Block.width, block.getY() - 0.5f * Block.height, 0.4f,
				0.4f, 0.4f, 1.0f, 1.0f, 1.0f));
	}

	private void spawnParticles(Coin coin) {
		particles.add(new SimpleExplosion(100, coin.getTexture(), coin.getX()
				+ 0.5f * Block.width, coin.getY(), 0.4f, 0.4f, 0.4f, 1.0f,
				1.0f, 1.0f));
	}

	private void spawnCoin(Block block) {
		BlockType type = block.getType();
		switch (type) {
		case BLUE_FACE:
			coins.add(new Coin(coinTextures[0], type, block.getX(), block
					.getY(), Block.width, Block.height, 0.0f, -40.0f));
			break;
		case RED_FACE:
			coins.add(new Coin(coinTextures[1], type, block.getX(), block
					.getY(), Block.width, Block.height, 0.0f, -40.0f));
			break;
		case GREEN_FACE:
			coins.add(new Coin(coinTextures[2], type, block.getX(), block
					.getY(), Block.width, Block.height, 0.0f, -40.0f));
			break;
		case GREY_FACE:
			coins.add(new Coin(coinTextures[3], type, block.getX(), block
					.getY(), Block.width, Block.height, 0.0f, -40.0f));
			break;
		case BROWN_FACE_BROKEN:
			coins.add(new Coin(coinTextures[4], type, block.getX(), block
					.getY(), Block.width, Block.height, 0.0f, -40.0f));
			break;
		default:
			break;
		}
	}

}