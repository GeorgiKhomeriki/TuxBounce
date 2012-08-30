package breakout;

import java.util.ArrayList;
import java.util.List;

import breakout.IBlock.BlockState;



import util.LevelLoader;

public class Level {
	List<IBlock> blocks = new ArrayList<IBlock>();

	public Level(String file) {
		blocks = LevelLoader.load(file);
	}

	public void render() {
		for (IBlock block : blocks) {
			block.render();
		}
	}

	public void update(float delta, Ball ball) {
		for(int i = 0; i < blocks.size(); i++) {
			IBlock block = blocks.get(i);
			if(block.getState().equals(BlockState.ALIVE) && block.isHit(ball)) {
				ball.bounce(block);
				block.onHit();
			} else if(block.getState().equals(BlockState.DEAD)) {
				blocks.remove(block);
				i--;
			} else {
				block.update(delta);
			}
		}
	}

}
