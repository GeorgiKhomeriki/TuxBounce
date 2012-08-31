package breakout;

import java.util.ArrayList;
import java.util.List;

import particles.Particles;
import particles.SimpleExplosion;
import util.LevelLoader;
import breakout.IBlock.BlockState;

public class Level {
	private List<IBlock> blocks;
	private List<Particles> particles;

	public Level(String file) {
		blocks = LevelLoader.load(file);
		particles = new ArrayList<Particles>();
	}

	public void render() {
		for (IBlock block : blocks) {
			block.render();
		}
		for (Particles p : particles) {
			p.render();
		}
	}

	public void update(float delta, Ball ball) {
		// update blocks
		for (int i = 0; i < blocks.size(); i++) {
			IBlock block = blocks.get(i);
			if (block.getState().equals(BlockState.ALIVE) && block.isHit(ball)) {
				ball.bounce(block);
				block.onHit();
				particles.add(new SimpleExplosion(30, block.getX() + 0.5f
						* IBlock.width, ball.getY() + 0.5f * IBlock.height,
						1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f));
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
				p.update(delta);
			} else {
				particles.remove(i);
				i--;
			}
		}
	}

}
