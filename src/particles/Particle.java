package particles;

import game.Paddle;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import util.Graphics;

public class Particle {
	private Texture texture;
	private float x;
	private float y;
	private float dx;
	private float dy;
	private float ay;
	private float size;
	private int life;
	private int age;
	private float intensity;

	public Particle(Texture texture, float x, float y, float dx, float dy,
			float ay, float size, int life, float intensity) {
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

	public void update(float delta) {
		x += dx * delta;
		y += dy * delta;
		dy += ay;
		age++;
	}

	public void render() {
		Graphics.drawQuad(x, y, size, size, texture, intensity, true);
	}

	public boolean isAlive() {
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}

	public boolean hasHitPaddle(Paddle paddle) {
		return dy < 0.0f && x < paddle.getX() + paddle.getWidth()
				&& x + size > paddle.getX()
				&& y - size < paddle.getY() + paddle.getHeight()
				&& y > paddle.getHeight();
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getLife() {
		return life;
	}

	public int getAge() {
		return age;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

}
