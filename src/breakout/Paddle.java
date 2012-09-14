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
	
	public Paddle(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
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
		glTexCoord2f(texture.getWidth(), texture.getHeight());
		glVertex2f(x + width, y);
		glTexCoord2f(texture.getWidth(), 0.0f);
		glVertex2f(x + width, y + height);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(x, y + height);
		glEnd();
	}
	
	public void update() {
		x = Mouse.getX() - width/2;
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

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
