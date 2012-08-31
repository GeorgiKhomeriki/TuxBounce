package breakout;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


import util.LevelLoader;

public class SimpleBlock implements IBlock {

	private float x;
	private float y;
	private float opacity;
	private Texture texture;
	private BlockState state;

	public SimpleBlock(BlockType type, float x, float y) {
		this.x = x;
		this.y = y;
		this.opacity = 1.0f;
		this.state = BlockState.ALIVE;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream(LevelLoader.getTexture(type)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
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

	@Override
	public void update(float delta) {
		if(state.equals(BlockState.DYING)) {
			opacity -= 1.0f/delta;
		}
		
		if(opacity <= 0.0f) {
			state = BlockState.DEAD;
		}
	}

	@Override
	public void onHit() {
		state = BlockState.DYING;
	}

	@Override
	public boolean isHit(Ball ball) {
		// RectA.X1 < RectB.X2 && RectA.X2 > RectB.X1 && RectA.Y1 < RectB.Y2 && RectA.Y2 > RectB.Y1
		return x < ball.getX() + ball.getR() &&
				x + width > ball.getX() &&
				y - height < ball.getY() + ball.getR() &&
				y > ball.getY();
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public BlockState getState() {
		return state;
	}
	
	@Override
	public Texture getTexture() {
		return texture;
	}
}
