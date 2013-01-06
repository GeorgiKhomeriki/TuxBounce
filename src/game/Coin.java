package game;

import game.Block.BlockType;

import org.lwjgl.opengl.Display;

import util.Graphics;
import assets.Textures;

public class Coin {
	private BlockType type;
	private float x;
	private float y;
	private float width;
	private float height;
	private float dy;

	public Coin(BlockType type, float x, float y, float width, float height,
			float dy) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dy = dy;
	}

	public void update(float delta) {
		y += dy * delta;
	}

	public boolean isHit(Paddle paddle) {
		return x < paddle.getX() + paddle.getWidth()
				&& x + width > paddle.getX()
				&& y - height < paddle.getY() + paddle.getHeight()
				&& y > paddle.getY();
	}

	public void render() {
		Graphics.drawQuad(x, y, width, -height,
				Textures.get().getCoinTexture(type));
	}

	public boolean isAlive() {
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}

	public BlockType getType() {
		return type;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
