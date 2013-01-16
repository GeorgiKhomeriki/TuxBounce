package game;

import game.Block.BlockType;

import org.lwjgl.opengl.Display;

import util.Graphics;
import assets.Textures;

public class Coin {
	private final BlockType type;
	private final float x;
	private float y;
	private final float width;
	private final float height;
	private final float dy;

	public Coin(final BlockType type, final float x, final float y,
			final float width, final float height, final float dy) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dy = dy;
	}

	public void update(final float delta) {
		y += dy * delta;
	}

	public final boolean isHit(final Paddle paddle) {
		return x < paddle.getX() + paddle.getWidth()
				&& x + width > paddle.getX()
				&& y - height < paddle.getY() + paddle.getHeight()
				&& y > paddle.getY();
	}

	public void render() {
		Graphics.drawQuad(x, y, width, -height,
				Textures.get().getCoinTexture(type));
	}

	public final boolean isAlive() {
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}

	public final BlockType getType() {
		return type;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

}
