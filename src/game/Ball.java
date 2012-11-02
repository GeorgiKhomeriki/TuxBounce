package game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

import menu.Commons;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import assets.Textures;

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
		this.hitR = 0.5f * r;
		this.dx = dx;
		this.dy = dy;
		this.sticky = true;
		this.stickyTimer = 0l;
		this.angle = 0.0f;
		this.speedFactor = 1.0f;
	}

	public void render() {
		glLoadIdentity();
		glTranslatef(x, y, 0.0f);
		glRotatef(angle, 0.0f, 0.0f, 1.0f);
		glColor3f(1.0f, 1.0f, 1.0f);
		Texture texture = Textures.get().getBall();
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
			if (Mouse.isButtonDown(0) && !Commons.get().isKeyPressed()
					|| stickyTimer > STICKY_TIME) {
				Commons.get().setKeyPressed(true);
				sticky = false;
			}
		} else {
			long time = Timer.getTime();

			if (time - debounceStartTimeY > DEBOUNCE_TIME) {
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

			float newX = x + dx * delta / 300.0f;
			float newY = y + dy * delta / 300.0f * speedFactor;

			if (newX - 0.5f * hitR < 0
					|| newX + 0.5f * hitR > Display.getWidth()) {
				dx = -dx;
				newX = x + dx * delta / 300.0f;
			}
			if (newY + 0.5f * hitR > Display.getHeight() - Hud.height) {
				dy = -dy;
				newY = y + dy * delta / 300.0f * speedFactor;
			}
			x = newX;
			y = newY;

			angle -= 0.1f * dx;
		}
	}

	public void bounce(Block block) {
		if (Timer.getTime() - debounceStartTimeY > DEBOUNCE_TIME
				&& (block.getY() - Block.getHeight() < y + 0.5f * hitR || block
						.getY() > y - 0.5f * hitR)) {
			dy = -dy;
			debounceStartTimeY = Timer.getTime();
		} else if (Timer.getTime() - debounceStartTimeX > DEBOUNCE_TIME
				&& (block.getX() < x + 0.5f * hitR || block.getX()
						+ Block.getWidth() > x - 0.5f * hitR)) {
			dx = -dx;
			debounceStartTimeX = Timer.getTime();
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

	public float getY() {
		return y;
	}

	public float getHitR() {
		return hitR;
	}

}
