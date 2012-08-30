package breakout;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;


import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import util.Timer;

public class Ball {
	private static final int DEBOUNCE_TIME = 200;
	private float x;
	private float y;
	private float r;
	private float dx;
	private float dy;
	private Texture texture;
	private long debounceStartTimeX;
	private long debounceStartTimeY;

	public Ball(float x, float y, float r, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.dx = dx;
		this.dy = dy;

		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/ball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + r, y);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(x + r, y + r);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + r);
		glEnd();
	}

	public void update(float delta, Paddle paddle) {
		long time = Timer.getTime();
		if (time - debounceStartTimeX > DEBOUNCE_TIME && (x < 0 || x + r > Display.getWidth())) {
			dx = -dx;
			debounceStartTimeX = Timer.getTime();
		}

		if (time - debounceStartTimeY > DEBOUNCE_TIME) {
			if(y < 0 || y + r > Display.getHeight()) {
				dy = -dy;
				debounceStartTimeY = time;
			}
			if (y <= paddle.getY() + paddle.getHeight()
					&& x > paddle.getX()
					&& x < paddle.getX() + paddle.getWidth()) {
				dx = ((x + 0.5f * r) - (paddle.getX() + 0.5f * paddle.getWidth())) * 3.0f;
				dy = -dy;
				debounceStartTimeY = time;
			}
		}
		
		x += dx / delta;
		y += dy / delta;
	}
	
	public void bounce(IBlock block) {
		if(Timer.getTime() - debounceStartTimeX > DEBOUNCE_TIME &&
				(block.getX() < x + r || block.getX() + IBlock.width > x)) {
			dx = -dx;
			debounceStartTimeX = Timer.getTime();
		} if(Timer.getTime() - debounceStartTimeY > DEBOUNCE_TIME &&
				(block.getY() - IBlock.height < y + r || block.getY() > y)) {
			dy = -dy;
			debounceStartTimeY = Timer.getTime();
		}
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

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

}
