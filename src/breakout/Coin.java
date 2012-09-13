package breakout;

import breakout.Block.BlockType;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Coin {
	private Texture texture;
	private BlockType type;
	private float x;
	private float y;
	private float width;
	private float height;
	private float dx;
	private float dy;

	public Coin(Texture texture, BlockType type, float x, float y, float width,
			float height, float dx, float dy) {
		this.texture = texture;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = dx;
		this.dy = dy;
	}

	public void update(float delta) {
		x += dx / delta;
		y += dy / delta;
	}

	public boolean isHit(Paddle paddle) {
		return x < paddle.getX() + paddle.getWidth()
				&& x + width > paddle.getX()
				&& y - height < paddle.getY() + paddle.getHeight()
				&& y > paddle.getY();
	}

	public void render() {
		texture.bind();
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(x + width, y);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + width, y - height);
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex2f(x, y - height);
		glEnd();
	}

	public boolean isAlive() {
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}
	
	public Texture getTexture() {
		return texture;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}