package particles;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import breakout.Paddle;

public class Particle {
	private Texture texture;
	private float x;
	private float y;
	private float dx;
	private float dy;
	private float ax;
	private float ay;
	private float width;
	private float height;
	private int life;
	private int age;
	private float R;
	private float G;
	private float B;
	private float alpha;

	public Particle(Texture texture, float x, float y, float dx, float dy,
			float ax, float ay, float width, float height, int life) {
		this(texture, x, y, dx, dy, ax, ay, width, height, life, 1.0f, 1.0f,
				1.0f);
	}

	public Particle(Texture texture, float x, float y, float dx, float dy,
			float ax, float ay, float width, float height, int life, float R,
			float G, float B) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.ax = ax;
		this.ay = ay;
		this.width = width;
		this.height = height;
		this.life = life;
		this.age = 0;
		this.R = R;
		this.G = G;
		this.B = B;
		this.alpha = 1.0f;
	}

	public void update(float delta) {
		x += dx / delta;
		y += dy / delta;
		dx += ax;
		dy += ay;
		age++;
	}

	public void render() {
		texture.bind();
		glColor4f(R, G, B, alpha);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}

	public boolean isAlive() {
		// return age < life;
		return x >= 0 && x <= Display.getWidth() && y >= 0;
	}

	public boolean hasHitPaddle(Paddle paddle) {
		return dy < 0.0f && x < paddle.getX() + paddle.getWidth()
				&& x + width > paddle.getX()
				&& y - height < paddle.getY() + paddle.getHeight()
				&& y > paddle.getHeight();
	}

	public void setColor(float R, float G, float B, float alpha) {
		this.R = R;
		this.G = G;
		this.B = B;
		this.alpha = alpha;
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

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getAx() {
		return ax;
	}

	public void setAx(float ax) {
		this.ax = ax;
	}

	public float getAy() {
		return ay;
	}

	public void setAy(float ay) {
		this.ay = ay;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

}
