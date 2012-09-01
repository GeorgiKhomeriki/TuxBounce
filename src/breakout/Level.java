package breakout;

import java.util.ArrayList;
import java.util.List;

import particles.Particles;
import particles.SimpleExplosion;
import util.LevelLoader;
import breakout.Block.BlockState;

public class Level {
	private List<Block> blocks;
	private List<Particles> particles;
	private List<Coin> coins;

	public Level(String file) {
		blocks = LevelLoader.load(file);
		particles = new ArrayList<Particles>();
		coins = new ArrayList<Coin>();
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
	}

	public void update(float delta, Paddle paddle, Ball ball) {
		// update blocks
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			if (block.getState().equals(BlockState.ALIVE) && block.isHit(ball)) {
				ball.bounce(block);
				block.onHit();
				particles.add(new SimpleExplosion(20, block.getTexture(), block
						.getX() + 0.5f * Block.width, block.getY() - 0.5f
						* Block.height, 0.4f, 0.4f, 0.4f, 1.0f, 1.0f, 1.0f));
				
				coins.add(new Coin(block.getTexture(), block.getX(), block
						.getY(), Block.width, Block.height, 0.0f, -40.0f));
			} else if (block.getState().equals(BlockState.DEAD)) {
				blocks.remove(block);
				i--;
			} else {
				block.update(delta);
			}
		}

		// update particles
		for (int i = 0; i < particles.size(); i++) {
			Particles p = particles.get(i);
			if (p.isAlive()) {
				p.update(delta, paddle);
			} else {
				particles.remove(i);
				i--;
			}
		}

		// update coins
		for (int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			if (c.isAlive()) {
				c.update(delta, paddle);
			} else {
				coins.remove(i);
				i--;
			}
		}
	}

}
