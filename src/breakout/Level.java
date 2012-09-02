package breakout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import particles.Particles;
import particles.SimpleExplosion;
import util.LevelLoader;
import breakout.Block.BlockState;
import engine.Texts;

public class Level {
	private List<Block> blocks;
	private List<Particles> particles;
	private List<Coin> coins;
	private Texture[] coinTextures;
	private Texts texts;

	public Level(String file, float offset) {
		blocks = LevelLoader.load(file, offset);
		particles = new ArrayList<Particles>();
		coins = new ArrayList<Coin>();
		texts = new Texts();
		loadCoinTextures();
	}

	private void loadCoinTextures() {
		coinTextures = new Texture[4];
		try {
			coinTextures[0] = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinBlue.png"));
			coinTextures[1] = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinRed.png"));
			coinTextures[2] = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinGreen.png"));
			coinTextures[3] = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/coinGrey.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render() {
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
	}

	public void update(float delta, Paddle paddle, List<Ball> balls) {
		// update blocks
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			for(Ball ball : balls) {
				if (block.getState().equals(BlockState.ALIVE)
						&& block.isHit(ball)) {
					ball.bounce(block);
					block.onHit();
					spawnParticles(block);
					spawnCoin(block);
				} else if (block.getState().equals(BlockState.DEAD)) {
					blocks.remove(block);
					i--;
					break;
				} else {
					block.update(delta);
				}
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
					balls.add(new Ball(Display.getWidth() / 2, Display
							.getHeight() / 2, Display.getWidth() / 30, Display
							.getWidth() / 8, Display.getHeight() / 6));
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
	}

	private void spawnParticles(Block block) {
		particles.add(new SimpleExplosion(5, block.getTexture(), block.getX()
				+ 0.5f * Block.width, block.getY() - 0.5f * Block.height, 0.4f,
				0.4f, 0.4f, 1.0f, 1.0f, 1.0f));
	}

	private void spawnCoin(Block block) {
		switch (block.getType()) {
		case BLUE_FACE:
			coins.add(new Coin(coinTextures[0], block.getX(), block.getY(),
					Block.width, Block.height, 0.0f, -40.0f));
			break;
		case RED_FACE:
			coins.add(new Coin(coinTextures[1], block.getX(), block.getY(),
					Block.width, Block.height, 0.0f, -40.0f));
			break;
		case GREEN_FACE:
			coins.add(new Coin(coinTextures[2], block.getX(), block.getY(),
					Block.width, Block.height, 0.0f, -40.0f));
			break;
		case GREY_FACE:
			coins.add(new Coin(coinTextures[3], block.getX(), block.getY(),
					Block.width, Block.height, 0.0f, -40.0f));
			break;
		default:
			break;
		}
	}
}
