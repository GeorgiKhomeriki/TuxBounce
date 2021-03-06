package game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;

import java.util.ArrayList;
import java.util.List;

import menu.Commons;
import menu.Highscore;
import menu.InputPopup;
import menu.MenuState;
import menu.Popup;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.Texture;

import particles.Particles;
import util.Config;
import util.Graphics;
import util.LevelLoader;
import util.Random;
import assets.Sounds;
import assets.Textures;
import engine.Game;
import engine.IGameState;
import engine.Lights;
import engine.Texts;
import game.Block.BlockState;
import game.Block.BlockType;

public class GameState implements IGameState {

	public static final String name = "STATE_GAME";

	public static boolean paused = false;
	public static int level = 1;

	private Paddle paddle;
	private List<Ball> balls;
	private List<Block> blocks;
	private List<Particles> particles;
	private List<Coin> coins;
	private Texts texts;
	private Popup winPopup;
	private Popup losePopup;
	private Popup highscorePopup;
	private InputPopup inputPopup;
	private int oldScore;

	private Lights lights;
	private boolean lightingEnabled;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void init() {
		initPopups();
		initLevel(level);
	}

	private void initLevel(final int level) {
		saveProgress(level);
		lights = new Lights();
		lightingEnabled = Lights.isLightingEnabled(level);
		paddle = new Paddle(100, 10, Display.getWidth() / 6,
				Display.getHeight() / 20);
		balls = new ArrayList<Ball>();
		blocks = LevelLoader.load("resources/levels/" + level + ".txt",
				Hud.height);
		particles = new ArrayList<Particles>();
		coins = new ArrayList<Coin>();
		texts = new Texts();
		winPopup.setEnabled(false);
		losePopup.setEnabled(false);
		highscorePopup.setEnabled(false);
		inputPopup.setEnabled(false);
		oldScore = Hud.get().getScore();
		spawnBall();
	}

	private void saveProgress(final int level) {
		if (level > Commons.get().getLevelProgress()) {
			Commons.get().saveLevelProgress(level);
		}
	}

	private void initPopups() {
		winPopup = new Popup("AWESOME!", "ARE YOU READY FOR THE NEXT LEVEL?",
				0.5f * Display.getWidth(), 0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				initLevel(++level);
			}

			@Override
			public void doNo() {
				if (Config.checkIsHighscore(Hud.get().getScore())) {
					winPopup.setEnabled(false);
					highscorePopup.setEnabled(true);
				} else {
					Sounds.get().playDecline();
					Game.get().setCurrentState(MenuState.name);
				}
			}
		};
		losePopup = new Popup("TOO BAD!", "WOULD YOU LIKE TO RETRY?",
				0.5f * Display.getWidth(), 0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				Hud.get().setLives(3);
				Hud.get().setScore(oldScore);
				initLevel(level);
			}

