package game;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import util.Graphics;
import assets.Sounds;
import assets.Textures;

public class Block {
	public enum BlockType {
		RED, PURPLE, ORANGE, GREY_FACE, GREEN_FACE, BLUE_FACE, RED_FACE, WALL, WALL_BROKEN, BROWN_FACE, BROWN_FACE_BROKEN;
	}

	public enum BlockState {
		ALIVE, DEAD, DYING;
	}

	private final float x;
	private final float y;
	private float opacity;
	private Texture texture;
	private BlockState state;
	private BlockType type;

	public Block(final BlockType type, final float x, final float y) {
		this.x = x;
		this.y = y;
		this.opacity = 1.0f;
		this.state = BlockState.ALIVE;
		this.type = type;
		this.texture = Textures.get().getBlockTexture(type);
	}

	public void render() {
		Graphics.drawQuad(x, y, getWidth(), -getHeight(), texture, opacity);
	}

	public void update(final float delta) {
		if (state.equals(BlockState.DYING)) {
			opacity -= delta / 290.0f;
		}

		if (opacity <= 0.0f) {
			state = BlockState.DEAD;
		}
	}

	public void onHit() {
		switch (type) {
		case WALL:
			setType(BlockType.WALL_BROKEN);
			break;
		case BROWN_FACE:
			setType(BlockType.BROWN_FACE_BROKEN);
			break;
		default:
			state = BlockState.DYING;
			break;
		}
		Sounds.get().playHit();
	}

	public final boolean isHit(final List<Ball> balls) {
		for (Ball ball : balls) {
			if (x < ball.getX() + 0.5f * ball.getHitR()
					&& x + getWidth() > ball.getX() - 0.5f * ball.getHitR()
					&& y - getHeight() < ball.getY() + 0.5f * ball.getHitR()
					&& y > ball.getY() - 0.5f * ball.getHitR()) {
				ball.bounce(this); // evil
				return true;
			}
		}
		return false;
	}

	public static final float getWidth() {
		return Display.getWidth() / 20;
	}

	public static final float getHeight() {
		return Display.getHeight() / 15;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final BlockState getState() {
		return state;
	}

	public final Texture getTexture() {
		return texture;
	}

	public final BlockType getType() {
		return type;
	}

	public void setType(final BlockType type) {
		this.type = type;
		this.texture = Textures.get().getBlockTexture(type);
	}
}
