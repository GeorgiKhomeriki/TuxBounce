package breakout;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import util.Timer;

public class Ball {
	private static final int DEBOUNCE_TIME = 200;
	private static final int STICKY_TIME = 4000;
	private static final float SPEED_UP_DELTA = 0.02f;
	private float x;
	private float y;
	private float r;
	private float hitR;
	private float dx;
	private float dy;
	private Texture texture;
	private long debounceStartTimeX;
	private long debounceStartTimeY;
	private boolean sticky;
	private long stickyTimer;
	private float angle;
	private float speedFactor;

	public Ball(float x, float y, float r, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.hitR = 0.45f * r;
		this.dx = dx;
		this.dy = dy;
		this.sticky = true;
		this.stickyTimer = 0l;
		this.angle = 0.0f;
		this.speedFactor = 1.0f;

		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/tux.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		glLoadIdentity();
		glTranslatef(x, y, 0.0f);
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glColor3f(1.0f, 1.0f, 1.0f);
		texture.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex2f(-0.5f * r, -0.5f * r);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(0.5f * r, -0.5f * r);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(0.5f * r, 0.5f * r);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(-0.5f * r, 0.5f * r);
		glEnd();
		glLoadIdentity();
	}

	public void update(float delta, Paddle paddle) {
		if (sticky) {
			x = paddle.getX() + paddle.getWidth() / 2.0f;
			y = paddle.getY() + paddle.getBounceHeight() + 0.5f * r;
			stickyTimer += delta;
			if (Mouse.isButtonDown(0) || stickyTimer > STICKY_TIME) {
				sticky = false;
			}
		} else {
			long time = Timer.getTime();
			if (time - debounceStartTimeX > DEBOUNCE_TIME
					&& (x - 0.5f * hitR < 0 || x + 0.5f * hitR > Display.getWidth())) {
				dx = -dx;
				debounceStartTimeX = Timer.getTime();
			}

			if (time - debounceStartTimeY > DEBOUNCE_TIME) {
				if (y + 0.5f * r > Display.getHeight() - Hud.height) {
					dy = -dy;
					debounceStartTimeY = time;
				}
				if (y - 0.5f * hitR <= paddle.getY() + paddle.getHeight()
						&& y > paddle.getY() && x > paddle.getX()
						&& x < paddle.getX() + paddle.getWidth()) {
					dx = (x - (paddle.getX() + 0.5f * paddle.getWidth())) * 3.0f;
					dy = -dy;
					paddle.bounce((x - paddle.getX()) / paddle.getWidth());
					speedFactor += SPEED_UP_DELTA;
					debounceStartTimeY = time;
				}
			}

			x += dx * delta / 300.0f;
			y += dy * delta / 300.0f * speedFactor;
			angle -= 0.1f * dx;
		}
	}

	public void bounce(Block block) {
		if (Timer.getTime() - debounceStartTimeX > DEBOUNCE_TIME
				&& (block.getX() < x + 0.5f * hitR || block.getX() + Block.width > x
						- 0.5f * hitR)) {
			dx = -dx;
			debounceStartTimeX = Timer.getTime();
		}
		if (Timer.getTime() - debounceStartTimeY > DEBOUNCE_TIME
				&& (block.getY() - Block.height < y + 0.5f * hitR || block.getY() > y
						- 0.5f * hitR)) {
			dy = -dy;
			debounceStartTimeY = Timer.getTime();
		}
	}

	public boolean isAlive() {
		return y + r >= 0;
	}

	public boolean isSticky() {
		return sticky;
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

	public float getHitR() {
		return hitR;
	}

	public void setHitR(float hitR) {
		this.hitR = hitR;
	}

}