			@Override
			public void doNo() {
				Sounds.get().playDecline();
				if (Config.checkIsHighscore(Hud.get().getScore())) {
					losePopup.setEnabled(false);
					highscorePopup.setEnabled(true);
				} else {
					Game.get().setCurrentState(MenuState.name);
				}
			}
		};
		highscorePopup = new Popup("HIGHSCORE!", "ADD YOUR SCORE TO THE LIST?",
				0.5f * Display.getWidth(), 0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				Sounds.get().playAccept();
				highscorePopup.setEnabled(false);
				inputPopup.setEnabled(true);
			}

			@Override
			public void doNo() {
				Sounds.get().playDecline();
				Game.get().setCurrentState(MenuState.name);
			}
		};
		inputPopup = new InputPopup("PLEASE ENTER YOUR NAME:",
				0.5f * Display.getWidth(), 0.333f * Display.getHeight()) {
			@Override
			public void doYes() {
				Sounds.get().playAccept();
				Config.addNewHighScore(new Highscore(this.getInput(), Hud.get()
						.getScore()));
				MenuState.activateHighscoreMenu();
				Game.get().setCurrentState(MenuState.name);
			}

			@Override
			public void doNo() {
				Sounds.get().playDecline();
				Game.get().setCurrentState(MenuState.name);
			}
		};
	}

	@Override
	public void start() {
		GameState.paused = false;
		Sounds.get().playMusic();
	}

	@Override
	public void stop() {
		Sounds.get().stopMusic();
	}

	@Override
	public void render(int delta) {
		glLoadIdentity();
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (lightingEnabled)
			glEnable(GL_LIGHTING);

		renderBackground();

		paddle.render();

		for (final Ball ball : balls) {
			ball.render();
		}

		for (final Block block : blocks) {
			block.render();
		}

		for (final Particles p : particles) {
			p.render();
		}

		for (final Coin c : coins) {
			c.render();
		}

		texts.render();

		if (lightingEnabled)
			glDisable(GL_LIGHTING);

		Hud.get().render();

		winPopup.render();
		losePopup.render();
		highscorePopup.render();
		inputPopup.render();
	}

	private void renderBackground() {
		final Texture bgTexture = Textures.get().getBgGame1();
		final float screenWidth = Display.getWidth();
		final float screenHeight = Display.getHeight();
		final float texWidth = bgTexture.getWidth();
		final float texHeight = bgTexture.getHeight();
		final float blockSize = 40.0f;
		bgTexture.bind();
		Color.white.bind();
		for (float x = 0; x < screenWidth; x += blockSize) {
			final float xi = x / blockSize;
			for (float y = 0; y < screenHeight; y += blockSize) {
				final float yi = y / blockSize;
				final float tx0 = texWidth * xi / (screenWidth / blockSize);
				final float tx1 = texWidth * (xi + 1)
						/ (screenWidth / blockSize);
				final float ty0 = texHeight - texHeight * yi
						/ (screenHeight / blockSize);
				final float ty1 = texHeight - texHeight * (yi + 1)
						/ (screenHeight / blockSize);
				Graphics.drawQuad(x, y, blockSize, blockSize, tx0, ty0, tx1,
						ty1);
			}
		}
	}

	@Override
	public void update(int delta) {
		// update paddle
		paddle.update(delta);

		// update balls
		for (int i = 0; i < balls.size(); i++) {
			final Ball ball = balls.get(i);
			ball.setHasBouncedInCurrentFrame(false);
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
				Sounds.get().playDeath();
			}
		}

		// update blocks
		for (int i = 0; i < blocks.size(); i++) {
			final Block block = blocks.get(i);
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
			final Particles p = particles.get(i);
			if (p.isAlive()) {
				p.update(delta, paddle, texts);
			} else {
				particles.remove(i);
				i--;
			}
		}

		// update coins
		for (int i = 0; i < coins.size(); i++) {
			final Coin c = coins.get(i);
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

		// update light
		lights.update(paddle, balls);

		// update texts
		texts.update(delta);

		// update HUD
		Hud.get().update(delta);

		// update popups
		winPopup.update(delta);
		losePopup.update(delta);
		highscorePopup.update(delta);
		inputPopup.update(delta);

		// check for victory
		if (!winPopup.isEnabled() && !highscorePopup.isEnabled()
				&& !inputPopup.isEnabled() && blocks.isEmpty()) {
			Sounds.get().playWin();
			winPopup.setEnabled(true);
		}

		// check for defeat
		if (!losePopup.isEnabled() && !highscorePopup.isEnabled()
				&& !inputPopup.isEnabled() && Hud.get().getLives() <= 0) {
			Sounds.get().playLose();
			losePopup.setEnabled(true);
		}

		// check if escape or q is pressed
		if ((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard
				.isKeyDown(Keyboard.KEY_Q)) && !inputPopup.isEnabled()) {
			GameState.paused = true;
			Sounds.get().playDecline();
			Game.get().setCurrentState(MenuState.name);
		}

		// check mouse clicked
		if (!losePopup.isEnabled() && !highscorePopup.isEnabled()
				&& !winPopup.isEnabled() && !inputPopup.isEnabled()
				&& !Mouse.isButtonDown(0)) {
			Commons.get().setKeyPressed(false);
		}

		// polling is required to allow streaming to get a chance to queue
		// buffers.
		SoundStore.get().poll(0);
	}

	private void doPowerup(final Coin coin) {
		switch (coin.getType()) {
		case GREY_FACE:
			texts.add("MULTI TUX", coin.getX(), coin.getY(), 130, true);
			spawnBall();
			break;
		case RED_FACE:
			texts.add("EXTRA LIFE", coin.getX(), coin.getY(), 130, true);
			if (Hud.get().getLives() > 0)
				Hud.get().addLives(1);
			break;
		case BLUE_FACE:
			texts.add("LONGER PADDLE", coin.getX(), coin.getY(), 130, true);
			paddle.setWidth(paddle.getWidth() + Display.getWidth() / 50.0f);
			break;
		case GREEN_FACE:
			texts.add("POINT FRENZY", coin.getX(), coin.getY(), 130, true);
			spawnParticles(coin);
			Sounds.get().playPointsPowerup();
			break;
		case BROWN_FACE_BROKEN:
			texts.add("MAGIC POTION", coin.getX(), coin.getY(), 130, true);
			for (Block b : blocks) {
				if (Random.get().nextFloat() < 0.2f) {
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
		for (final Ball ball : balls) {
			if (ball.isSticky())
				return;
		}
		balls.add(new Ball(Display.getWidth() / 2.0f,
				Display.getHeight() / 2.0f, Display.getWidth() / 22.0f, 0.0f,
				-Display.getHeight() / 6));
	}

	private void spawnParticles(final Block block) {
		spawnParticles(5, block.getTexture(),
				block.getX() + 0.5f * Block.getWidth(), block.getY() - 0.5f
						* Block.getHeight());
	}

	private void spawnParticles(final Coin coin) {
		spawnParticles(100, Textures.get().getCoinGreen(), coin.getX() + 0.5f
				* Block.getWidth(), coin.getY());
	}

	private void spawnParticles(final int numParticles, final Texture texture,
			final float x, final float y) {
		final float dx = Display.getWidth() / 8500.0f;
		final float minDy = Display.getHeight() / 4000.0f;
		final float maxDy = Display.getHeight() / 2500.0f;
		final float minAy = -Display.getHeight() / 65000.0f;
		final float maxAy = -Display.getHeight() / 80000.0f;
		final float size = Display.getWidth() / 80.0f;
		final int minLife = 50;
		final int maxLife = 150;
		final float startIntensity = 0.4f;
		final float endIntensity = 1.0f;
		particles.add(new Particles(numParticles, texture, x, y, -dx, dx,
				minDy, maxDy, minAy, maxAy, size, minLife, maxLife,
				startIntensity, endIntensity));
	}

	private void spawnCoin(final Block block) {
		if (block.getType().equals(Block.BlockType.BLUE_FACE)
				|| block.getType().equals(Block.BlockType.RED_FACE)
				|| block.getType().equals(Block.BlockType.GREEN_FACE)
				|| block.getType().equals(Block.BlockType.GREY_FACE)
				|| block.getType().equals(Block.BlockType.BROWN_FACE_BROKEN)) {
			coins.add(new Coin(block.getType(), block.getX(), block.getY(),
					Block.getWidth(), Block.getHeight(), -0.2f - 0.1f
							* Random.get().nextFloat()));
		}
	}

}
