package breakout;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Paddle {
	private float x;
	private float y;
	private float width;
	private float height;
	private Texture texture;
	private float bounceIndex;
	private float bounceSize;
	private float bounceHeight;
	private float bounceCenter;

	public Paddle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bounceIndex = 0.0f;
		this.bounceSize = 0.0f;
		this.bounceHeight = 0.0f;
		this.bounceCenter = 0.5f;

		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("resources/images/paddle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render() {
		texture.bind();
		Color.white.bind();

		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, texture.getHeight());
		glVertex2f(x, y);
		glTexCoord2f(bounceCenter * texture.getWidth(), texture.getHeight());
		glVertex2f(x + bounceCenter * width, y);
		glTexCoord2f(bounceCenter * texture.getWidth(), 0.0f);
		glVertex2f(x + bounceCenter * width, y + bounceHeight);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);

		glTexCoord2f(bounceCenter * texture.getWidth(), texture.getHeight());
		glVertex2f(x + bounceCenter * width, y);
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(bounceCenter * texture.getWidth(), 0.0f);
		glVertex2f(x + bounceCenter * width, y + bounceHeight);
		glEnd();
	}

	public void update(float delta) {
		x = Mouse.getX() - width / 2;
		bounceIndex += 8.0f / delta;

		if (bounceSize <= 0.0f) {
			bounceHeight = height;
		} else {
			bounceHeight = this.height + (float) Math.sin(bounceIndex)
					* bounceSize;
			bounceSize -= 0.2f;
		}
	}

	public void bounce(float bounceCenter) {
		this.bounceCenter = bounceCenter;
		bounceSize = 0.2f * height; 
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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getBounceHeight() {
		return bounceHeight;
	}
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

}
