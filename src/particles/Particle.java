package particles;

import game.Paddle;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import util.Graphics;

public class Particle {
	private final Texture texture;
	private float x;
	private float y;
	private final float dx;
	private float dy;
	private final float ay;
	private final float size;
	private final int life;
	private int age;
	private float intensity;

	public Particle(final Texture texture, final float x, final float y,
			final float dx, final float dy, final float ay, final float size, final int life,
			final float intensity) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.ay = ay;
		this.size = size;
		this.life = life;
		this.age = 0;
		this.intensity = intensity;
	}

	public void update(final float delta) {
		x += dx * delta;
		y += dy * delta;
		dy += ay;
		age++;
	}

	public void render() {
		Graphics.drawQuad(x, y, size, size, texture, intensity, 1.0f, true);
	}

	public final boolean isAlive() {
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}

	public final boolean hasHitPaddle(final Paddle paddle) {
		return dy < 0.0f && x < paddle.getX() + paddle.getWidth()
				&& x + size > paddle.getX()
				&& y - size < paddle.getY() + paddle.getHeight()
				&& y > paddle.getHeight();
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final int getLife() {
		return life;
	}

	public final int getAge() {
		return age;
	}

	public void setIntensity(final float intensity) {
		this.intensity = intensity;
	}

}
