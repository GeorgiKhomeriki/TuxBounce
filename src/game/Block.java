package game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import assets.Sounds;
import assets.Textures;


public class Block {
	public static final float width = Display.getWidth() / 20;
	public static final float height = Display.getHeight() / 15;

	public enum BlockType {
		RED, PURPLE, ORANGE, GREY_FACE, GREEN_FACE, BLUE_FACE, RED_FACE, WALL, WALL_BROKEN, BROWN_FACE, BROWN_FACE_BROKEN;
	}

	public enum BlockState {
		ALIVE, DEAD, DYING;
	}

	private float x;
	private float y;
	private float opacity;
	private Texture texture;
	private BlockState state;
	private BlockType type;

	public Block(BlockType type, float x, float y) {
		this.x = x;
		this.y = y;
		this.opacity = 1.0f;
		this.state = BlockState.ALIVE;
		this.type = type;
		this.texture = Textures.get().getBlockTexture(type);
	}

	public void render() {
		texture.bind();

		glColor4f(1.0f, 1.0f, 1.0f, opacity);

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

	public void update(float delta) {
		if (state.equals(BlockState.DYING)) {
			opacity -= 1.0f / delta;
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

	public boolean isHit(List<Ball> balls) {
		for (Ball ball : balls) {
			if (x < ball.getX() + 0.5f * ball.getHitR()
					&& x + width > ball.getX() - 0.5f * ball.getHitR()
					&& y - height < ball.getY() + 0.5f * ball.getHitR()
					&& y > ball.getY() - 0.5f * ball.getHitR()) {
				ball.bounce(this);
				return true;
			}
		}
		return false;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public BlockState getState() {
		return state;
	}

	public Texture getTexture() {
		return texture;
	}

	public BlockType getType() {
		return type;
	}

	public void setType(BlockType type) {
		this.type = type;
		this.texture = Textures.get().getBlockTexture(type);
	}
}
